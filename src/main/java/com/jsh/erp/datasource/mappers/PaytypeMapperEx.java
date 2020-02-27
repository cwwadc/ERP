package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Paytype;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Date: 18:30 2020/2/15
 * @Author: ChenShiGen
 * @Description:
 */
public interface PaytypeMapperEx {
    List<Paytype> selectByConditionPaytype(
            @Param("payMethod") String payMethod,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows
    );

    Long countsByPaytype(@Param("payMethod") String payMethod);

    int batchDeletePaytypeByIds(@Param("updateTime") Date updateTime, @Param("updater") Long updater, @Param("ids") String ids[]);
}
