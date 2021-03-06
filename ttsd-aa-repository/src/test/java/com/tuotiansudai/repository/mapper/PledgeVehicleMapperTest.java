package com.tuotiansudai.repository.mapper;

import com.tuotiansudai.repository.model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})@Transactional
public class PledgeVehicleMapperTest {
    @Autowired
    FakeUserHelper userMapper;

    @Autowired
    LoanMapper loanMapper;

    @Autowired
    PledgeVehicleMapper pledgeVehicleMapper;

    private UserModel createUserModel() {
        UserModel userModel = new UserModel();
        userModel.setLoginName("loginName");
        userModel.setMobile(RandomStringUtils.randomNumeric(11));
        userModel.setPassword("password");
        userModel.setSalt("salt");
        userModel.setRegisterTime(new Date());
        userModel.setStatus(UserStatus.ACTIVE);
        userMapper.create(userModel);
        return userModel;
    }

    private LoanModel createLoanModel(long loanId) {
        LoanModel loanModel = new LoanModel();
        loanModel.setId(loanId);
        loanModel.setName("loanName");
        loanModel.setLoanerLoginName("loginName");
        loanModel.setLoanerUserName("借款人");
        loanModel.setLoanerIdentityNumber("111111111111111111");
        loanModel.setAgentLoginName("loginName");
        loanModel.setType(LoanType.INVEST_INTEREST_MONTHLY_REPAY);
        loanModel.setPeriods(3);
        loanModel.setStatus(LoanStatus.RAISING);
        loanModel.setActivityType(ActivityType.NORMAL);
        loanModel.setFundraisingStartTime(new Date());
        loanModel.setFundraisingEndTime(new Date());
        loanModel.setDescriptionHtml("html");
        loanModel.setDescriptionText("text");
        loanModel.setPledgeType(PledgeType.HOUSE);
        loanModel.setCreatedTime(new Date());
        loanMapper.create(loanModel);
        return loanModel;
    }

    private void prepareData() {
        createUserModel();
        createLoanModel(9999L);
    }

    @Test
    public void testCreateAndGetPledgeVehicleDetailByLoanId() throws Exception {
        prepareData();

        PledgeVehicleModel pledgeVehicleModel = new PledgeVehicleModel(9999L, "pledgeLocation", "estimateAmount", "loanAmount",
                "brand", "model");

        pledgeVehicleMapper.create(pledgeVehicleModel);
        List<PledgeVehicleModel> findPledgeVehicleModel = pledgeVehicleMapper.getByLoanId(pledgeVehicleModel.getLoanId());
        assertNotNull(findPledgeVehicleModel);
        assertEquals(pledgeVehicleModel.getLoanId(), findPledgeVehicleModel.get(0).getLoanId());
        assertEquals(pledgeVehicleModel.getPledgeLocation(), findPledgeVehicleModel.get(0).getPledgeLocation());
        assertEquals(pledgeVehicleModel.getEstimateAmount(), findPledgeVehicleModel.get(0).getEstimateAmount());
        assertEquals(pledgeVehicleModel.getLoanAmount(), findPledgeVehicleModel.get(0).getLoanAmount());
        assertEquals(pledgeVehicleModel.getBrand(), findPledgeVehicleModel.get(0).getBrand());
        assertEquals(pledgeVehicleModel.getModel(), findPledgeVehicleModel.get(0).getModel());
    }

    @Test
    public void testUpdateByLoanId() throws Exception {
        prepareData();
        PledgeVehicleModel pledgeVehicleModel = new PledgeVehicleModel(9999L, "pledgeLocation", "estimateAmount", "loanAmount",
                "brand", "model");
        pledgeVehicleMapper.create(pledgeVehicleModel);

        pledgeVehicleModel.setPledgeLocation("updateLocation");
        pledgeVehicleModel.setEstimateAmount("updateValue");
        pledgeVehicleModel.setLoanAmount("updateLoanAmount");
        pledgeVehicleModel.setModel("updateModel");
        pledgeVehicleModel.setBrand("updateBrand");

        pledgeVehicleMapper.updateByLoanId(pledgeVehicleModel);
        List<PledgeVehicleModel> findPledgeVehicleModel = pledgeVehicleMapper.getByLoanId(pledgeVehicleModel.getLoanId());
        assertNotNull(findPledgeVehicleModel);
        assertEquals(pledgeVehicleModel.getLoanId(), findPledgeVehicleModel.get(0).getLoanId());
        assertEquals(pledgeVehicleModel.getPledgeLocation(), findPledgeVehicleModel.get(0).getPledgeLocation());
        assertEquals(pledgeVehicleModel.getEstimateAmount(), findPledgeVehicleModel.get(0).getEstimateAmount());
        assertEquals(pledgeVehicleModel.getLoanAmount(), findPledgeVehicleModel.get(0).getLoanAmount());
        assertEquals(pledgeVehicleModel.getBrand(), findPledgeVehicleModel.get(0).getBrand());
        assertEquals(pledgeVehicleModel.getModel(), findPledgeVehicleModel.get(0).getModel());
    }

    @Test
    public void testDeleteByLoanId() throws Exception {
        prepareData();
        PledgeVehicleModel pledgeVehicleModel = new PledgeVehicleModel(9999L, "pledgeLocation", "estimateAmount", "loanAmount",
                "brand", "model");
        pledgeVehicleMapper.create(pledgeVehicleModel);
        assertNotNull(pledgeVehicleMapper.getByLoanId(pledgeVehicleModel.getLoanId()));

        pledgeVehicleMapper.deleteByLoanId(pledgeVehicleModel.getLoanId());
        assertEquals(0,pledgeVehicleMapper.getByLoanId(pledgeVehicleModel.getLoanId()).size());
    }
}
