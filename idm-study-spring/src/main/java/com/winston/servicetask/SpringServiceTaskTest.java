package com.winston.servicetask;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SpringProcessTest
 * @Author: Winston
 * @Description: 流程创建-部署-启动-任务
 * @Date:Create：in 2019/8/29 9:34
 * @Version：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:flowable-context.xml")
public class SpringServiceTaskTest {

    ProcessEngine processEngine;
    RepositoryService repositoryService;
    RuntimeService runtimeService;
    TaskService taskService;
    HistoryService historyService;
    IdentityService identityService;

    @Before
    public void buildRepository(){
        // 获取流程引擎对象
        processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
        repositoryService = processEngine.getRepositoryService();
        System.out.println(repositoryService);
        String name = processEngine.getName();
        System.out.println("流程引擎的名称：------ ：" + name);

        runtimeService = processEngine.getRuntimeService();
        // 获取任务服务
        taskService = processEngine.getTaskService();
        // 获取历史流程服务
        historyService = processEngine.getHistoryService();
        // 获取用户服务
        identityService = processEngine.getIdentityService();
    }

    @Test
    public void deploy(){
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("服务任务3")
                .name("servicetask3")
                .addClasspathResource("servicetask3.bpmn20.xml");
        Deployment deploy = deploymentBuilder.deploy();
        System.out.println(deploy);
    }

    /**
     * @auther: Winston
     * @Description: 根据流程key启动流程实例
     * @param:
     * @return:
     * @date: 2019/8/29 9:41
     */
    @Test
    public void startProcessInstanceByKey(){
        String processDefinitionKey = "serviceTask3";
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println(processInstance.getId()+"---------"+processInstance.getActivityId());
    }

}
