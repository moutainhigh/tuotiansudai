package com.tuotiansudai.service;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.tuotiansudai.client.SmsClient;
import com.tuotiansudai.repository.mapper.SmsCaptchaMapper;
import com.tuotiansudai.repository.model.CaptchaStatus;
import com.tuotiansudai.repository.model.CaptchaType;
import com.tuotiansudai.repository.model.SmsCaptchaModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration
@Transactional
public class SmsCaptchaServiceTest {
    private MockWebServer server;

    @Autowired
    private SmsCaptchaMapper smsCaptchaMapper;

    @Autowired
    private SmsClient smsClient;

    @Autowired
    private SmsCaptchaService smsCaptchaService;

    @Before
    public void setUp() throws Exception {
        this.server = new MockWebServer();
        this.server.start();
    }

    @After
    public void tearDown() throws Exception {
        this.server.shutdown();
    }

    @Test
    public void shouldSendRegisterCaptchaIsOk() throws Exception {
        MockResponse mockResponse = new MockResponse();
        String jsonString = "{\"success\":true,\"data\":{\"status\":true}}";
        mockResponse.setBody(jsonString);
        server.enqueue(mockResponse);
        smsClient.setHost("http://" + server.getHostName() + ":" + server.getPort());
        String mobile = "13900000000";
        String code = "1000";

        boolean result = smsCaptchaService.sendRegisterCaptcha("13900000001", "123");

        assertTrue(result);
    }

    @Test
    public void shouldSendRegisterCaptchaIsFailed() throws Exception {
        MockResponse mockResponse = new MockResponse();
        String jsonString = "{\"success\":true,\"data\":{\"status\":false}}";
        mockResponse.setBody(jsonString);
        server.enqueue(mockResponse);
        smsClient.setHost("http://" + server.getHostName() + ":" + server.getPort());
        String mobile = "13900000000";
        String code = "1000";

        boolean result = smsCaptchaService.sendRegisterCaptcha("13900000001", "123");

        assertFalse(result);
    }

    @Test
    public void shouldCreateRegisterCaptchaIsOk() {
        smsCaptchaService.createSmsCaptcha("13900000000");
        SmsCaptchaModel smsCaptchaModel = new SmsCaptchaModel();
        smsCaptchaModel.setMobile("13900000000");
        smsCaptchaModel.setStatus(CaptchaStatus.ACTIVATED);
        smsCaptchaModel.setCaptchaType(CaptchaType.MOBILECAPTCHA);

        SmsCaptchaModel smsCaptchaModel1 = smsCaptchaMapper.findCaptchabyMobile(smsCaptchaModel);

        assertNotNull(smsCaptchaModel1);
        System.out.println(smsCaptchaModel1.getCode());
        assertEquals("13900000000", smsCaptchaModel1.getMobile());
    }
    @Test
    public void shouldModifyRegisterCaptchaIsOk(){
        SmsCaptchaModel smsCaptchaModel = this.getSmsCaptchaModeal();
        smsCaptchaMapper.insertSmsCaptcha(smsCaptchaModel);
        smsCaptchaService.createSmsCaptcha("13900000000");

        SmsCaptchaModel smsCaptchaModel1 = smsCaptchaMapper.findCaptchabyMobile(smsCaptchaModel);
        assertNotNull(smsCaptchaModel1);
        System.out.println(smsCaptchaModel1.getCode());
        assertNotEquals("12345", smsCaptchaModel1.getCode());

    }
    @Test
    public void shouldGenerateCaptchaIsOk(){
        String captcha = smsCaptchaService.createRandomVcode(6);
        assertNotNull(captcha);
    }

    @Test
    public void shouldVerifyMobileNumberIsOk() {
        assertTrue(smsCaptchaService.verifyMobileNumber("13900000000"));
    }

    @Test
    public void shouldVerifyMobileNumberIsInvalid() {
        assertFalse(smsCaptchaService.verifyMobileNumber("aaaa"));
        assertFalse(smsCaptchaService.verifyMobileNumber(""));
    }

