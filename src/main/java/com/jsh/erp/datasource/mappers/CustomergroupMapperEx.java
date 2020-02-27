package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Customergroup;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Date: 11:41 2020/2/21
 * @Author: ChenShiGen
 * @Description:
 */
public interface CustomergroupMapperEx {
    List<Customergroup> selectByConditionCustomergroup(
            @Param("province") String province,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows
    );

    Long countsByCustomergroup(@Param("province") String province);

    int batchDeleteCustomergroupByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);
}
