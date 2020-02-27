package com.jsh.erp.datasource.entities;

/**
 * @Date: 16:40 2020/2/15
 * @Author: ChenShiGen
 * @Description:
 */
public class Paytype {

    private Long id;
    private String payMethod;
    private Long tenantId;
    private String deleteFlag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
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
}
