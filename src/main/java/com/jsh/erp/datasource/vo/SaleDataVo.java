package com.jsh.erp.datasource.vo;

import java.math.BigDecimal;

/**
 * @Date: 14:17 2020/2/25
 * @Author: ChenShiGen
 * @Description:
 */
public class SaleDataVo {
    //单据编号
    private String depotheadNum;
    //商品信息
    private String goodsInfo;
    //单据日期
    private String depotheadTime;
    //单据金额
    private double depotheadAmount;
    //提成金额
    private double royaltyAmount;
    //销售
    private String salesman;


    public String getDepotheadNum() {
        return depotheadNum;
    }

    public void setDepotheadNum(String depotheadNum) {
        this.depotheadNum = depotheadNum;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getDepotheadTime() {
        return depotheadTime;
    }

    public void setDepotheadTime(String depotheadTime) {
        this.depotheadTime = depotheadTime;
    }

    public double getDepotheadAmount() {
        return depotheadAmount;
    }

    public void setDepotheadAmount(double depotheadAmount) {
        this.depotheadAmount = depotheadAmount;
    }

    public double getRoyaltyAmount() {
        return royaltyAmount;
    }

    public void setRoyaltyAmount(double royaltyAmount) {
        this.royaltyAmount = royaltyAmount;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }
}
