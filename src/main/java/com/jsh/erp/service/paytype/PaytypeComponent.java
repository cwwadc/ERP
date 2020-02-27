package com.jsh.erp.service.paytype;

import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Date: 17:20 2020/2/15
 * @Author: ChenShiGen
 * @Description:
 */
@Service(value = "paytype_component")
@PaytypeResource
public class PaytypeComponent implements ICommonQuery {

    @Resource
    private PaytypeService paytypeService;

    @Override
    public Object selectOne(Long id) throws Exception {
        return paytypeService.getPaytype(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getPaytypeList(map);
    }

    private List<?> getPaytypeList(Map<String, String> map) throws Exception{
        String search = map.get(Constants.SEARCH);
        String payMethod = StringUtil.getInfo(search, "payMethod");
        String order = QueryUtils.order(map);
        return paytypeService.select(payMethod, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map) throws Exception{
        String search = map.get(Constants.SEARCH);
        String payMethod = StringUtil.getInfo(search, "payMethod");
        return paytypeService.countPaytype(payMethod);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request)throws Exception {
        return paytypeService.insertPaytype(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id, HttpServletRequest request)throws Exception {
        return paytypeService.updatePaytype(beanJson, id, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return paytypeService.deletePaytype(id, request);
    }

    @Override
    public int batchDelete(String ids, HttpServletRequest request)throws Exception {
        return paytypeService.batchDeletePaytype(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String payMethod)throws Exception {
        return paytypeService.checkIsNameExist(id, payMethod);
    }


}
