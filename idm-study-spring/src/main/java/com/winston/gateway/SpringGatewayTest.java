package com.winston.gateway;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SpringGatewayTest
 * @Author: Winston
 * @Description: 网关，排他网关、并行网关、
 * @Date:Create：in 2019/9/2 15:02
 * @Version：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:flowable-context.xml")
public class SpringGatewayTest {

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
//                .category("排他网关测试1")
//                .name("gateway1")
//                .addClasspathResource("exclusideGateway.bpmn20.xml");
                .category("并行网关测试1")
                .name("parallel1")
                .addClasspathResource("parallelGateway.bpmn20.xml");
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
        String processDefinitionKey = "parallelGateway";
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("day", 2);
        // 启动排他网关任务
//        ProcessInstance processInstance =
//                runtimeService.startProcessInstanceByKey(processDefinitionKey, vars);
        // 启动并行网关任务
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println(processInstance.getId() + "---------" + processInstance.getActivityId());
    }

    /**
     * @auther: Winston
     * @Description: 完成个人任务，若有下一个任务则可以设置任务执行人
     * @param:
     * @return:
     * @date: 2019/8/30 10:45
     */
    @Test
    public void completeTask(){
        // 设置userId变量
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("userId", "老危啊");

        String taskId = "185009";
        taskService.complete(taskId, variables);
        System.out.println("完成任务");
    }

}
