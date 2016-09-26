package com.tuotiansudai.client;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.SmsDataDto;
import com.tuotiansudai.dto.sms.SmsCaptchaDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SmsWrapperClientTest {

    @Autowired
    private SmsWrapperClient smsWrapperClient;

    private MockWebServer server;

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
    @Transactional
    public void shouldSendCaptchaIsOk() throws Exception {
        MockResponse mockResponse = new MockResponse();
        String jsonString = "{\"success\":true,\"data\":{\"status\":true}}";
        mockResponse.setBody(jsonString);
        server.enqueue(mockResponse);
        smsWrapperClient.setHost(server.getHostName());
        smsWrapperClient.setPort(String.valueOf(server.getPort()));
        smsWrapperClient.setApplicationContext("");
        BaseDto<SmsDataDto> resultDto = this.smsWrapperClient.sendRegisterCaptchaSms(new SmsCaptchaDto("13900000000", "1000", "127.0.0.1"));

        assertNotNull(resultDto);
        assertTrue(resultDto.getData().getStatus());
    }
}