    @Test
    public void shouldSendSmsbyMobileNumberRegisterIsOk() throws Exception {
        MockResponse mockResponse = new MockResponse();
        String jsonString = "{\"success\":true,\"data\":{\"status\":true}}";
        mockResponse.setBody(jsonString);
        server.enqueue(mockResponse);
        smsClient.setHost("http://" + server.getHostName() + ":" + server.getPort());
        boolean result = smsCaptchaService.sendSmsbyMobileNumberRegister("13900000000");

        SmsCaptchaModel smsCaptchaModelQuery = new SmsCaptchaModel();
        smsCaptchaModelQuery.setMobile("13900000000");
        smsCaptchaModelQuery.setCode("1000");
        smsCaptchaModelQuery.setStatus(CaptchaStatus.ACTIVATED);
        smsCaptchaModelQuery.setCaptchaType(CaptchaType.MOBILECAPTCHA);
        SmsCaptchaModel smsCaptchaModel = smsCaptchaMapper.findCaptchabyMobile(smsCaptchaModelQuery);

        assertTrue(result);
        assertNotNull(smsCaptchaModel);

    }

    @Test
    public void shouldSendSmsbyMobileNumberRegisterIsFail() throws Exception {
        MockResponse mockResponse = new MockResponse();
        String jsonString = "{\"success\":true,\"data\":{\"status\":false}}";
        mockResponse.setBody(jsonString);
        server.enqueue(mockResponse);
        smsClient.setHost("http://" + server.getHostName() + ":" + server.getPort());
        boolean result = smsCaptchaService.sendSmsbyMobileNumberRegister("13900000000");

        SmsCaptchaModel smsCaptchaModelQuery = new SmsCaptchaModel();
        smsCaptchaModelQuery.setMobile("13900000000");
        smsCaptchaModelQuery.setCode("1000");
        smsCaptchaModelQuery.setStatus(CaptchaStatus.ACTIVATED);
        smsCaptchaModelQuery.setCaptchaType(CaptchaType.MOBILECAPTCHA);
        SmsCaptchaModel smsCaptchaModel = smsCaptchaMapper.findCaptchabyMobile(smsCaptchaModelQuery);

        assertFalse(result);
        assertNotNull(smsCaptchaModel);

    }
    @Test
    public void shouldVerifyCaptchaIsNotExisted(){
        SmsCaptchaModel SmsCaptchaModelQuery = new SmsCaptchaModel();
        SmsCaptchaModelQuery.setMobile("13900000000");
        SmsCaptchaModelQuery.setCode("1000");
        SmsCaptchaModelQuery.setCaptchaType(CaptchaType.MOBILECAPTCHA);
        SmsCaptchaModelQuery.setStatus(CaptchaStatus.ACTIVATED);
        SmsCaptchaModel smsCaptchaModel = smsCaptchaMapper.findSmsCaptchaByMobileAndCaptcha(SmsCaptchaModelQuery);

        assertNull(smsCaptchaModel);


    }

    @Test
    public void shouldVerifyCaptchaIsExisted(){
        SmsCaptchaModel smsCaptchaModel1 = this.getSmsCaptchaModeal();
        smsCaptchaMapper.insertSmsCaptcha(smsCaptchaModel1);
        SmsCaptchaModel SmsCaptchaModelQuery = new SmsCaptchaModel();
        SmsCaptchaModelQuery.setMobile("13900000000");
        SmsCaptchaModelQuery.setCode("12345");
        SmsCaptchaModelQuery.setCaptchaType(CaptchaType.MOBILECAPTCHA);
        SmsCaptchaModelQuery.setStatus(CaptchaStatus.ACTIVATED);
        SmsCaptchaModel smsCaptchaModel = smsCaptchaMapper.findSmsCaptchaByMobileAndCaptcha(SmsCaptchaModelQuery);

        assertNotNull(smsCaptchaModel);


    }


    public SmsCaptchaModel getSmsCaptchaModeal() {
        SmsCaptchaModel smsCaptchaModel = new SmsCaptchaModel();
        smsCaptchaModel.setCode("12345");
        smsCaptchaModel.setMobile("13900000000");
        smsCaptchaModel.setDeadLine(new Date());
        smsCaptchaModel.setGenerationTime(new Date());
        smsCaptchaModel.setStatus(CaptchaStatus.ACTIVATED);
        smsCaptchaModel.setCaptchaType(CaptchaType.MOBILECAPTCHA);
        smsCaptchaModel.setUserId(100001);
        return smsCaptchaModel;
    }


}