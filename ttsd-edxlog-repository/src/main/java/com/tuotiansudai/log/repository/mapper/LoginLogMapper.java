package com.tuotiansudai.log.repository.mapper;

import com.tuotiansudai.log.repository.model.LoginLogModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginLogMapper {

    void create(@Param("table") String table,
                @Param("model") LoginLogModel model);

    LoginLogModel findById(@Param("table") String table,
                           @Param("id") long id);

    long count(@Param("mobile") String mobile,
               @Param("success") Boolean success,
               @Param("table") String table);

    List<LoginLogModel> getPaginationData(@Param("mobile") String mobile,
                                          @Param("success") Boolean success,
                                          @Param("index") long index,
                                          @Param("pageSize") long pageSize,
                                          @Param("table") String table);

}
