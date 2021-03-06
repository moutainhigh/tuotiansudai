package com.tuotiansudai.activity.repository.mapper;

import com.tuotiansudai.activity.repository.model.WeChatHelpInfoModel;
import com.tuotiansudai.activity.repository.model.WeChatHelpInfoView;
import com.tuotiansudai.activity.repository.model.WeChatHelpUserStatus;
import com.tuotiansudai.activity.repository.model.WeChatUserInfoModel;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WeChatHelpInfoMapper {

    void create(WeChatHelpInfoModel weChatHelpInfoModel);

    void update(WeChatHelpInfoModel weChatHelpInfoModel);

    int getCountByHelpId(@Param(value = "helpId") long helpId);

    WeChatHelpInfoModel findByOpenId(@Param(value = "openId") String openId,
                                     @Param(value = "helpId") long helpId);

    int findHelpCountByOpenIdAndTime(@Param(value = "openId") String openId,
                                     @Param(value = "startTime") Date startTime,
                                     @Param(value = "endTime") Date endTime);

    List<WeChatHelpInfoView> findByNickName(@Param(value = "helpId") long helpId,
                                            @Param(value = "nickName") String nickName,
                                            @Param(value = "status") WeChatHelpUserStatus status);

    List<WeChatHelpInfoModel> findByHelpId(@Param(value = "helpId") long helpId);

}
