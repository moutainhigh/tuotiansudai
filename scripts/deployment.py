import os
from paver.shell import sh
import config_deploy

class Deployment(object):

    _config_path = os.getenv('TTSD_CONFIG_PATH', '/workspace/deploy-config')
    _gradle='/opt/gradle/latest/bin/gradle'
    _dockerCompose='/usr/local/bin/docker-compose'
    _paver='/usr/bin/paver'

    def deploy(self, env):
        self.clean()
        self.config_file(env)
        self.jcversion()
        self.compile()
        self.build_and_unzip_worker()
        self.build_mq_consumer()
        self.build_rest_service()
        self.build_diagnosis()
        self.build_worker_monitor()
        self.mk_static_package()
        self.init_docker()

    def clean(self):
        print "Cleaning..."
        print self._gradle
        sh('/usr/bin/git clean -fd', ignore_error=True)

    def config_file(self, env):
        print "Generate config file..."
        config_deploy.deploy(env, "./ttsd-config/src/main/resources/", "{0}/ttsd-config/ttsd-env.properties".format(self._config_path))

    def compile(self):
        print "Compiling..."
        sh('{0} clean ttsd-config:flywayAA ttsd-config:flywayUMP ttsd-config:flywayAnxin ttsd-config:flywaySms ttsd-config:flywayWorker '
           'ttsd-config:flywayAsk ttsd-config:flywayActivity ttsd-config:flywayPoint ttsd-config:flywayMessage ttsd-config:flywayLog initMQ war renameWar'.format(
            self._gradle))
        sh('cp {0}/signin_service/settings_local.py ./signin_service/'.format(self._config_path))

    def build_and_unzip_worker(self):
        print "Making worker build..."
        sh('cd ./ttsd-job-worker && {0} distZip'.format(self._gradle))
        sh('cd ./ttsd-job-worker && {0} -Pwork=jpush distZip'.format(self._gradle))
        sh('cd ./ttsd-job-worker && {0} -Pwork=repay distZip'.format(self._gradle))
        sh('cd ./ttsd-job-worker/build/distributions && unzip \*.zip')

    def build_mq_consumer(self):
        print "Making MQ consumer build..."
        sh('cd ./ttsd-loan-mq-consumer && {0} distZip'.format(self._gradle))
        sh('cd ./ttsd-loan-mq-consumer/build/distributions && unzip \*.zip')
        sh('cd ./ttsd-message-mq-consumer && {0} distZip'.format(self._gradle))
        sh('cd ./ttsd-message-mq-consumer/build/distributions && unzip \*.zip')
        sh('cd ./ttsd-point-mq-consumer && {0} distZip'.format(self._gradle))
        sh('cd ./ttsd-point-mq-consumer/build/distributions && unzip \*.zip')
        sh('cd ./ttsd-activity-mq-consumer && {0} distZip'.format(self._gradle))
        sh('cd ./ttsd-activity-mq-consumer/build/distributions && unzip \*.zip')
        sh('cd ./ttsd-user-mq-consumer && {0} distZip'.format(self._gradle))
        sh('cd ./ttsd-user-mq-consumer/build/distributions && unzip \*.zip')
        sh('cd ./ttsd-auditLog-mq-consumer && {0} distZip'.format(self._gradle))
        sh('cd ./ttsd-auditLog-mq-consumer/build/distributions && unzip \*.zip')

    def build_rest_service(self):
        print "Making rest services build..."
        sh('cd ./ttsd-ask-rest && {0} distZip'.format(self._gradle))
        sh('cd ./ttsd-ask-rest/build/distributions && unzip \*.zip')

    def build_diagnosis(self):
        print "Making diagnosis build..."
        sh('cd ./ttsd-diagnosis && {0} distZip'.format(self._gradle))
        sh('cd ./ttsd-diagnosis/build/distributions && unzip \*.zip')

    def build_worker_monitor(self):
        print "Making diagnosis build..."
        sh('cd ./ttsd-worker-monitor && {0} bootRepackage'.format(self._gradle))

    def mk_static_package(self):
        print "Making static package..."
        sh('cd ./ttsd-web/src/main/webapp && zip -r static.zip images/ js/ pdf/ style/ tpl/ robots.txt')
        sh('mv ./ttsd-web/src/main/webapp/static.zip  ./ttsd-web/build/')
        sh('cd ./ttsd-web/build && unzip static.zip -d static')

        sh('cd ./ttsd-mobile-api/src/main/webapp && zip -r static_api.zip api/')
        sh('mv ./ttsd-mobile-api/src/main/webapp/static_api.zip  ./ttsd-web/build/')
        sh('cd ./ttsd-web/build && unzip static_api.zip -d static')

        sh('cd ./ttsd-frontend-manage/resources/prod && zip -r static_ask.zip *')
        sh('mv ./ttsd-frontend-manage/resources/prod/static_ask.zip  ./ttsd-web/build/')
        sh('cd ./ttsd-web/build && unzip static_ask.zip -d static')

        sh('cd ./ttsd-activity-web/src/main/webapp && zip -r static_activity.zip activity/')
        sh('mv ./ttsd-activity-web/src/main/webapp/static_activity.zip  ./ttsd-web/build/')
        sh('cd ./ttsd-web/build && unzip static_activity.zip -d static')

        sh('cd ./ttsd-point-web/src/main/webapp && zip -r static_point.zip point/')
        sh('mv ./ttsd-point-web/src/main/webapp/static_point.zip  ./ttsd-web/build/')
        sh('cd ./ttsd-web/build && unzip static_point.zip -d static')

    def init_docker(self):
        print "Initialing docker..."
        import platform

        sudoer = 'sudo' if 'centos' in platform.platform() else ''
        self._remove_old_container(sudoer)
        self._start_new_container(sudoer)

    def _remove_old_container(self, suoder):
        sh('{0} {1} -f dev.yml stop'.format(suoder, self._dockerCompose))
        sh('{0} /bin/bash -c "export COMPOSE_HTTP_TIMEOUT=300 && {1} -f dev.yml rm -f"'.format(suoder, self._dockerCompose))

    def _start_new_container(self, sudoer):
        sh('{0} {1} -f dev.yml up -d'.format(sudoer, self._dockerCompose))

    def jcversion(self):
        print "Starting jcmin..."
        sh('{0} jcversion'.format(self._paver))
