package com.jsh.erp.service.paytype;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.ExceptionConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.AccountHeadMapperEx;
import com.jsh.erp.datasource.mappers.DepotHeadMapperEx;
import com.jsh.erp.datasource.mappers.PaytypeMapper;
import com.jsh.erp.datasource.mappers.PaytypeMapperEx;
import com.jsh.erp.exception.BusinessRunTimeException;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.service.user.UserService;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Date: 17:20 2020/2/15
 * @Author: ChenShiGen
 * @Description:
 */
@Service
public class PaytypeService {
    private Logger logger = LoggerFactory.getLogger(PaytypeService.class);

    @Resource
    private PaytypeMapper paytypeMapper;
    @Resource
    private PaytypeMapperEx paytypeMapperEx;
    @Resource
    private UserService userService;
    @Resource
    private LogService logService;
    @Resource
    private AccountHeadMapperEx accountHeadMapperEx;
    @Resource
    private DepotHeadMapperEx depotHeadMapperEx;


    public Paytype getPaytype(long id)throws Exception {
        Paytype result=null;
        try{
            result=paytypeMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<Paytype> getPaytype()throws Exception {
        PaytypeExample example = new PaytypeExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Paytype> list=null;
        try{
            list=paytypeMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Paytype> select(String payMethod, int offset, int rows)throws Exception {
        List<Paytype> list=null;
        try{
            list=paytypeMapperEx.selectByConditionPaytype(payMethod, offset, rows);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public Long countPaytype(String payMethod)throws Exception {
        Long result=null;
        try{
            result=paytypeMapperEx.countsByPaytype(payMethod);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertPaytype(String beanJson, HttpServletRequest request)throws Exception {
        Paytype paytype = JSONObject.parseObject(beanJson, Paytype.class);
        int result=0;
        try{
            result=paytypeMapper.insertSelective(paytype);
            logService.insertLog("支付方式", BusinessConstants.LOG_OPERATION_TYPE_ADD, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updatePaytype(String beanJson, Long id, HttpServletRequest request)throws Exception {
        Paytype paytype = JSONObject.parseObject(beanJson, Paytype.class);
        paytype.setId(id);
        int result=0;
        try{
            result=paytypeMapper.updateByPrimaryKeySelective(paytype);
            logService.insertLog("支付方式",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deletePaytype(Long id, HttpServletRequest request)throws Exception {
        int result=0;
        try{
            result=paytypeMapper.deleteByPrimaryKey(id);
            logService.insertLog("支付方式",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeletePaytype(String ids, HttpServletRequest request) throws Exception{
        List<Long> idList = StringUtil.strToLongList(ids);
        PaytypeExample example = new PaytypeExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            result=paytypeMapper.deleteByExample(example);
            logService.insertLog("支付方式", "批量删除,id集:" + ids, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String payMethod) throws Exception{
        PaytypeExample example = new PaytypeExample();
        example.createCriteria().andIdNotEqualTo(id).andNameEqualTo(payMethod).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Paytype> list =null;
        try{
            list=paytypeMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }

    public String getPaytypeByIds(String paytypeIDs)throws Exception {
        List<Long> ids = StringUtil.strToLongList(paytypeIDs);
        PaytypeExample example = new PaytypeExample();
        example.createCriteria().andIdIn(ids).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Id asc");
        List<Paytype> list =null;
        try{
            list=paytypeMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        StringBuffer sb = new StringBuffer();
        if (null != list) {
            for (Paytype paytype : list) {
                sb.append(paytype.getPayMethod() + " ");
            }
        }
        return  sb.toString();
    }

    public List<Paytype> getPaytypeByType(String type)throws Exception {
        PaytypeExample example = new PaytypeExample();
        example.createCriteria().andTypeEqualTo(type).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        example.setOrderByClause("Id asc");
        List<Paytype> list =null;
        try{
            list=paytypeMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeletePaytypeByIds(String ids)throws Exception {
        logService.insertLog("支付方式",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        User userInfo=userService.getCurrentUser();
        String [] idArray=ids.split(",");
        int result =0;
        try{
            result=paytypeMapperEx.batchDeletePaytypeByIds(new Date(),userInfo==null?null:userInfo.getId(),idArray);
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
    public int batchDeletePaytypeByIdsNormal(String ids) throws Exception {
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
            //accountHeadList=accountHeadMapperEx.getAccountHeadListByHandsPaytypeIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(accountHeadList!=null&&accountHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,HandsPaytypeIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        /**
         * 校验单据主表	jsh_depothead
         * */
        List<DepotHead> depotHeadList =null;
        try{
            //depotHeadList=depotHeadMapperEx.getDepotHeadListByHandsPaytypeIds(idArray);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        if(depotHeadList!=null&&depotHeadList.size()>0){
            logger.error("异常码[{}],异常提示[{}],参数,HandsPaytypeIds[{}]",
                    ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,ExceptionConstants.DELETE_FORCE_CONFIRM_MSG,ids);
            throw new BusinessRunTimeException(ExceptionConstants.DELETE_FORCE_CONFIRM_CODE,
                    ExceptionConstants.DELETE_FORCE_CONFIRM_MSG);
        }
        /**
         * 校验通过执行删除操作
         * */
        deleteTotal= batchDeletePaytypeByIds(ids);
        return deleteTotal;
    }


}
