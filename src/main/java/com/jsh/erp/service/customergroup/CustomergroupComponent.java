package com.jsh.erp.service.customergroup;

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
 * @Date: 11:21 2020/2/21
 * @Author: ChenShiGen
 * @Description:
 */
@Service(value = "customergroup_component")
@CustomergroupResource
public class CustomergroupComponent implements ICommonQuery {
    
    @Resource
    private CustomergroupService customergroupService;

    @Override
    public Object selectOne(Long id) throws Exception {
        return customergroupService.getCustomergroup(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        return getCustomergroupList(map);
    }

    private List<?> getCustomergroupList(Map<String, String> map) throws Exception{
        String search = map.get(Constants.SEARCH);
        String province = StringUtil.getInfo(search, "province");
        String order = QueryUtils.order(map);
        return customergroupService.select(province, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map) throws Exception{
        String search = map.get(Constants.SEARCH);
        String province = StringUtil.getInfo(search, "province");
        return customergroupService.countCustomergroup(province);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request)throws Exception {
        return customergroupService.insertCustomergroup(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id, HttpServletRequest request)throws Exception {
        return customergroupService.updateCustomergroup(beanJson, id, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return customergroupService.deleteCustomergroup(id, request);
    }

    @Override
    public int batchDelete(String ids, HttpServletRequest request)throws Exception {
        return customergroupService.batchDeleteCustomergroup(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String province)throws Exception {
        return customergroupService.checkIsNameExist(id, province);
    }

}
