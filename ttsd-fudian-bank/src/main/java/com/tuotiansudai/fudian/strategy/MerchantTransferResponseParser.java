package com.tuotiansudai.fudian.strategy;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.tuotiansudai.fudian.dto.response.MerchantTransferContentDto;
import com.tuotiansudai.fudian.dto.response.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class MerchantTransferResponseParser implements ResponseParserInterface<MerchantTransferContentDto> {

    private static Logger logger = LoggerFactory.getLogger(MerchantTransferResponseParser.class);

    @Override
    public ResponseDto<MerchantTransferContentDto> parse(String data) {
        try {
            ResponseDto<MerchantTransferContentDto> dto = gson.fromJson(data, new TypeToken<ResponseDto<MerchantTransferContentDto>>() {
            }.getType());
            dto.setReqData(data);
            return dto;
        } catch (JsonSyntaxException e) {
            logger.error(MessageFormat.format("[Response Parse] deserialize error, data is {0}", data), e);
        }

        return null;
    }
}
