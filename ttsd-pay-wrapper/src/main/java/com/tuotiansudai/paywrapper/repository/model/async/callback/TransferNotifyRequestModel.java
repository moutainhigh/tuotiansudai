package com.tuotiansudai.paywrapper.repository.model.async.callback;

public class TransferNotifyRequestModel extends BaseCallbackRequestModel {

    private String merCheckDate;

    public String getMerCheckDate() {
        return merCheckDate;
    }

    public void setMerCheckDate(String merCheckDate) {
        this.merCheckDate = merCheckDate;
    }

}
