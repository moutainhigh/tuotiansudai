package com.tuotiansudai.service;

import com.tuotiansudai.repository.model.AccountModel;

/**
 * Created by Administrator on 2015/9/11.
 */
public interface AccountService {

    public AccountModel findByLoginName(String loginName);


    long getBalance(String loginName);

    boolean isIdentityNumberExist(String identityNumber);

    long getFreeze(String loginName);
}
