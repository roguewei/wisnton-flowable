package com.winston.process;

import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.DataObject;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName SpringProcessTest
 * @Author: Winston
 * @Description: 流程创建-部署-启动
 * @Date:Create：in 2019/8/29 9:34
 * @Version：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:flowable-context.xml")
public class SpringProcessTest {

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

    /**
     * @auther: Winston
     * @Description: 创建并部署请假流程
     * @param:
     * @return:
     * @date: 2019/8/29 9:41
     */
//    @Test
//    public void deploy(){
//        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
//                .category("leave")
//                .name("请假流程")
//                .addClasspathResource("leave.bpmn");
//        Deployment deploy = deploymentBuilder.deploy();
//        System.out.println(deploy);
//    }
    @Test
    public void deploy(){
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("dataobject")
                .name("请假流程")
                .addClasspathResource("dataobject.bpmn20.xml")
                .tenantId("winston");
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
        String processDefinitionKey = "dataobject";
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
     * @Description: 查看任务（查询的是act_ru_task表）
     * @param:
     * @return:
     * @date: 2019/8/29 9:57
     */
    @Test
    public void queryMyTask(){
        // 指定任务办理者
        String assignee = "张三";
        // 查询任务列表
        List<Task> list = taskService
                //创建任务查询对象
                .createTaskQuery()
                // 指定个人任务办理人
                .taskAssignee(assignee)
                .list();
        // 遍历集合查看内容
        for(Task task : list){
            System.out.println("taskid:"+task.getId()+", taskName : "+ task.getName());
            System.out.println("--------------");
        }
    }

    /**
     * @auther: Winston
     * @Description: 办理任务
     * @param:
     * @return:
     * @date: 2019/8/29 10:22
     */
    @Test
    public void completeTask(){
        String taskId = "117506";
        taskService.complete(taskId);
        System.out.println("完成任务");
    }

    /**
     * @auther: Winston
     * @Description:  查询流程状态，查act_ru_execution表
     * 存在数据则表示正在运行
     * @param:
     * @return:
     * @date: 2019/8/29 10:29
     */
    @Test
    public void queryProcessState(){
        String processInstanceId = "100001";
        ProcessInstance result = runtimeService
                // 创建流程实例查询，查询正在执行的流程实例
                .createProcessInstanceQuery()
                // 按照流程实例id查询
                .processInstanceId(processInstanceId)
                // 返回唯一的结果
                .singleResult();
        if(result != null){
            System.out.println("当前流程在： "+result.getActivityId());
        }else{
            System.out.println("流程一结束");
        }
    }

    /**
     * @auther: Winston
     * @Description: 查询执行实例
     * @param:
     * @return:
     * @date: 2019/8/29 10:39
     */
    @Test
    public void createExecutionQuery(){
        List<Execution> list = runtimeService.createExecutionQuery()
                .list();
        for(Execution execution : list){
            System.out.println(execution.getId()+"--------"+execution.getActivityId());
        }
    }

    /**
     * @auther: Winston
     * @Description: 查询历史流程实例
     * 历史流程实例act_hi_procinst表，历史 流程实例与运行流程实例是一对一关系
     * @param:
     * @return:
     * @date: 2019/8/29 10:39
     */
    @Test
    public void createHistoricProcessInstanceQuery(){

        HistoricProcessInstance historicProcessInstance = historyService
                // 创建历史流程实例查询
                .createHistoricProcessInstanceQuery()
                // 使用流程实例id查询
                .processInstanceId("90001")
                .singleResult();
        System.out.println("流程定义id： "+ historicProcessInstance.getProcessDefinitionId());
        System.out.println("流程实例id： "+ historicProcessInstance.getId());
        System.out.println(historicProcessInstance.getStartTime()+"--------"+ historicProcessInstance.getEndTime()
                +"------"+historicProcessInstance.getDurationInMillis());
    }

    /**
     * @auther: Winston
     * @Description: 查询历史任务
     * 历史任务表act_hi_taskinst
     * @param:
     * @return:
     * @date: 2019/8/29 11:11
     */
    @Test
    public void createHistoricTaskInstanceQuery(){
        List<HistoricTaskInstance> list =
                historyService.createHistoricTaskInstanceQuery().list();
        System.out.println(list);
    }

    /**
     * @auther: Winston
     * @Description: 设置流程实例启动人（方式一）
     * @param:
     * @return:
     * @date: 2019/8/29 14:15
     */
    @Test
    public void setAuthenticatedUserId(){
        String authenticatedUserId = "老高";
        identityService.setAuthenticatedUserId(authenticatedUserId);

        String processDefinitionKey = "leave";
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println(processInstance.getId()+"---------"+processInstance.getActivityId());
    }

    /**
     * @auther: Winston
     * @Description: 设置流程实例启动人（方式二）
     * @param:
     * @return:
     * @date: 2019/8/29 14:15
     */
    @Test
    public void setAuthenticatedUserId2(){
        String authenticatedUserId = "老高222";
        Authentication.setAuthenticatedUserId(authenticatedUserId);

        String processDefinitionKey = "leave";
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey(processDefinitionKey);
        System.out.println(processInstance.getId()+"---------"+processInstance.getActivityId());
    }

    /**
     * @auther: Winston
     * @Description: 获取变量表ACT_RU_VARIABLE数据
     * @param:
     * @return:
     * @date: 2019/8/29 15:57
     */
    @Test
    public void getDataObject(){
        String executionId = "115001";
        String dataObject = "天数";
        DataObject dataObject1 = runtimeService.getDataObject(executionId, dataObject);
        System.out.println(dataObject1.getDataObjectDefinitionKey());
        System.out.println(dataObject1.getDescription());
        System.out.println(dataObject1.getExecutionId());
        System.out.println(dataObject1.getName());
        System.out.println(dataObject1.getValue());
        System.out.println(dataObject1.getType());
    }

    /**
     * @auther: Winston
     * @Description: 获取变量表ACT_RU_VARIABLE数据
     * @param:
     * @return:
     * @date: 2019/8/29 15:57
     */
    @Test
    public void setAuthenticatedUserId3(){
        String executionId = "115001";
        Map<String, DataObject> dataObjects = runtimeService.getDataObjects(executionId);
        Set<Map.Entry<String, DataObject>> entries = dataObjects.entrySet();
        for(Map.Entry<String, DataObject> map : entries){
            DataObject value = map.getValue();
            if(value != null){
                System.out.println(value.getDataObjectDefinitionKey());
                System.out.println(value.getDescription());
                System.out.println(value.getExecutionId());
                System.out.println(value.getName());
                System.out.println(value.getValue());
                System.out.println(value.getType());
            }
        }
    }

    /**
     * @auther: Winston
     * @Description: 实例删除
     * @param:
     * @return:
     * @date: 2019/8/29 16:13
     */
    @Test
    public void deleteProcessInstance(){
        // 删除原因
        String deleteReason = "winston测试删除";
        // 流程实例id
        String processInstanceId = "110001";
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }

    /**
     * @auther: Winston
     * @Description: 实例删除
     * @param:
     * @return:
     * @date: 2019/8/29 16:13
     */
    @Test
    public void getActiveActivityIds(){
        // 流程实例id
        String executionId = "115001";
        List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionId);
        for(String id : activeActivityIds){
            System.out.println(id);
        }
    }

