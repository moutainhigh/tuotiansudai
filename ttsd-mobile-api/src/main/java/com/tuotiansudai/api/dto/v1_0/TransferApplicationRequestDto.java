package com.tuotiansudai.api.dto.v1_0;

import com.tuotiansudai.repository.model.TransferStatus;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TransferApplicationRequestDto extends BaseParamDto {

    private Integer index;
    private Integer pageSize;

    @ApiModelProperty(value = "债权转让类型", example = "[TRANSFERABLE,TRANSFERRING]")
    private List<TransferStatus> transferStatus;

    public Integer  getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<TransferStatus> getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(List<TransferStatus> transferStatus) {
        this.transferStatus = transferStatus;
    }
}
