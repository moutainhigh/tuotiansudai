# coding=utf-8
import json
from unittest import TestCase, main
from models import User

import redis

from service import SessionManager, TOKEN_FORMAT
from settings import REDIS_HOST, REDIS_PORT, REDIS_DB, WEB_TOKEN_EXPIRED_SECONDS, MOBILE_TOKEN_EXPIRED_SECONDS, \
    LOGIN_FAILED_MAXIMAL_TIMES
import web


class TestSessionManager(TestCase):
    def setUp(self):
        self.connection = redis.Redis(host=REDIS_HOST, port=REDIS_PORT, db=REDIS_DB)
        self.connection.flushdb()

    def test_should_set_data_with_new_token_and_remove_old_token(self):
        manager = SessionManager(source='WEB')
        old_token_id = manager.create()
        old_token = TOKEN_FORMAT.format(old_token_id)
        self.connection.set(old_token, "{}")
        new_data = {'data': 'new_data'}
        new_token_id = manager.set(new_data, old_token_id)

        self.assertFalse(self.connection.exists(old_token))
        self.assertIsNotNone(new_token_id)
        self.assertEqual(new_data, manager.get(new_token_id))

    def test_should_generate_new_token_with_WEB_source(self):
        manager = SessionManager(source='WEB')
        old_token_id = manager.create()
        old_token = TOKEN_FORMAT.format(old_token_id)
        self.connection.set(old_token, "{}")
        new_data = {'data': 'new_data'}
        new_token_id = manager.set(new_data, old_token_id)
        new_token = TOKEN_FORMAT.format(new_token_id)
        new_token_ttl = self.connection.ttl(new_token)
        self.assertEqual(new_token_ttl, WEB_TOKEN_EXPIRED_SECONDS)

    def test_should_generate_new_token_with_IOS_source(self):
        manager = SessionManager(source='IOS')
        old_token_id = manager.create()
        old_token = TOKEN_FORMAT.format(old_token_id)
        self.connection.set(old_token, "{}")
        new_data = {'data': 'new_data'}
        new_token_id = manager.set(new_data, old_token_id)
        new_token = TOKEN_FORMAT.format(new_token_id)
        new_token_ttl = self.connection.ttl(new_token)
        self.assertEqual(new_token_ttl, MOBILE_TOKEN_EXPIRED_SECONDS)

    def test_should_generate_new_token_with_ANDROID_source(self):
        manager = SessionManager(source='android')
        old_token_id = manager.create()
        old_token = TOKEN_FORMAT.format(old_token_id)
        self.connection.set(old_token, "{}")
        new_data = {'data': 'new_data'}
        new_token_id = manager.set(new_data, old_token_id)
        new_token = TOKEN_FORMAT.format(new_token_id)
        new_token_ttl = self.connection.ttl(new_token)
        self.assertEqual(new_token_ttl, MOBILE_TOKEN_EXPIRED_SECONDS)

    def test_replace_should_generate_new_token(self):
        manager = SessionManager(source='IOS')
        data = {'data': 'test data', 'login_name': 'sidneygao'}
        token_id = manager.set(data, 'fake_session_id')
        new_token_id = manager.refresh(token_id)['token']
        self.assertEqual(data, manager.get(new_token_id))
        self.assertIsNone(manager.get(token_id))

    def test_replace_should_return_none_given_session_id_not_exist(self):
        manager = SessionManager(source='android')
        new_token_id = manager.refresh("not_exist_session_id")
        self.assertIsNone(new_token_id)

    def test_refresh_should_update_last_login_time_source(self):
        manager = SessionManager(source='IOS')
        user_name = 'sidneygao'
        data = {'data': 'test data', 'login_name': user_name}
        token_id = manager.set(data, 'fake_session_id')
        manager.refresh(token_id)
        user = User.query.filter(User.username == user_name).first()
        self.assertIsNotNone(user.last_login_time)
        self.assertEqual(user.last_login_source, "IOS")


