package com.jsh.erp.scheduler;

import com.jsh.erp.service.depotHead.DepotHeadService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Date: 19:05 2020/2/23
 * @Author: ChenShiGen
 * @Description:
 */
@Component
public class SchedulerTask {

    @Resource
    private DepotHeadService depotHeadService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat2 = new SimpleDateFormat("YYYY-MM");

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    //每月一号执行： 核算业务员提成入库jsh_royalty
    //@Scheduled(cron = "0 0 0 1 * ?")
    //@Scheduled(cron = "* */2 * * * ?")
    /*public void testTasks() {
        System.out.println("定时任务执行开始时间：" + dateFormat.format(new Date()));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        mapList = depotHeadService.selectByTime(dateFormat2.format(calendar.getTime()));
        for(int i=0; i<mapList.size(); i++){
            System.out.println(mapList.get(i));
        }

        System.out.println("定时任务执行结束时间：" + dateFormat.format(new Date()));
    }*/



}
