package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Royalty;
import com.jsh.erp.datasource.entities.RoyaltyExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 16:01 2020/2/24
 * @Author: ChenShiGen
 * @Description:
 */
public interface RoyaltyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsh_royalty
     *
     * @mbggenerated
     */
    int countByExample(RoyaltyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsh_Royalty
     *
     * @mbggenerated
     */
    int deleteByExample(RoyaltyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsh_Royalty
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsh_Royalty
     *
     * @mbggenerated
     */
    int insert(Royalty record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsh_Royalty
     *
     * @mbggenerated
     */
    int insertSelective(Royalty record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsh_Royalty
     *
     * @mbggenerated
     */
    List<Royalty> selectByExample(RoyaltyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsh_Royalty
     *
     * @mbggenerated
     */
    Royalty selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsh_Royalty
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Royalty record, @Param("example") RoyaltyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsh_Royalty
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Royalty record, @Param("example") RoyaltyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsh_Royalty
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Royalty record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table jsh_Royalty
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Royalty record);

    List<Royalty> selectByTimeCount(@Param("monthTime") String monthTime);
}