class TestView(TestCase):
    def setUp(self):
        web.app.config['TESTING'] = True
        self.connection = redis.Redis(host=REDIS_HOST, port=REDIS_PORT, db=REDIS_DB)
        self.connection.flushdb()
        self.app = web.app.test_client()

    def test_should_login_successful(self):
        username = 'sidneygao'
        source = 'WEB'
        data = {'username': username, 'source': source, 'device_id': 'device_id1',
                'token': 'fake_token', 'password': '123abc'}
        rv = self.app.post('/login/', data=data)
        response_data = json.loads(rv.data)
        user = User.query.filter(User.username == username).first()
        self.assertEqual(user.last_login_source, source)
        self.assertIsNotNone(user.last_login_time)
        self.assertEqual(200, rv.status_code)
        self.assertTrue(response_data['result'])
        self.assertEqual('sidneygao', response_data['user_info']['login_name'])
        self.assertSetEqual({'message', 'result', 'token', 'user_info'}, set(response_data.keys()))

    def test_should_return_400_if_password_wrong(self):
        data = {'username': 'sidneygao', 'source': 'WEB', 'device_id': 'device_id1',
                'token': 'fake_token', 'password': 'wrong_pwd'}
        rv = self.app.post('/login/', data=data)
        response_data = json.loads(rv.data)
        self.assertEqual(400, rv.status_code)
        self.assertFalse(response_data['result'])
        self.assertEqual(u'用户名或密码错误', response_data['message'])
        self.assertIsNone(response_data['user_info'])
        self.assertSetEqual({'message', 'result', 'token', 'user_info'}, set(response_data.keys()))

    def test_should_return_400_given_login_failed_times_over_3(self):
        data = {'username': 'sidneygao', 'source': 'WEB', 'device_id': 'device_id1',
                'token': 'fake_token', 'password': 'wrong_pwd'}
        for _ in range(LOGIN_FAILED_MAXIMAL_TIMES):
            rv = self.app.post('/login/', data=data)
            response_data = json.loads(rv.data)
            self.assertEqual(400, rv.status_code)
            self.assertFalse(response_data['result'])
            self.assertEqual(u'用户名或密码错误', response_data['message'])

        rv = self.app.post('/login/', data=data)
        response_data = json.loads(rv.data)
        self.assertEqual(400, rv.status_code)
        self.assertFalse(response_data['result'])
        self.assertEqual(u'用户已被禁用', response_data['message'])

    def test_should_active_user_given_user_was_banned(self):
        data = {'username': 'sidneygao', 'source': 'WEB', 'device_id': 'device_id1',
                'token': 'fake_token', 'password': 'wrong_pwd'}
        for _ in range(LOGIN_FAILED_MAXIMAL_TIMES):
            self.app.post('/login/', data=data)

        rv = self.app.post('/login/', data=data)
        response_data = json.loads(rv.data)
        self.assertEqual(400, rv.status_code)
        self.assertEqual(u'用户已被禁用', response_data['message'])

        rv = self.app.post('/user/sidneygao/active/')
        self.assertEqual(200, rv.status_code)

        data['password'] = '123abc'
        rv = self.app.post('/login/', data=data)
        response_data = json.loads(rv.data)
        self.assertEqual(200, rv.status_code)
        self.assertTrue(response_data['result'])
        self.assertEqual('sidneygao', response_data['user_info']['login_name'])

    def test_should_login_successful_without_password(self):
        username = 'sidneygao'
        source = 'WEB'
        data = {'username': username, 'source': source, 'device_id': 'device_id1',
                'token': 'fake_token'}
        rv = self.app.post('/login/nopassword/', data=data)
        response_data = json.loads(rv.data)
        user = User.query.filter(User.username == username).first()
        self.assertEqual(user.last_login_source, source)
        self.assertIsNotNone(user.last_login_time)
        self.assertEqual(200, rv.status_code)
        self.assertTrue(response_data['result'])
        self.assertEqual('sidneygao', response_data['user_info']['login_name'])
        self.assertSetEqual({'message', 'result', 'token', 'user_info'}, set(response_data.keys()))

    def test_should_return_400_given_user_not_exist(self):
        data = {'username': 'not_exist_user', 'source': 'WEB', 'device_id': 'device_id1',
                'token': 'fake_token'}
        rv = self.app.post('/login/nopassword/', data=data)
        response_data = json.loads(rv.data)
        self.assertEqual(400, rv.status_code)
        self.assertFalse(response_data['result'])
        self.assertEqual(u'用户不存在', response_data['message'])
        self.assertSetEqual({'message', 'result', 'token', 'user_info'}, set(response_data.keys()))

    def test_logout_should_return_new_token(self):
        rv = self.app.post('/logout/fakesessionid')
        response_data = json.loads(rv.data)
        self.assertEqual(200, rv.status_code)
        self.assertIsNotNone(response_data['token'])


if __name__ == '__main__':
    with web.app.app_context():
        main()
