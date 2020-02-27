package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Royalty;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Date: 16:01 2020/2/24
 * @Author: ChenShiGen
 * @Description:
 */
public interface RoyaltyMapperEx {

    List<Royalty> selectByConditionRoyalty(
            @Param("monthTime") String monthTime,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    Long countsByRoyalty(@Param("monthTime") String monthTime);




}
