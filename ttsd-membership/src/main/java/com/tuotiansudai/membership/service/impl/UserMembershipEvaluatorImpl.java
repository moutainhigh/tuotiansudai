package com.tuotiansudai.membership.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.primitives.Ints;
import com.tuotiansudai.membership.repository.mapper.MembershipMapper;
import com.tuotiansudai.membership.repository.mapper.UserMembershipMapper;
import com.tuotiansudai.membership.repository.model.MembershipModel;
import com.tuotiansudai.membership.repository.model.MembershipType;
import com.tuotiansudai.membership.repository.model.UserMembershipModel;
import com.tuotiansudai.membership.service.UserMembershipEvaluator;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.mapper.InvestMapper;
import com.tuotiansudai.repository.model.AccountModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserMembershipEvaluatorImpl implements UserMembershipEvaluator {

    @Autowired
    private MembershipMapper membershipMapper;

    @Autowired
    private UserMembershipMapper userMembershipMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private InvestMapper investMapper;

    @Override
    public MembershipModel evaluate(String loginName) {
        List<UserMembershipModel> userMembershipModels = userMembershipMapper.findByLoginName(loginName);

        if (CollectionUtils.isEmpty(userMembershipModels)) {
            return null;
        }

        UnmodifiableIterator<UserMembershipModel> filter = Iterators.filter(userMembershipModels.iterator(), new Predicate<UserMembershipModel>() {
            @Override
            public boolean apply(UserMembershipModel input) {
                return input.getExpiredTime().after(new Date());
            }
        });

        UserMembershipModel max = new Ordering<UserMembershipModel>() {
            @Override
            public int compare(UserMembershipModel left, UserMembershipModel right) {
                return Ints.compare(membershipMapper.findById(left.getMembershipId()).getLevel(), membershipMapper.findById(right.getMembershipId()).getLevel());
            }
        }.max(filter);

        return membershipMapper.findById(max.getMembershipId());
    }

    @Override
    public MembershipType receiveMembership(String loanName) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date membershipOpenDate = sdf.parse("2016-07-01");
        if(Calendar.getInstance().getTime().getTime() < membershipOpenDate.getTime()){
            return MembershipType.NOT_TO_THE_TIME;
        }

        if(loanName == null || loanName.equals("")){
            return MembershipType.NOT_TO_LOGIN;
        }

        AccountModel accountModel = accountMapper.findByLoginName(loanName);
        if(accountModel == null || StringUtils.isEmpty(accountModel.getIdentityNumber())){
            return MembershipType.NOT_TO_REGISTER;
        }

        List<UserMembershipModel> userMembershipModelList = userMembershipMapper.findByLoginName(loanName);
        if(CollectionUtils.isNotEmpty(userMembershipModelList)){
            return MembershipType.ALREADY_RECEIVE;
        }

        investMapper.sumSuccessInvestCountByLoginName(loanName);


        return null;
    }
}
