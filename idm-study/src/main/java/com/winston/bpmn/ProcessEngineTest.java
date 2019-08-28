package com.winston.bpmn;

import org.flowable.engine.*;
import org.junit.After;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName ProcessEngineTest
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/8/24 16:25
 * @Version：
 */
public class ProcessEngineTest {

    ProcessEngine processEngine;

    /**
     * @auther: Winston
     * @Description: 根据xml创建引擎
     * @param:
     * @return:
     * @date: 2019/8/26 17:13
     */
    @Test
    public void testProcessEngine(){
        processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println("流程引擎类： " + processEngine);

        RepositoryService repositoryService = processEngine.getRepositoryService();
        System.out.println(repositoryService);

        String name = processEngine.getName();
        System.out.println("流程引擎名称： " + name);
        DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();
        System.out.println(dynamicBpmnService);
        FormService formService = processEngine.getFormService();
        System.out.println(formService);
        HistoryService historyService = processEngine.getHistoryService();
        System.out.println(historyService);
        IdentityService identityService = processEngine.getIdentityService();
        System.out.println(identityService);
        ManagementService managementService = processEngine.getManagementService();
        System.out.println(managementService);

        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        System.out.println(processEngineConfiguration);
        RuntimeService runtimeService = processEngine.getRuntimeService();
        System.out.println(runtimeService);
        TaskService taskService = processEngine.getTaskService();
        System.out.println(taskService);


        Map<Object, Object> beans = processEngineConfiguration.getBeans();
        Object testA = beans.get("testA");
        System.out.println(testA);

        // 不支持下列使用方式
        Set<Map.Entry<Object, Object>> entries = beans.entrySet();
        for(Map.Entry<Object, Object> entry : entries){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            System.out.println("---------------");
        }
    }

    /**
     * @Description: 代码手动构造流程引擎配置类和引擎类
     * @param:
     * @return:
     * @auther: Winston
     * @date: 2019/8/26 17:10
     */
    @Test
    public void shougongCreate(){
        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        engineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
        engineConfiguration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/flowable-idm?characterEncoding=UTF-8");
        engineConfiguration.setJdbcUsername("root");
        engineConfiguration.setJdbcPassword("123456");

        processEngine = engineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
    }

    /**
     * @Description: 代码手动构造流程引擎配置类和引擎类
     * @param:
     * @return:
     * @auther: Winston
     * @date: 2019/8/26 17:10
     */
    @Test
    public void shougongCreate2(){
        InputStream resourceAsStream = ProcessEngineTest.class.getClassLoader().getResourceAsStream("flowable.cfg.xml");
        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream(resourceAsStream);
        engineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
        engineConfiguration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/flowable-idm?characterEncoding=UTF-8");
        engineConfiguration.setJdbcUsername("root");
        engineConfiguration.setJdbcPassword("123456");

        processEngine = engineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
    }

    /**
     * @Description: 代码手动构造流程引擎配置类和引擎类
     * @param:
     * @return:
     * @auther: Winston
     * @date: 2019/8/26 17:10
     */
    @Test
    public void shougongCreate3(){
        String resource = "flowable.cfg1.xml";
        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(resource);
        processEngine = engineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
    }

    @After
    public void close(){
        processEngine.close();
    }

}
