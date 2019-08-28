package com.winston.idm;

import org.flowable.common.engine.api.management.TableMetaData;
import org.flowable.common.engine.api.management.TablePage;
import org.flowable.idm.api.*;
import org.flowable.idm.engine.IdmEngine;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.persistence.entity.GroupEntityImpl;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName IdmTest
 * @Author: Winston
 * @Description: 用户、组、权限操作
 * @Date:Create：in 2019/8/23 16:12
 * @Version：
 */
public class IdmTest {

    IdmEngine idmEngine;
    IdmIdentityService idmIdentityService;
    IdmEngineConfiguration idmEngineConfiguration;
    IdmManagementService idmManagementService;

    @Before
    public void init(){
        InputStream inputStream = IdmTest.class.getClassLoader().getResourceAsStream("flowable.idm.cfg.xml");
        idmEngine = IdmEngineConfiguration.createIdmEngineConfigurationFromInputStream(inputStream).buildIdmEngine();

        idmIdentityService = idmEngine.getIdmIdentityService();
        idmEngineConfiguration = idmEngine.getIdmEngineConfiguration();
        idmManagementService = idmEngine.getIdmManagementService();
        String name = idmEngine.getName();

        System.out.println("引擎的名称： "+name);
    }

    @Test
    public void testInit(){

    }

    // 添加用户
    @Test
    public void testAddUser(){
        UserEntityImpl user = new UserEntityImpl();
        user.setFirstName("winston");
        user.setEmail("winston@qq.com");
        user.setId("winston");
        user.setPassword("1");
        user.setRevision(0);
        idmIdentityService.saveUser(user);
    }

    // 添加组
    @Test
    public void testAddGroup(){
        GroupEntityImpl group = new GroupEntityImpl();
        group.setId("kaifabu");
        group.setRevision(0);
        group.setName("开发部");
        idmIdentityService.saveGroup(group);
    }

    // 添加权限
    @Test
    public void testAddPrivilege(){
        idmIdentityService.createPrivilege("winston-idm");
    }

    // 把用户添加组
    @Test
    public void testUserAddGroup(){
        idmIdentityService.createMembership("winston", "yunying");
    }

    // 为用户添加权限
    @Test
    public void testUserAddPrivilege(){
        Privilege privilege = idmIdentityService.createPrivilegeQuery().privilegeName("winston-idm").singleResult();
        idmIdentityService.addUserPrivilegeMapping(privilege.getId(), "winston");
    }

    // 为组添加权限
    @Test
    public void testGroupAddPrivilege(){
        Privilege privilege = idmIdentityService.createPrivilegeQuery().privilegeName("winston-idm").singleResult();
        Group group = idmIdentityService.createGroupQuery().groupNameLike("运%").singleResult();
        idmIdentityService.addGroupPrivilegeMapping(privilege.getId(), group.getId());
    }

    // 查询所有用户
    @Test
    public void createUserQuery(){
        List<User> users = idmIdentityService.createUserQuery().list();
        for(User user : users){
            System.out.println(user.getId());
            System.out.println(user.getFirstName());
            System.out.println(user.getPassword());
            System.out.println("-----------user-----------");
        }
    }

    // 根据id查询用户
    @Test
    public void createUserQueryById(){
        User user = idmIdentityService.createUserQuery().userId("winston").singleResult();
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println("------------user----------");
    }

    // 查询所有组
    @Test
    public void createGroupQuery(){
        List<Group> groups = idmIdentityService.createGroupQuery().list();
        for(Group group : groups){
            System.out.println(group.getId());
            System.out.println(group.getName());
            System.out.println("---------group--------");
        }
    }

    // 根据组名模糊查询
    @Test
    public void createGroupQueryById(){
        Group group = idmIdentityService.createGroupQuery().groupNameLike("%营%").singleResult();
        System.out.println(group.getId());
        System.out.println(group.getName());
        System.out.println("---------group--------");
    }

    // 查询所有权限
    @Test
    public void createPrivilegeQuery(){
        List<Privilege> privileges = idmIdentityService.createPrivilegeQuery().list();
        for(Privilege privilege : privileges){
            System.out.println(privilege.getId());
            System.out.println(privilege.getName());
            System.out.println("---------privilege--------");
        }
    }

    // 根据权限名查询
    @Test
    public void createPrivilegeById(){
        Privilege privilege = idmIdentityService.createPrivilegeQuery().privilegeName("winston-idm").singleResult();
        System.out.println(privilege.getId());
        System.out.println(privilege.getName());
        System.out.println("---------privilege--------");
    }

    // 查询表里的数据有多少条
    @Test
    public void queryTablesCount(){
        Map<String, Long> tableCount = idmManagementService.getTableCount();
        Set<Map.Entry<String, Long>> entries = tableCount.entrySet();
        for(Map.Entry<String, Long> entry : entries){
            System.out.println("key： "+ entry.getKey());
            System.out.println("value： "+ entry.getValue());
        }
    }

    // 查询实体对应的表名
    @Test
    public void queryTableName(){
        String tableName = idmManagementService.getTableName(User.class);
        System.out.println(tableName);
    }

    // 查询表的数据结构
    @Test
    public void queryTableMetaData(){
        TableMetaData tableMetaData = idmManagementService.getTableMetaData("ACT_ID_USER");
        System.out.println(tableMetaData.getTableName());
        System.out.println(tableMetaData.getColumnNames());
        System.out.println(tableMetaData.getColumnTypes());
    }

    // 查询表的属性
    @Test
    public void queryTablePage(){
        TablePage tablePage = idmManagementService.createTablePageQuery().tableName("ACT_ID_USER").listPage(0, 10);
        System.out.println(tablePage.getRows());
    }

}
