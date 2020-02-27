package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.DepotHead;
import com.jsh.erp.datasource.entities.DepotHeadExample;
import java.util.List;
import java.util.Map;

import com.jsh.erp.datasource.vo.SaleDataVo;
import org.apache.ibatis.annotations.Param;

public interface DepotHeadMapper {
    long countByExample(DepotHeadExample example);

    int deleteByExample(DepotHeadExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DepotHead record);

    int insertSelective(DepotHead record);

    List<DepotHead> selectByExample(DepotHeadExample example);

    DepotHead selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DepotHead record, @Param("example") DepotHeadExample example);

    int updateByExample(@Param("record") DepotHead record, @Param("example") DepotHeadExample example);

    int updateByPrimaryKeySelective(DepotHead record);

    int updateByPrimaryKey(DepotHead record);

    List<SaleDataVo> selectBySaleAndTime(@Param("saleId") Long saleId, @Param("monthTime") String monthTime);

    List<Map<String, Object>> selectByTime(@Param("monthTime") String monthTime);
}