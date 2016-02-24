package com.tuotiansudai.jpush.service;


import com.tuotiansudai.dto.BaseDataDto;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.jpush.dto.JPushAlertDto;
import com.tuotiansudai.jpush.repository.model.JPushAlertModel;
import com.tuotiansudai.jpush.repository.model.PushStatus;
import com.tuotiansudai.jpush.repository.model.PushType;
import com.tuotiansudai.repository.model.InvestNotifyInfo;

import java.util.List;

public interface JPushAlertService {
    void buildJPushAlert(String loginName, JPushAlertDto jPushAlertDto);

    int findPushTypeCount(PushType pushType);

    int findPushAlertCount(String name, boolean isAutomatic);

    List<JPushAlertModel> findPushAlerts(int index, int pageSize, String name, boolean isAutomatic);

    JPushAlertModel findJPushAlertModelById(long id);

    void refreshPushReport(long jpushId);

    void send(String loginName, long id);

    void changeJPushAlertStatus(long id, PushStatus status, String loginName);

    void changeJPushAlertContent(long id, String content, String loginName);

    void manualJPushAlert(long id);

    void autoJPushAlertBirthMonth();

    void autoJPushAlertBirthDay();

    void autoJPushNoInvestAlert();

    void autoJPushLoanAlert(List<InvestNotifyInfo> notifyInfos);

    BaseDto<BaseDataDto> pass(String loginName, long id, String ip);

    void reject(String loginName, long id);

    void delete(String loginName, long id);

}
