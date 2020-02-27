package com.jsh.erp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsh.erp.datasource.entities.Account;
import com.jsh.erp.datasource.entities.DepotHead;
import com.jsh.erp.datasource.entities.Royalty;
import com.jsh.erp.datasource.vo.AccountVo4List;
import com.jsh.erp.datasource.vo.SaleDataVo;
import com.jsh.erp.service.account.AccountService;
import com.jsh.erp.service.depotHead.DepotHeadService;
import com.jsh.erp.service.royalty.RoyaltyService;
import com.jsh.erp.utils.BaseResponseInfo;
import com.jsh.erp.utils.ErpInfo;
import com.jsh.erp.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsh.erp.utils.ResponseJsonUtil.returnJson;

/**
 * @Date: 14:55 2020/2/24
 * @Author: ChenShiGen
 * @Description:
 */
@RestController
@RequestMapping(value = "/royalty")
public class RoyaltyController {
    private Logger logger = LoggerFactory.getLogger(RoyaltyController.class);

    @Resource
    private RoyaltyService royaltyService;
    @Resource
    private AccountService accountService;
    @Resource
    private DepotHeadService depotHeadService;

    /**
     * 批量设置状态-启用或者禁用
     * @param status
     * @param royaltyIDs
     * @param request
     * @return
     */
    @PostMapping(value = "/batchSetStatus")
    public String batchSetStatus(@RequestParam("status") Boolean status,
                                 @RequestParam("royaltyIDs") String royaltyIDs,
                                 HttpServletRequest request)throws Exception {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        int res = royaltyService.batchSetStatus(status, royaltyIDs);
        if(res > 0) {
            return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
        } else {
            return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
        }
    }

    @GetMapping(value = "/getSaleData")
    public BaseResponseInfo getSaleData(HttpServletRequest request, @RequestParam("saleId") Long saleId, @RequestParam("monthTime") String monthTime){
        BaseResponseInfo res = new BaseResponseInfo();

        List<SaleDataVo> saleDataVoList = new ArrayList<SaleDataVo>();
        saleDataVoList = depotHeadService.selectBySaleAndTime(saleId, monthTime);
        JSONArray dataArray = new JSONArray();
        JSONObject item = null;
        for(int i=0; i<saleDataVoList.size(); i++) {
            item = new JSONObject();
            SaleDataVo saleDataVo = saleDataVoList.get(i);
            item.put("depotheadNum", saleDataVo.getDepotheadNum());
            item.put("goodsInfo", saleDataVo.getGoodsInfo());
            item.put("depotheadTime", saleDataVo.getDepotheadTime());
            item.put("depotheadAmount", saleDataVo.getDepotheadAmount());
            item.put("royaltyAmount", saleDataVo.getRoyaltyAmount()/saleDataVo.getSalesman().split(",").length);
            dataArray.add(item);
        }

        JSONObject outer = new JSONObject();
        outer.put("total", 2);
        outer.put("rows", dataArray);
        res.code = 200;
        res.data = outer;
        return res;
    }


    //发放提成
    @PostMapping(value = "/extendRoyalty")
    public BaseResponseInfo insert(@RequestParam("info") String beanJson, HttpServletRequest request)throws Exception {
        BaseResponseInfo res = new BaseResponseInfo();

        //字符串转json
        JSONObject jsonObject = JSONObject.parseObject(beanJson);
        Long royaltyId = jsonObject.getLong("royaltyId"); //提成核算Id
        Long saleId = jsonObject.getLong("saleId");     //销售Id
        BigDecimal allRoyalty = jsonObject.getBigDecimal("royaltyAmount");  //提成总金额
        Long AccountId = jsonObject.getLong("AccountId");   //发放账户

        //账户
        Account al = accountService.getAccount(AccountId);
        //提成核算
        Royalty royalty = royaltyService.getRoyalty(royaltyId);

        //修改提成核算：状态、发放账户
        royalty.setStatus(true);
        royalty.setAccountId(AccountId);
        royalty.setAccountName(al.getName());
        royaltyService.updateRoyalty(royalty, request);

        //发放账户： 简单处理，单据主表入一条提成数据
        DepotHead depotHead = new DepotHead();
        depotHead.setType("其它");
        depotHead.setSubtype("提成");
        depotHead.setAccountid(AccountId);
        depotHead.setChangeamount(allRoyalty.multiply(new BigDecimal(-1)));
        depotHead.setTotalprice(allRoyalty.multiply(new BigDecimal(-1)));
        depotHead.setPaytype("现付");
        //depotHead.setTenantId(Long.parseLong(request.getSession().getAttribute("tenantId").toString()));
        depotHeadService.insertDepotHead2(depotHead, request);


        res.code = 200;
        return res;
    }



}
