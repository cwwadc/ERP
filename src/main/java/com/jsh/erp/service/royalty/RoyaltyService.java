package com.jsh.erp.service.royalty;

import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.datasource.entities.*;
import com.jsh.erp.datasource.mappers.RoyaltyMapper;
import com.jsh.erp.datasource.mappers.RoyaltyMapperEx;
import com.jsh.erp.exception.JshException;
import com.jsh.erp.service.log.LogService;
import com.jsh.erp.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;


/**
 * @Date: 14:58 2020/2/24
 * @Author: ChenShiGen
 * @Description:
 */
@Service
public class RoyaltyService {
    private Logger logger = LoggerFactory.getLogger(RoyaltyService.class);

    @Resource
    private RoyaltyMapper royaltyMapper;
    @Resource
    private RoyaltyMapperEx royaltyMapperEx;

    @Resource
    private LogService logService;



    public Royalty getRoyalty(long id)throws Exception {
        Royalty result=null;
        try{
            result=royaltyMapper.selectByPrimaryKey(id);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    public List<Royalty> getRoyalty()throws Exception {
        RoyaltyExample example = new RoyaltyExample();
        example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Royalty> list=null;
        try{
            list=royaltyMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list;
    }

    public List<Royalty> select(String monthTime, int offset, int rows) throws Exception{
        List<Royalty> royaltyList = new ArrayList<Royalty>();
        try{
            List<Royalty> list = royaltyMapperEx.selectByConditionRoyalty(monthTime, offset, rows);
            for(Royalty s : list) {
                BigDecimal wholesaleRoyalty = s.getWholesaleRoyalty();
                if(wholesaleRoyalty==null) {
                    wholesaleRoyalty = BigDecimal.ZERO;
                }
                BigDecimal retailRoyalty = s.getRetailRoyalty();
                if(retailRoyalty==null) {
                    retailRoyalty = BigDecimal.ZERO;
                }
                BigDecimal allRoyalty = s.getAllRoyalty();
                if(allRoyalty==null) {
                    allRoyalty = BigDecimal.ZERO;
                }
                royaltyList.add(s);
            }
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return royaltyList;
    }

    public Long countRoyalty(String monthTime) throws Exception{
        Long result=null;
        try{
            result=royaltyMapperEx.countsByRoyalty(monthTime);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertRoyalty(String beanJson, HttpServletRequest request)throws Exception {
        Royalty royalty = JSONObject.parseObject(beanJson, Royalty.class);
        int result=0;
        try{
            result=royaltyMapper.insertSelective(royalty);
            logService.insertLog("提成核算", BusinessConstants.LOG_OPERATION_TYPE_ADD, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int insertRoyalty(Royalty royalty)throws Exception {
        int result=0;
        try{
            result=royaltyMapper.insertSelective(royalty);
            logService.insertLog("提成核算", BusinessConstants.LOG_OPERATION_TYPE_ADD,
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateRoyalty(String beanJson, Long id, HttpServletRequest request)throws Exception {
        Royalty royalty = JSONObject.parseObject(beanJson, Royalty.class);
        if(royalty.getWholesaleRoyalty() == null) {
            royalty.setWholesaleRoyalty(BigDecimal.ZERO);
        }
        if(royalty.getRetailRoyalty() == null) {
            royalty.setRetailRoyalty(BigDecimal.ZERO);
        }
        if(royalty.getAllRoyalty() == null){
            royalty.setAllRoyalty(BigDecimal.ZERO);
        }
        royalty.setId(id);
        int result=0;
        try{
            result=royaltyMapper.updateByPrimaryKeySelective(royalty);
            logService.insertLog("提成核算",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int updateRoyalty(Royalty royalty, HttpServletRequest request)throws Exception {
        int result=0;
        try{
            result=royaltyMapper.updateByPrimaryKeySelective(royalty);
            logService.insertLog("提成核算",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(royalty.getId()).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int deleteRoyalty(Long id, HttpServletRequest request)throws Exception {
        int result=0;
        try{
            result=royaltyMapper.deleteByPrimaryKey(id);
            logService.insertLog("提成核算",
                    new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(id).toString(), request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return royaltyMapper.deleteByPrimaryKey(id);
    }

    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchDeleteRoyalty(String ids, HttpServletRequest request) throws Exception{
        List<Long> idList = StringUtil.strToLongList(ids);
        RoyaltyExample example = new RoyaltyExample();
        example.createCriteria().andIdIn(idList);
        int result=0;
        try{
            result=royaltyMapper.deleteByExample(example);
            logService.insertLog("提成核算", "批量删除,id集:" + ids, request);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public int checkIsNameExist(Long id, String saleName)throws Exception {
        RoyaltyExample example = new RoyaltyExample();
        example.createCriteria().andIdNotEqualTo(id).andSaleNameEqualTo(saleName).andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
        List<Royalty> list=null;
        try{
            list= royaltyMapper.selectByExample(example);
        }catch(Exception e){
            JshException.readFail(logger, e);
        }
        return list==null?0:list.size();
    }


    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public int batchSetStatus(Boolean status, String royaltyIDs)throws Exception {
        logService.insertLog("提成核算",
                new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(royaltyIDs).toString(),
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        List<Long> ids = StringUtil.strToLongList(royaltyIDs);
        Royalty royalty = new Royalty();
        royalty.setStatus(status);
        RoyaltyExample example = new RoyaltyExample();
        example.createCriteria().andIdIn(ids);
        int result=0;
        try{
            result = royaltyMapper.updateByExampleSelective(royalty, example);
        }catch(Exception e){
            JshException.writeFail(logger, e);
        }
        return result;
    }

    public List<Royalty> selectByTimeCount(String monthTime){
        return royaltyMapper.selectByTimeCount(monthTime);
    }
    
}
