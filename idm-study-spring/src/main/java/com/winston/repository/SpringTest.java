package com.winston.repository;

import org.apache.commons.io.FileUtils;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @ClassName SpringTest
 * @Author: Winston
 * @Description: TODO
 * @Date:Create：in 2019/8/27 17:04
 * @Version：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:flowable-context.xml")
public class SpringTest {

    ProcessEngine processEngine;
    RepositoryService repositoryService;

    @Before
    public void buildRepository(){
        processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
        repositoryService = processEngine.getRepositoryService();
        System.out.println(repositoryService);
        String name = processEngine.getName();
        System.out.println("流程引擎的名称：------ ：" + name);
    }

    @Test
    public void deploymentBuild(){
        DeploymentBuilder deploymentBuilder = repositoryService
                .createDeployment()
                .category("测试分类")
                .name("名称");
        System.out.println(deploymentBuilder);
    }

    /**
     * @auther: Winston
     * @Description: 流程文件的名称必须是"bpmn或bpmn20.xml结尾的文件才可以部署到流程定义表act_re_procdef
     * 比如下面的winston_test.bpmn20.xml改成winston_test.xml就会部署失败
     * @param:
     * @return:
     * @date: 2019/8/28 14:38
     */
    @Test
    public void deploymentBuild2(){
        // 获取流程
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("测试分类2")
                .name("名称2")
                .addClasspathResource("winston_test.bpmn20.xml");

        // 部署流程
        Deployment deploy = deploymentBuilder.deploy();

        System.out.println("------"+deploy.getId());
    }

    /**
     * @auther: Winston
     * @Description: 文本方式部署
     * 流程文件的名称必须是"bpmn或bpmn20.xml结尾的文件才可以部署到流程定义表act_re_procdef
     * @param:
     * @return:
     * @date: 2019/8/28 14:34
     */
    @Test
    public void deploymentBuild3(){
        // 获取流程
        String text = IoUtil.readFileAsString("winston_test.bpmn20.xml");
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("测试分类2")
                .name("名称2")
                .key("测试的key")
                .addString("winston.bpmn20.xml", text);

        // 部署流程
        Deployment deploy = deploymentBuilder.deploy();

        System.out.println("------"+deploy.getId());
    }

    /**
     * @auther: Winston
     * @Description: 输入流方式部署
     * @param: 
     * @return: 
     * @date: 2019/8/28 14:43
     */
    @Test
    public void deploymentBuild4(){
        // 获取流程
        InputStream resourceAsStream =
                SpringTest.class.getClassLoader().getResourceAsStream("winston_test.bpmn20.xml");
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("测试分类2")
                .name("名称2")
                .key("测试的key")
                .addInputStream("winston.bpmn20.xml", resourceAsStream);

        // 部署流程
        Deployment deploy = deploymentBuilder.deploy();

        System.out.println("------"+deploy.getId());
    }

    /**
     * @auther: Winston
     * @Description: 压缩流方式部署，可同时部署多个流程
     * @param:
     * @return:
     * @date: 2019/8/28 14:43
     */
    @Test
    public void deploymentBuild5(){
        // 获取流程
        InputStream resourceAsStream =
                SpringTest.class.getClassLoader().getResourceAsStream("resources.zip");
        ZipInputStream zipInputStream = new ZipInputStream(resourceAsStream);
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category("测试分类3")
                .name("名称3")
                .key("测试的key3")
                .addZipInputStream(zipInputStream);

        // 部署流程
        Deployment deploy = deploymentBuilder.deploy();

        System.out.println("------"+deploy.getId());
    }

    /**
     * @auther: Winston
     * @Description: 流程定义信息获取
     * @param:
     * @return:
     * @date: 2019/8/28 15:15
     */
    @Test
    public void createProcessDefinitionQuery(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        for(ProcessDefinition definition : list){
            System.out.println(definition.getId());
            System.out.println(definition.getCategory());
            System.out.println(definition.getDeploymentId());
            System.out.println("-----------");
        }
    }

    /**
     * @auther: Winston
     * @Description: 删除流程定义
     * @param:
     * @return:
     * @date: 2019/8/28 16:13
     */
    @Test
    public void deleteDeployment(){
        String deploymentId = "160001";
        repositoryService.deleteDeployment(deploymentId);
    }

    /**
     * @auther: Winston
     * @Description: 级联删除流程定义
     * 会删除当前流程定义下所有的流程实例
     * @param:
     * @return:
     * @date: 2019/8/28 16:13
     */
    @Test
    public void deleteProcessCaseCade(){
        String deploymentId = "62501";
        repositoryService.deleteDeployment(deploymentId, true);
    }

    /**
     * @auther: Winston
     * @Description: 获取流程定义图片
     * @param:
     * @return:
     * @date: 2019/8/28 16:13
     */
    @Test
    public void viewImage() throws IOException {
        String deploymentId = "82501";
        List<String> deploymentResourceNames = repositoryService.getDeploymentResourceNames(deploymentId);
        System.out.println(deploymentResourceNames);
        String imageName = null;
        for(String name : deploymentResourceNames){
            if(name.indexOf(".png") > 0){
                imageName = name;
            }
            System.out.println(imageName);
        }
        if(imageName != null){
            File file = new File("J:/workspaceMy/myflowable/idm-study-spring/"+imageName);
            InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, imageName);
            FileUtils.copyInputStreamToFile(resourceAsStream, file);
        }
    }

    /**
     * @auther: Winston
     * @Description: 查询部署对象
     * @param:
     * @return:
     * @date: 2019/8/28 16:36
     */
    @Test
    public void queryDeployMent(){
        List<Deployment> list = repositoryService
                .createDeploymentQuery()
                .deploymentCategory("测试分类2")
                .deploymentId("80001")
                .list();
        for(Deployment deployment : list){
            System.out.println("----------");
            System.out.println(deployment.getId());
            System.out.println(deployment.getKey());
        }
    }

    /**
     * @auther: Winston
     * @Description: 本地sql查询
     * @param: select RES.* from ACT_RE_DEPLOYMENT RES WHERE RES.ID_ = ? and RES.CATEGORY_ = ? order by RES.ID_ asc
     * @return:
     * @date: 2019/8/28 16:46
     */
    @Test
    public void createNativeDeploymentQuery(){
        List<Deployment> list = repositoryService.createNativeDeploymentQuery()
                .sql("select RES.* from ACT_RE_DEPLOYMENT RES WHERE RES.ID_ = '82501' and RES.CATEGORY_ = '测试分类2' order by RES.ID_ asc")
                .list();
        for(Deployment deployment : list){
            System.out.println("----------");
            System.out.println(deployment.getId());
            System.out.println(deployment.getKey());
        }
    }

}
