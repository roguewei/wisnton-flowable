package com.winston.task;

import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @ClassName SpringProcessTest
 * @Author: Winston
 * @Description: 流程创建-部署-启动-任务
 * @Date:Create：in 2019/8/29 9:34
 * @Version：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:flowable-context.xml")
public class SpringTaskTest {

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
                .category("singletask")
                .name("个人任务测试")
                .addClasspathResource("singletask.bpmn20.xml");
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
//        String processDefinitionKey = "leave";
        String processDefinitionKey = "singletask";
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println(processInstance.getId()+"---------"+processInstance.getActivityId());
    }

    /**
     * @auther: Winston
     * @Description: 根据流程key以及租户id启动流程实例
     * @param:
     * @return:
     * @date: 2019/8/29 9:41
     */
    @Test
    public void startProcessInstanceByKeyAndTentantId(){
        String processDefinitionKey = "dataobject";
        // tentantId需要在部署deploy时定义
        String tentantId = "winston";
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKeyAndTenantId(processDefinitionKey, tentantId);
        System.out.println(processInstance.getId()+"---------"+processInstance.getActivityId());
    }

    /**
     * @auther: Winston
     * @Description: 根据id启动流程实例
     * @param:
     * @return:
     * @date: 2019/8/29 9:41
     */
    @Test
    public void startProcessInstanceById(){
//        String processDefinitionKey = "leave";
        String processDefinitionId = "leave:1:87504";
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceById(processDefinitionId);
        System.out.println(processInstance.getId()+"---------"+processInstance.getActivityId());
    }

    /**
     * @auther: Winston
     * @Description: 查询执行实例，ACT_RU_EXECUTION
     * 接收任务不会在act_ru_task表中插入数据
     * @param:
     * @return:
     * @date: 2019/8/30 9:47
     */
    @Test
    public void activityId(){
        String activityId = "sid-4DB1CA82-3C10-4C4E-811F-66B518F0EE42";
        Execution execution = runtimeService
                .createExecutionQuery()
                .activityId(activityId)
                .singleResult();
        System.out.println(execution.getId());
    }

    /**
     * @auther: Winston
     * @Description: 触发执行实例继续往下运转
     * 执行该方法之后上面的activityId（）方法将查询不到之前的数据，需要传递新的activityId才能查到
     * 因为执行实例已经改变成下一个节点
     * @param:
     * @return:
     * @date: 2019/8/30 9:47
     */
    @Test
    public void trigger(){
        String executionId = "10002";
        runtimeService.trigger(executionId);
    }

    /**
     * @auther: Winston
     * @Description: 查询个人任务，ACT_RU_TASK
     * @param:
     * @return:
     * @date: 2019/8/30 10:38
     */
    @Test
    public void findMyTaskList(){
        String userId = "张三丰";
        List<Task> list = taskService
                .createTaskQuery()
                .taskAssignee(userId)
                .list();
        for(Task task : list){
            System.out.println("id= " + task.getId());
            System.out.println("name= "+task.getName());
            System.out.println("assinee= "+ task.getAssignee());
            System.out.println("createTime= "+task.getCreateTime());
            System.out.println("executionId="+ task.getExecutionId());
        }
    }

    /**
     * @auther: Winston
     * @Description: 完成个人任务
     * @param:
     * @return:
     * @date: 2019/8/30 10:45
     */
    @Test
    public void completeTask(){
        String taskId = "20006";
        taskService.complete(taskId);
        System.out.println("完成任务");
    }

}
