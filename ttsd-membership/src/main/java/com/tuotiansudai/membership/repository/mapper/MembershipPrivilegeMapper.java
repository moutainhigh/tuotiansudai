package com.tuotiansudai.membership.repository.mapper;

import com.tuotiansudai.membership.repository.model.MembershipPrivilegeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface MembershipPrivilegeMapper {
    int create(MembershipPrivilegeModel membershipPrivilegeModel);

    int update(MembershipPrivilegeModel membershipPrivilegeModel);

    MembershipPrivilegeModel findById(long id);

    MembershipPrivilegeModel findValidPrivilegeModelByLoginName(@Param(value = "loginName") String loginName,
                                                                @Param(value = "currentDate") Date currentDate);
}
