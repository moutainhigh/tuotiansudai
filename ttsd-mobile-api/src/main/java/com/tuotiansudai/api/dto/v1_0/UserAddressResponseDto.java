package com.tuotiansudai.api.dto.v1_0;


public class UserAddressResponseDto extends BaseResponseDataDto{

    private String contract;
    private String mobile;
    private String address;

    public UserAddressResponseDto(String contract, String mobile, String address) {
        this.contract = contract;
        this.mobile = mobile;
        this.address = address;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
