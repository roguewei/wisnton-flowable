package com.winston.bpmn.spring;

import org.flowable.spring.SpringProcessEngineConfiguration;

/**
 * @ClassName WinstonSpringProcessEngineConfiguration
 * @Author: Winston
 * @Description: 源码扩展（扩展spring风格的引擎配置类）
 * @Date:Create：in 2019/8/27 14:42
 * @Version：
 */
public class WinstonSpringProcessEngineConfiguration extends SpringProcessEngineConfiguration {

    @Override
    public void init() {
        System.out.println("WinstonSpringProcessEngineConfiguration : init--------");
        super.init();
    }
}
