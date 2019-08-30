# flowable


# 第八章
## 历史数据查询
##### 历史任务表act_hi_taskinst
##### 历史任务表的数据跟运行任务表的数据一起插入数据库，并且在一个事物里面执行
##### 历史流程实例表act_hi_procinst
##### 历史流程实例与运行流程实例是一对一关系

## 流程相关操作

### 流程发起人设置
##### 参考SpringProcessTest
##### setAuthenticatedUserId()

## 开始节点initator
##### < startEvent id="startEvent1" activiti:initiator="winston"/>

## dataObject使用
##### flowable允许开发人员在流程文档中为流程或子流程定义dataObject元素，该元素可以指定变量的id、
##### 名称、数据类型等，支持的数据类型有string,boolean,datatime,int,long等。流程实例启动时会将
##### dataObject元素的信息自动转换为流程实例变量存在，变量名称对应dataObject元素中定义的‘name’值

## 删除实例
##### 参考SpringProcessTest
##### deleteProcessInstance()

## 获取流程实例运行的活动节点
##### 参考SpringProcessTest
##### getActiveActivityIds()

## 流程定义挂机和解除挂起
##### 参考SpringProcessTest
i##### sProcessDefinitionSuspended()
##### 流程定义表act_re_procdef中SUSPENSION_STATE_ 1表示未挂起，2表示挂起

# 第九章

## 查询执行实例
##### 参考SpringTaskTest
##### activityId()

## 触发执行实例继续往下运转
##### 参考SpringTaskTest
##### trigger()

## 查询个人任务
##### 参考SpringTaskTest
##### findMyTaskList()

## 完成个人任务
##### 参考SpringTaskTest
##### completeTask()

## 