    /**
     * @auther: Winston
     * @Description: 查询流程是否挂起
     * @param:
     * @return:
     * @date: 2019/8/29 17:43
     */
    @Test
    public void isProcessDefinitionSuspended(){
        // 流程定义id
        String processDefinitionId = "dataobject:1:4";
        boolean processDefinitionSuspended = repositoryService.isProcessDefinitionSuspended(processDefinitionId);
        System.out.println(processDefinitionSuspended);
    }

    /**
     * @auther: Winston
     * @Description: 挂起流程
     * 流程定义表act_re_procdef中SUSPENSION_STATE_ 1表示未挂起，2表示挂起
     * 该流程实例下的所有执行实例都会被挂起
     * @param:
     * @return:
     * @date: 2019/8/29 17:46
     */
    @Test
    public void suspendProcessDefinitionById(){
        // 流程定义id
        String processDefinitionId = "dataobject:1:4";
        repositoryService.suspendProcessDefinitionById(processDefinitionId);
    }

    /**
     * @auther: Winston
     * @Description: 验证挂起流程
     * 挂起会报错Cannot start process instance. Process definition dataobject (id = dataobject:1:4) is suspended
     * @param:
     * @return:
     * @date: 2019/8/29 17:46
     */
    @Test
    public void startProcessInstanceByIdSuspend(){
        // 流程定义id
        String processDefinitionId = "dataobject:1:4";
        runtimeService.startProcessInstanceById(processDefinitionId);
    }

    /**
     * @auther: Winston
     * @Description: 解除挂起流程
     * 流程定义表act_re_procdef中SUSPENSION_STATE_ 1表示未挂起，2表示挂起
     * @param:
     * @return:
     * @date: 2019/8/29 17:46
     */
    @Test
    public void activateProcessDefinitionById(){
        // 流程定义id
        String processDefinitionId = "dataobject:1:4";
        repositoryService.activateProcessDefinitionById(processDefinitionId);
    }

    /**
     * @auther: Winston
     * @Description: 挂起流程实例act_ru_execution，同时也挂起流程act_re_procdef
     * @param:
     * @return:
     * @date: 2019/8/29 17:55
     */
    @Test
    public void suspendProcessDefinitionById2(){
        // 流程定义id
        String processDefinitionId = "dataobject:1:4";
        repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
    }

    /**
     * @auther: Winston
     * @Description: 激活流程实例
     * @param:
     * @return:
     * @date: 2019/8/29 17:55
     */
    @Test
    public void activateProcessDefinitionById2(){
        // 流程定义id
        String processDefinitionId = "dataobject:1:4";
        repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
    }

}
