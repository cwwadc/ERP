package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.Customergroup;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.service.customergroup.CustomergroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Date: 11:13 2020/2/21
 * @Author: ChenShiGen
 * @Description:
 */
@RestController
@RequestMapping(value = "/customergroup")
public class CustomergroupController {
    private Logger logger = LoggerFactory.getLogger(CustomergroupController.class);

    @Resource
    private CustomergroupService customergroupService;

    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  批量删除支付方式信息
     * create time: 2019/3/29 11:15
     * @Param: ids
     * @return java.lang.Object
     */
    @RequestMapping(value = "/batchDeletecustomergroupByIds")
    public Object batchDeleteCustomergroupByIds(@RequestParam("ids") String ids, @RequestParam(value="deleteType",
            required =false,defaultValue= BusinessConstants.DELETE_TYPE_NORMAL)String deleteType) throws Exception {
        JSONObject result = ExceptionConstants.standardSuccess();
        int i=0;
        if(BusinessConstants.DELETE_TYPE_NORMAL.equals(deleteType)){
            i= customergroupService.batchDeleteCustomergroupByIdsNormal(ids);
        }else if(BusinessConstants.DELETE_TYPE_FORCE.equals(deleteType)){
            i= customergroupService.batchDeleteCustomergroupByIds(ids);
        }else{
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}],deleteType[{}]",
                    ExceptionConstants.DELETE_REFUSED_CODE,ExceptionConstants.DELETE_REFUSED_MSG,ids,deleteType);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_REFUSED_CODE,
                    ExceptionConstants.DELETE_REFUSED_MSG);
        }
        if(i<1){
            logger.error("异常码[{}],异常提示[{}],参数,ids[{}]",
                    ExceptionConstants.Customergroup_DELETE_FAILED_CODE,ExceptionConstants.Customergroup_DELETE_FAILED_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.Customergroup_DELETE_FAILED_CODE,
                    ExceptionConstants.Customergroup_DELETE_FAILED_MSG);
        }
        return result;
    }


    /**
     * 查找客户组-下拉框
     * @param request
     * @return
     */
    @PostMapping(value = "/findBySelect_sup")
    public JSONArray findBySelectSup(HttpServletRequest request) throws Exception{
        JSONArray arr = new JSONArray();
        try {
            List<Customergroup> customergroupList = customergroupService.getCustomergroup();
            JSONArray dataArray = new JSONArray();
            if (null != customergroupList) {
                for (Customergroup customergroup : customergroupList) {
                    JSONObject item = new JSONObject();
                    item.put("id", customergroup.getId());
                    //客户组省名称
                    item.put("province", customergroup.getProvince());
                    dataArray.add(item);
                }
            }
            arr = dataArray;
        } catch(Exception e){
            e.printStackTrace();
        }
        return arr;
    }


}
