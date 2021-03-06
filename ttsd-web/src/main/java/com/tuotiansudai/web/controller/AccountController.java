package com.tuotiansudai.web.controller;

import com.tuotiansudai.coupon.service.UserCouponService;
import com.tuotiansudai.enums.Role;
import com.tuotiansudai.membership.repository.model.MembershipModel;
import com.tuotiansudai.membership.service.UserMembershipEvaluator;
import com.tuotiansudai.point.service.PointService;
import com.tuotiansudai.point.service.SignInService;
import com.tuotiansudai.repository.mapper.UserFundMapper;
import com.tuotiansudai.repository.model.AccountModel;
import com.tuotiansudai.repository.model.BankCardModel;
import com.tuotiansudai.repository.model.UserFundView;
import com.tuotiansudai.service.*;
import com.tuotiansudai.spring.LoginUserInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private LoanRepayService loanRepayService;

    @Autowired
    private InvestRepayService investRepayService;

    @Autowired
    private SignInService signInService;

    @Autowired
    private PointService pointService;

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private UserMembershipEvaluator userMembershipEvaluator;

    @Autowired
    private UserFundMapper userFundMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BindBankCardService bindBankCardService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView account() {
        ModelAndView modelAndView = new ModelAndView("/account");

        String loginName = LoginUserInfo.getLoginName();

        UserFundView userFundView = userFundMapper.findByLoginName(loginName);

        MembershipModel membershipModel = userMembershipEvaluator.evaluate(loginName);
        AccountModel accountModel = accountService.findByLoginName(loginName);
        BankCardModel bankCard = bindBankCardService.getPassedBankCard(LoginUserInfo.getLoginName());

        modelAndView.addObject("mobile", LoginUserInfo.getMobile());
        modelAndView.addObject("userMembershipLevel", membershipModel != null ? membershipModel.getLevel() : 0);

        modelAndView.addObject("balance", userFundView.getBalance()); //余额
        modelAndView.addObject("expectedTotalCorpus", userFundView.getExpectedTotalCorpus()); //待收出借本金
        modelAndView.addObject("expectedTotalInterest", userFundView.getExpectedTotalInterest()); //待收预期收益
        modelAndView.addObject("overdueTotalCorpus", userFundView.getOverdueTotalCorpus()); //逾期待收出借本金
        modelAndView.addObject("overdueTotalInterest", userFundView.getOverdueTotalInterest() + userFundView.getOverdueTotalDefaultInterest()); //逾期待收预期收益

        modelAndView.addObject("referRewardAmount", userFundView.getReferRewardAmount()); //已收推荐奖励
        modelAndView.addObject("actualTotalInterest", userFundView.getActualTotalInterest()); //已收出借收益
        //已收优惠券奖励 = 已收红包奖励+已收加息券奖励
        modelAndView.addObject("actualCouponInterest", userFundView.getActualCouponInterest() + userFundView.getRedEnvelopeAmount());

        modelAndView.addObject("actualTotalExtraInterest", userFundView.getActualTotalExtraInterest()); //已收出借奖励=阶梯加息+现金补贴
        modelAndView.addObject("expectedTotalExtraInterest", userFundView.getExpectedTotalExtraInterest()); //待收收出借奖励

        modelAndView.addObject("expectedExperienceInterest", userFundView.getExpectedExperienceInterest()); //待收体验金收益
        modelAndView.addObject("expectedCouponInterest", userFundView.getExpectedCouponInterest()); //待收优惠券收益
        modelAndView.addObject("actualExperienceInterest", userFundView.getActualExperienceInterest()); //已收体验金收益

        modelAndView.addObject("investFrozeAmount", userFundView.getInvestFrozeAmount());
        modelAndView.addObject("withdrawFrozeAmount", userFundView.getWithdrawFrozeAmount());
        modelAndView.addObject("freeze", userFundView.getInvestFrozeAmount() + userFundView.getWithdrawFrozeAmount()); //冻结金额
        modelAndView.addObject("hasAccount", accountModel != null);
        modelAndView.addObject("hasBankCard", bankCard != null);

        //累计收益(分)=已收出借收益+已收出借奖励(阶梯加息+现金补贴)+已收优惠券奖励(已收红包奖励+已收加息券奖励)+已收推荐奖励+已收体验金收益
        modelAndView.addObject("totalIncome", userFundView.getActualTotalInterest()
                + userFundView.getActualTotalExtraInterest()
                + userFundView.getActualCouponInterest()
                + userFundView.getRedEnvelopeAmount()
                + userFundView.getReferRewardAmount()
                + userFundView.getActualExperienceInterest());


        modelAndView.addObject("experienceBalance", userService.getExperienceBalanceByLoginName(loginName));

        Date firstDateOfMonth = new DateTime().dayOfMonth().withMinimumValue().toDate();
        Date lastDateOfMonth = DateUtils.addMonths(firstDateOfMonth, 1);
        if (userRoleService.judgeUserRoleExist(loginName, Role.LOANER)) {
            modelAndView.addObject("expectedRepayAmountOfMonth", loanRepayService.findByLoginNameAndTimeSuccessRepay(loginName, firstDateOfMonth, lastDateOfMonth)); //本月未还款总额
            modelAndView.addObject("repayList", loanRepayService.findLoanRepayInAccount(loginName, firstDateOfMonth, lastDateOfMonth, 0, 6));
        }

        modelAndView.addObject("actualInvestRepay", investRepayService.findByLoginNameAndTimeAndSuccessInvestRepay(loginName, firstDateOfMonth, lastDateOfMonth)); //本月已收回款总额
        modelAndView.addObject("actualInvestRepayList", investRepayService.findByLoginNameAndTimeSuccessInvestRepayList(loginName, firstDateOfMonth, lastDateOfMonth, 0, 6));

        modelAndView.addObject("expectedInvestRepay", investRepayService.findByLoginNameAndTimeAndNotSuccessInvestRepay(loginName, firstDateOfMonth, lastDateOfMonth)); //本月待收回款总额
        modelAndView.addObject("expectedInvestRepayList", investRepayService.findByLoginNameAndTimeNotSuccessInvestRepayList(loginName, firstDateOfMonth, lastDateOfMonth, 0, 6));

        modelAndView.addObject("latestInvestList", investRepayService.findLatestInvestByLoginName(loginName, 0, 6)); //最新出借项目

        modelAndView.addObject("signedIn", signInService.signInIsSuccess(loginName)); //是否签到

        modelAndView.addObject("myPoint", pointService.getAvailablePoint(loginName)); //积分

        modelAndView.addObject("isUsableCouponExist", userCouponService.isUsableUserCouponExist(loginName));
        return modelAndView;
    }

}
