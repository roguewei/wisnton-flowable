package com.winston.task;

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
                .category("singletask4")
                .name("个人任务测试4")
                // singletask2.bpmn20.xml中userTask动态设置userId
                .addClasspathResource("singletask4.bpmn20.xml");
        Deployment deploy = deploymentBuilder.deploy();
        System.out.println(deploy);
    }

    @Test
    public void deployGroup(){
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("grouptask4")
                .name("组任务测试4")
//                .name("组任务监听器测试3")
                // singletask2.bpmn20.xml中userTask动态设置userId
                // singletask3.bpmn20.xml中监听器动态设置userId
//                .addClasspathResource("grouptask2.bpmn20.xml");
                .addClasspathResource("grouptask4.bpmn20.xml");

        Deployment deploy = deploymentBuilder.deploy();
        System.out.println(deploy);
    }

    /**
     * @auther: Winston
     * @Description: 根据流程key启动流程实例
     * singletask.bpmn20.xml中userTask设置固定userId
     * @param:
     * @return:
     * @date: 2019/8/29 9:41
     */
    @Test
    public void startProcessInstanceByKey(){
        String processDefinitionKey = "singletask";
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println(processInstance.getId()+"---------"+processInstance.getActivityId());
    }

    /**
     * @auther: Winston
     * @Description: 根据流程key启动流程实例
     * 分配任务方式一（设置固定任务处理人）
     * singletask2.bpmn20.xml中userTask动态设置userId，所以启动时需要设置，否则爆粗
     * @param:
     * @return:
     * @date: 2019/8/29 9:41
     */
    @Test
    public void startProcessInstanceByKey2(){
        // 设置userId变量
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("userId", "老高啊");

        String processDefinitionKey = "singletask3";
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        System.out.println(processInstance.getId()+"---------"+processInstance.getActivityId());
    }

    /**
     * @auther: Winston
     * @Description: 根据流程key启动流程实例
     * 分配任务方式二（监听器设置任务处理人）
     * singletask4.bpmn20.xml中userTask设置任务监听器，
     * 通过监听器设置任务处理人
     * @param:
     * @return:
     * @date: 2019/8/29 9:41
     */
    @Test
    public void startProcessInstanceByKey4(){
        String processDefinitionKey = "grouptask4";
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
     * @Description: 启动组流程实例
     * @param:
     * @return:
     * @date: 2019/8/29 9:41
     */
    @Test
    public void startProcessInstanceByGroup(){
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("userIds", "张三1,李四1,王五1");
        String processDefinitionKey = "grouptask2";
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(processDefinitionKey, vars);
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
        String userId = "老高";
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

        String taskId = "115006";
        taskService.complete(taskId, variables);
        System.out.println("完成任务");
    }

    /**
     * @auther: Winston
     * @Description: 分配任务方式三——任务认领
     * @param:
     * @return:
     * @date: 2019/8/30 11:51
     */
    @Test
    public void setAssigneeTask(){
        // 任务id
        String taskId = "65006";
        // 指定认领的办理人
        String userId = "wgs";
        taskService.setAssignee(taskId, userId);
    }

    /**
     * @auther: Winston
     * @Description: 查询运行任务处理人，ACT_RU_IDENTITYLINK
     * @param:
     * @return:
     * @date: 2019/8/30 14:27
     */
    @Test
    public void findGroupUser(){
        String taskId = "87507";
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskId);
        for(IdentityLink identityLink : identityLinksForTask){
            System.out.println("getProcessDefinitionId= "+ identityLink.getProcessDefinitionId());
            System.out.println("getGroupId= "+ identityLink.getGroupId());
            System.out.println("getUserId="+ identityLink.getUserId());
            System.out.println("getTaskId="+ identityLink.getTaskId());
            System.out.println("-----------------");
        }
    }

    /**
     * @auther: Winston
     * @Description: 查询组任务成员历史列表
     * @param:
     * @return:
     * @date: 2019/8/30 14:27
     */
    @Test
    public void findGroupHisUser(){
        String taskId = "87507";
        List<HistoricIdentityLink> historicIdentityLinksForTask = historyService.getHistoricIdentityLinksForTask(taskId);
        for(HistoricIdentityLink historicIdentityLink : historicIdentityLinksForTask){
            System.out.println("getUserId= "+ historicIdentityLink.getUserId());
            System.out.println("getTaskId= "+ historicIdentityLink.getTaskId());
            System.out.println("getProcessInstanceId="+ historicIdentityLink.getProcessInstanceId());
            System.out.println("---------------");
        }
    }

    /**
     * @auther: Winston
     * @Description: 查询个人任务
     * @param:
     * @return:
     * @date: 2019/8/30 14:27
     */
    @Test
    public void findMyTask(){
        List<Task> list = taskService
                .createTaskQuery()
                .taskAssignee("wangwu")
                .list();
        for(Task task : list){
            System.out.println("id= "+ task.getId());
            System.out.println("name= "+ task.getName());
            System.out.println("assinee="+ task.getAssignee());
        }
    }

    /**
     * @auther: Winston
     * @Description: 查询组任务
     * @param:
     * @return:
     * @date: 2019/8/30 14:27
     */
    @Test
    public void findGroupTask(){
        String userId = "wangwu";
        List<Task> list = taskService
                .createTaskQuery()
                .taskCandidateUser(userId)
                .list();
        for(Task task : list){
            System.out.println("id= "+ task.getId());
            System.out.println("name= "+ task.getName());
            System.out.println("assinee="+ task.getAssignee());
        }
    }

    /**
     * @auther: Winston
     * @Description: 拾取、认领任务
     * @param:
     * @return:
     * @date: 2019/8/30 14:53
     */
    @Test
    public void claim(){
        // 任务Id
        String taskId = "102506";
        // 分配办理人
        String userId = "大哥大";
        taskService.claim(taskId, userId);
    }

    /**
     * @auther: Winston
     * @Description: 取消拾取、认领任务
     * @param:
     * @return:
     * @date: 2019/8/30 14:53
     */
    @Test
    public void unClaim(){
        // 任务Id
        String taskId = "102506";
        taskService.claim(taskId, null);
    }

}
