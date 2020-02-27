package com.jsh.erp.service.royalty;

import com.jsh.erp.datasource.entities.Account;
import com.jsh.erp.datasource.entities.Person;
import com.jsh.erp.datasource.entities.Royalty;
import com.jsh.erp.service.ICommonQuery;
import com.jsh.erp.service.account.AccountService;
import com.jsh.erp.service.depotHead.DepotHeadService;
import com.jsh.erp.service.person.PersonService;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.QueryUtils;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Date: 14:57 2020/2/24
 * @Author: ChenShiGen
 * @Description:
 */
@Service(value = "royalty_component")
@RoyaltyResource
public class RoyaltyComponent implements ICommonQuery {

    @Resource
    private RoyaltyService royaltyService;
    @Resource
    private DepotHeadService depotHeadService;
    @Resource
    private PersonService personService;

    @Override
    public Object selectOne(Long id) throws Exception {
        return royaltyService.getRoyalty(id);
    }

    @Override
    public List<?> select(Map<String, String> map)throws Exception {
        //检测上月提成是否入库
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        String lastMonth = new SimpleDateFormat("YYYY-MM").format(calendar.getTime());
        List<Royalty> royalties = royaltyService.selectByTimeCount(lastMonth);
        //无数据则入数据
        if(royalties==null || royalties.size()==0){
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            mapList = depotHeadService.selectByTime(lastMonth);
            Map<Long, Royalty> mp = new HashMap<Long, Royalty>();//所有数据放入Map集合中
            for(int i=0; i<mapList.size(); i++) {
                Map<String, Object> m = mapList.get(i);
                String[] strArr = m.get("salesman").toString().split(",");//业务员数组
                for(int j=0; j<strArr.length; j++){
                    Long saleId = Long.parseLong(strArr[j].substring(1, strArr[j].length()-1));//业务员
                    double allSaleRoyalty = Double.valueOf(m.get("saleRoyalty").toString());//数组内总提成
                    double saleRoyalty = allSaleRoyalty/strArr.length;//当前提成
                    if(mp.containsKey(saleId)){     //包含则更改
                        Royalty royalty = mp.get(saleId);
                        if("零售".equals(m.get("subType").toString())){
                            royalty.setRetailRoyalty(royalty.getRetailRoyalty().add(new BigDecimal(saleRoyalty)));
                        }else{
                            royalty.setWholesaleRoyalty(royalty.getWholesaleRoyalty().add(new BigDecimal(saleRoyalty)));
                        }
                        royalty.setAllRoyalty(royalty.getAllRoyalty().add(new BigDecimal(saleRoyalty)));
                    }else{      //不包含则新增
                        Royalty royalty = new Royalty();
                        royalty.setSaleId(saleId);
                        Person person = personService.getPerson(saleId);
                        royalty.setSaleName( person.getName());
                        royalty.setWholesaleRoyalty(new BigDecimal(saleRoyalty));
                        royalty.setRetailRoyalty(BigDecimal.ZERO);
                        royalty.setAllRoyalty(new BigDecimal(saleRoyalty));
                        royalty.setMonthTime(lastMonth);
                        mp.put(saleId, royalty);
                    }
                }
            }
            //入库提成核算
            for(Map.Entry<Long, Royalty> a : mp.entrySet()){
                royaltyService.insertRoyalty(a.getValue());
            }
        }

        return getRoyaltyList(map);
    }
    private List<?> getRoyaltyList(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String monthTime = StringUtil.getInfo(search, "monthTime");
        String order = QueryUtils.order(map);
        return royaltyService.select(monthTime, QueryUtils.offset(map), QueryUtils.rows(map));
    }

    @Override
    public Long counts(Map<String, String> map)throws Exception {
        String search = map.get(Constants.SEARCH);
        String monthTime = StringUtil.getInfo(search, "monthTime");
        return royaltyService.countRoyalty(monthTime);
    }

    @Override
    public int insert(String beanJson, HttpServletRequest request)throws Exception {
        return royaltyService.insertRoyalty(beanJson, request);
    }

    @Override
    public int update(String beanJson, Long id, HttpServletRequest request)throws Exception {
        return royaltyService.updateRoyalty(beanJson, id, request);
    }

    @Override
    public int delete(Long id, HttpServletRequest request)throws Exception {
        return royaltyService.deleteRoyalty(id, request);
    }

    @Override
    public int batchDelete(String ids, HttpServletRequest request)throws Exception {
        return royaltyService.batchDeleteRoyalty(ids, request);
    }

    @Override
    public int checkIsNameExist(Long id, String name)throws Exception {
        return royaltyService.checkIsNameExist(id, name);
    }
}
