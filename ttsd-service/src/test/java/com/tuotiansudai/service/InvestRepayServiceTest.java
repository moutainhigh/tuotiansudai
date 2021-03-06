package com.tuotiansudai.service;

import com.google.common.collect.Lists;
import com.tuotiansudai.dto.InvestRepayDataItemDto;
import com.tuotiansudai.dto.InvestorInvestPaginationItemDataDto;
import com.tuotiansudai.dto.LoanDto;
import com.tuotiansudai.repository.mapper.FakeUserHelper;
import com.tuotiansudai.repository.mapper.InvestMapper;
import com.tuotiansudai.repository.mapper.InvestRepayMapper;
import com.tuotiansudai.repository.mapper.LoanMapper;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.util.IdGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})@Transactional
public class InvestRepayServiceTest {

    @Autowired
    private InvestRepayService investRepayService;

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private FakeUserHelper userMapper;

    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private InvestRepayMapper investRepayMapper;

    private void createLoanByUserId(String userId, long loanId) {
        LoanDto loanDto = new LoanDto();
        loanDto.setLoanerLoginName(userId);
        loanDto.setLoanerUserName("借款人");
        loanDto.setLoanerIdentityNumber("111111111111111111");
        loanDto.setAgentLoginName(userId);
        loanDto.setBasicRate("16.00");
        loanDto.setId(loanId);
        loanDto.setProjectName("店铺资金周转");
        loanDto.setActivityRate("12");
        loanDto.setShowOnHome(true);
        loanDto.setPeriods(30);
        loanDto.setActivityType(ActivityType.NORMAL);
        loanDto.setContractId(123);
        loanDto.setDescriptionHtml("asdfasdf");
        loanDto.setDescriptionText("asdfasd");
        loanDto.setFundraisingEndTime(new Date());
        loanDto.setFundraisingStartTime(new Date());
        loanDto.setInvestIncreasingAmount("1");
        loanDto.setLoanAmount("10000");
        loanDto.setType(LoanType.INVEST_INTEREST_MONTHLY_REPAY);
        loanDto.setMaxInvestAmount("100000000000");
        loanDto.setMinInvestAmount("0");
        loanDto.setCreatedTime(new Date());
        loanDto.setLoanStatus(LoanStatus.REPAYING);
        loanDto.setProductType(ProductType._30);
        loanDto.setPledgeType(PledgeType.HOUSE);
        LoanModel loanModel = new LoanModel(loanDto);
        loanMapper.create(loanModel);
        loanModel.setStatus(LoanStatus.REPAYING);
        loanMapper.update(loanModel);
    }

    private void createUserByUserId(String userId) {
        UserModel userModelTest = new UserModel();
        userModelTest.setLoginName(userId);
        userModelTest.setPassword("123abc");
        userModelTest.setEmail("12345@abc.com");
        userModelTest.setMobile("1" + RandomStringUtils.randomNumeric(10));
        userModelTest.setRegisterTime(new Date());
        userModelTest.setStatus(UserStatus.ACTIVE);
        userModelTest.setSalt(UUID.randomUUID().toString().replaceAll("-", ""));
        userMapper.create(userModelTest);
    }

    private void createInvest(String loginName, long loanId, long investId) {
        InvestModel model = new InvestModel(investId, loanId, null, 1, loginName, new Date(), Source.WEB, null, 0.1);
        model.setStatus(InvestStatus.SUCCESS);
        investMapper.create(model);
    }

    private void createInvestRepay(long investId, RepayStatus repayStatus, int period) {
        List<InvestRepayModel> investRepayModelList = Lists.newArrayList();
        InvestRepayModel investRepayModel = new InvestRepayModel(IdGenerator.generate(), investId, period, 100, 100, 100, new DateTime().withTimeAtStartOfDay().plusDays(1).minusSeconds(1).toDate(), repayStatus);
        investRepayModel.setActualInterest(100);
        investRepayModel.setActualFee(100);
        investRepayModel.setDefaultInterest(100);
        investRepayModel.setActualRepayDate(new Date());
        investRepayModelList.add(investRepayModel);
        investRepayMapper.create(investRepayModelList);
    }

    @Before
    public void init() throws Exception {
        long loanId = IdGenerator.generate();
        createUserByUserId("testuser123");
        createLoanByUserId("testuser123", loanId);
        long investId = IdGenerator.generate();
        createInvest("testuser123", loanId, investId);
        createInvestRepay(investId, RepayStatus.COMPLETE, 1);
        createInvestRepay(investId, RepayStatus.OVERDUE, 2);
        createInvestRepay(investId, RepayStatus.REPAYING, 3);
    }

    @Test
    public void investRepayAmount() {
        Date startTime = new DateTime().withTimeAtStartOfDay().dayOfMonth().withMinimumValue().toDate();
        Date endTime = DateUtils.addMonths(startTime, 1);
        long notSuccessInvestRepay = investRepayService.findByLoginNameAndTimeAndNotSuccessInvestRepay("testuser123", startTime, endTime);
        assertThat(notSuccessInvestRepay, is(400L));

        long successInvestRepay = investRepayService.findByLoginNameAndTimeAndSuccessInvestRepay("testuser123", startTime, endTime);
        assertThat(successInvestRepay, is(100l));
    }

    @Test
    public void investRepayList() {
        Date startTime = new DateTime().withTimeAtStartOfDay().dayOfMonth().withMinimumValue().toDate();
        Date endTime = DateUtils.addMonths(startTime, 1);
        List<InvestRepayDataItemDto> investRepayModels = investRepayService.findByLoginNameAndTimeNotSuccessInvestRepayList("testuser123", startTime, endTime, 0, 6);

        assertThat(investRepayModels.size(), is(2));

        List<InvestRepayDataItemDto> investRepayModelList = investRepayService.findByLoginNameAndTimeSuccessInvestRepayList("testuser123", startTime, endTime, 0, 6);
        assertThat(investRepayModelList.size(), is(1));
    }
}
