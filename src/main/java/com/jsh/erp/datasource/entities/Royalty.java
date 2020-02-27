package com.jsh.erp.datasource.entities;

import java.math.BigDecimal;

/**
 * @Date: 9:49 2020/2/24
 * @Author: ChenShiGen
 * @Description:
 */
public class Royalty {

    //主键Id
    private Long            id;
    //销售人Id
    private Long            saleId;
    //销售人
    private String          saleName;
    //批发提成
    private BigDecimal      wholesaleRoyalty;
    //零售提成
    private BigDecimal      retailRoyalty;
    //总提成
    private BigDecimal      allRoyalty;
    //时间月份 202002
    private String          monthTime;
    //状态 0未发放 1已发放
    private Boolean         status;
    //发放账户
    private Long            accountId;
    //账户名称
    private String          accountName;
    //租户id
    private Long            tenantId;
    //删除标记，0未删除，1删除
    private String          deleteFlag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public BigDecimal getWholesaleRoyalty() {
        return wholesaleRoyalty;
    }

    public void setWholesaleRoyalty(BigDecimal wholesaleRoyalty) {
        this.wholesaleRoyalty = wholesaleRoyalty;
    }

    public BigDecimal getRetailRoyalty() {
        return retailRoyalty;
    }

    public void setRetailRoyalty(BigDecimal retailRoyalty) {
        this.retailRoyalty = retailRoyalty;
    }

    public BigDecimal getAllRoyalty() {
        return allRoyalty;
    }

    public void setAllRoyalty(BigDecimal allRoyalty) {
        this.allRoyalty = allRoyalty;
    }

    public String getMonthTime() {
        return monthTime;
    }

    public void setMonthTime(String monthTime) {
        this.monthTime = monthTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
