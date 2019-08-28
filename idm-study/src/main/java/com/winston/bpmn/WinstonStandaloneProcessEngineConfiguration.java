package com.winston.bpmn;

import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;

/**
 * @ClassName WinstonStandaloneProcessEngineConfiguration
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/8/27 9:19
 * @Version：
 */
public class WinstonStandaloneProcessEngineConfiguration extends StandaloneProcessEngineConfiguration {

    @Override
    public void init() {
        System.out.println("WinstonStandaloneProcessEngineConfiguration init");
        super.init();
    }
}
