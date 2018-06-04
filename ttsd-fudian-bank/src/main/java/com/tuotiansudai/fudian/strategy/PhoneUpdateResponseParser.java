package com.tuotiansudai.fudian.strategy;

import com.google.common.base.Strings;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tuotiansudai.fudian.dto.response.PhoneUpdateContentDto;
import com.tuotiansudai.fudian.dto.response.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class PhoneUpdateResponseParser implements ResponseParserInterface<PhoneUpdateContentDto> {

    private static Logger logger = LoggerFactory.getLogger(PhoneUpdateResponseParser.class);

    @Override
    public ResponseDto<PhoneUpdateContentDto> parse(String data) {
        if (Strings.isNullOrEmpty(data)) {
            return null;
        }
        try {
            ResponseDto<PhoneUpdateContentDto> dto = gson.fromJson(data, new TypeToken<ResponseDto<PhoneUpdateContentDto>>() {
            }.getType());
            dto.setReqData(data);
            return dto;
        } catch (JsonSyntaxException e) {
            logger.error(MessageFormat.format("[Response Parse] failed to deserialize, data is {0}", data), e);
        }

        return null;
    }
}
