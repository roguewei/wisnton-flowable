package com.winston.bpmn;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineLifecycleListener;

/**
 * @ClassName WinstonProcessEngineLifecycleListener
 * @Author: Winston
 * @Description: 需要在flowable.cfg.xml中配置该类
 * @Date:Create：in 2019/8/26 16:33
 * @Version：
 */
public class WinstonProcessEngineLifecycleListener implements ProcessEngineLifecycleListener {

    /**
     * @Description: 
     * @param: 监听引擎启动
     * @return: 
     * @auther: Winston
     * @date: 2019/8/26 16:34
     */
    public void onProcessEngineBuilt(ProcessEngine processEngine) {
        System.out.println("WinstonProcessEngineLifecycleListener:onProcessEngineBuilt: "+ processEngine);
    }

    /**
     * @Description: 
     * @param: 监听引擎关闭
     * @return:
     * @auther: Winston
     * @date: 2019/8/26 17:06
     */
    public void onProcessEngineClosed(ProcessEngine processEngine) {
        System.out.println("WinstonProcessEngineLifecycleListener:onProcessEngineClosed: "+ processEngine);
    }
}
