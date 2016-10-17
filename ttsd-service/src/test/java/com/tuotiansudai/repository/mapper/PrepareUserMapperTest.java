package com.tuotiansudai.repository.mapper;


import com.tuotiansudai.enums.Source;
import com.tuotiansudai.repository.model.PrepareUserModel;
import com.tuotiansudai.repository.model.UserModel;
import com.tuotiansudai.repository.model.UserStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

public class PrepareUserMapperTest extends BaseMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PrepareUserMapper prepareUserMapper;

    @Test
    public void shouldCreatePrepare() {
        UserModel userModel = fakeUserModel("prepareUser", "18999999999");
        userMapper.create(userModel);
        PrepareUserModel prepareUserModel = fakePrepareModel(userModel.getMobile(), "18998888888");
        prepareUserMapper.create(prepareUserModel);
    }


    private UserModel fakeUserModel(String loginName, String mobile) {
        UserModel fakeUser = new UserModel();
        fakeUser.setLoginName(loginName);
        fakeUser.setPassword("password");
        fakeUser.setMobile(mobile);
        fakeUser.setRegisterTime(new Date());
        fakeUser.setStatus(UserStatus.ACTIVE);
        fakeUser.setSalt(UUID.randomUUID().toString().replaceAll("-", ""));
        return fakeUser;
    }


    private PrepareUserModel fakePrepareModel(String referrerMobile, String mobile) {
        PrepareUserModel prepareUserModel = new PrepareUserModel();
        prepareUserModel.setReferrerMobile(referrerMobile);
        prepareUserModel.setMobile(mobile);
        prepareUserModel.setChannel(Source.ANDROID);
        prepareUserModel.setCreatedTime(new Date());
        return prepareUserModel;
    }
}
