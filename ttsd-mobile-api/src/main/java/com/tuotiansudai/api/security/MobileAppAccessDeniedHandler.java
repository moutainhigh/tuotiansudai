package com.tuotiansudai.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import com.tuotiansudai.api.dto.v1_0.BaseResponseDto;
import com.tuotiansudai.api.dto.v1_0.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class MobileAppAccessDeniedHandler extends AccessDeniedHandlerImpl {
    static Logger log = Logger.getLogger(MobileAppAccessDeniedHandler.class);
    @Autowired
    private MobileAppTokenProvider mobileAppTokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(request);

        String requestToken = bufferedRequest.getParameter("token");
        if (StringUtils.isEmpty(requestToken)) {
            log.debug(MessageFormat.format("[Access Denied] uri: {0} body: {1}", request.getRequestURI(), bufferedRequest.getInputStreamString()));
        } else {
            String redisLoginName = mobileAppTokenProvider.getUserNameByToken(requestToken);
            log.debug(MessageFormat.format("[Access Denied] uri: {0} body: {1} redisTokenLoginName:{2}", request.getRequestURI(), bufferedRequest.getInputStreamString(), redisLoginName));
        }

        BaseResponseDto dto = mobileAppTokenProvider.generateResponseDto(ReturnMessage.UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter out = response.getWriter();
        String jsonString = objectMapper.writeValueAsString(dto);
        out.print(jsonString);
    }
}