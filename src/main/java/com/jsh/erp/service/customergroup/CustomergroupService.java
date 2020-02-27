package com.jsh.erp.service.customergroup;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.AccountHeadMapperEx;
import com.jsh.erp.datasource.mappers.CustomergroupMapper;
import com.jsh.erp.datasource.mappers.CustomergroupMapperEx;
import com.jsh.erp.datasource.mappers.DepotHeadMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Date: 11:21 2020/2/21
 * @Author: ChenShiGen
 * @Description:
 */
@Service
public class CustomergroupService {
    private Logger logger = LoggerFactory.getLogger(CustomergroupService.class);

    @Resource
    private CustomergroupMapper customergroupMapper;
    @Resource
    private CustomergroupMapperEx customergroupMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;
    @Resource
    private AccountHeadMapperEx accountHeadMapperEx;
    @Resource
    private DepotHeadMapperEx depotHeadMapperEx;


    public Customergroup getCustomergroup(long id)throws Exception {
        Customergroup result=null;
        try{
            result=customergroupMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<Customergroup> getCustomergroup()throws Exception {
        CustomergroupExample example = new CustomergroupExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Customergroup> list=null;
        try{
            list=customergroupMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Customergroup> select(String province, int offset, int rows)throws Exception {
        List<Customergroup> list=null;
        try{
            list=customergroupMapperEx.selectByConditionCustomergroup(province, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long countCustomergroup(String province)throws Exception {
        Long result=null;
        try{
            result=customergroupMapperEx.countsByCustomergroup(province);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertCustomergroup(String beanJson, HttpServletRequest request)throws Exception {
        Customergroup customergroup = JSONObject.parseObject(beanJson, Customergroup.class);
        int result=0;
        try{
            result=customergroupMapper.insertSelective(customergroup);
            logService.insertLog("客户组", BusinessConstants.LOG_OPERATION_TYPE_ADD, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateCustomergroup(String beanJson, Long id, HttpServletRequest request)throws Exception {
        Customergroup customergroup = JSONObject.parseObject(beanJson, Customergroup.class);
        customergroup.setId(id);
        int result=0;
        try{
            result=customergroupMapper.updateByPrimaryKeySelective(customergroup);
            logService.insertLog("客户组",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteCustomergroup(Long id, HttpServletRequest request)throws Exception {
        int result=0;
        try{
            result=customergroupMapper.deleteByPrimaryKey(id);
            logService.insertLog("客户组",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteCustomergroup(String ids, HttpServletRequest request) throws Exception{
        List<Long> idList = StringUtil.strToLongList(ids);
        CustomergroupExample example = new CustomergroupExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            result=customergroupMapper.deleteByExample(example);
            logService.insertLog("客户组", "批量删除,id集:" + ids, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String province) throws Exception{
        CustomergroupExample example = new CustomergroupExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(province).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Customergroup> list =null;
        try{
            list=customergroupMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    public String getCustomergroupByIds(String customergroupIDs)throws Exception {
        List<Long> ids = StringUtil.strToLongList(customergroupIDs);
        CustomergroupExample example = new CustomergroupExample();
        example.createCriteria().andIdIn(ids).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Id asc");
        List<Customergroup> list =null;
        try{
            list=customergroupMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        StringBuffer sb = new StringBuffer();
        if (null != list) {
            for (Customergroup customergroup : list) {
                sb.append(customergroup.getProvince() + " ");
            }
        }
        return  sb.toString();
    }

    public List<Customergroup> getCustomergroupByType(String type)throws Exception {
        CustomergroupExample example = new CustomergroupExample();
        example.createCriteria().andTypeEqualTo(type).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Id asc");
        List<Customergroup> list =null;
        try{
            list=customergroupMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteCustomergroupByIds(String ids)throws Exception {
        logService.insertLog("客户组",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int result =0;
        try{
            result=customergroupMapperEx.batchDeleteCustomergroupByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }
    /**
     * create by: qiankunpingtai
     * website：https://qiankunpingtai.cn
     * description:
     *  正常删除，要考虑数据完整性，进行完整性校验
     * create time: 2019/4/10 15:14
     * @Param: ids
     * @return int
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteCustomergroupByIdsNormal(String ids) throws Exception {
        /**
         * 校验
         * 1、财务主表	jsh_accounthead
         * 2、单据主表	jsh_depothead
         * 是否有相关数据
         * */
        int deleteTotal=0;
        if(StringUtils.isEmpty(ids)){
            return deleteTotal;
        }
        String [] idArray=ids.split(",");
        /**
         * 校验财务主表	jsh_accounthead
         * */
        List<AccountHead> accountHeadList =null;
        try{
            //accountHeadList=accountHeadMapperEx.getAccountHeadListByHandsCustomergroupIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(accountHeadList!=null&&accountHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,HandsCustomergroupIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        /**
         * 校验单据主表	jsh_depothead
         * */
        List<DepotHead> depotHeadList =null;
        try{
            //depotHeadList=depotHeadMapperEx.getDepotHeadListByHandsCustomergroupIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(depotHeadList!=null&&depotHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,HandsCustomergroupIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        /**
         * 校验通过执行删除操作
         * */
        deleteTotal= batchDeleteCustomergroupByIds(ids);
        return deleteTotal;
    }

}
