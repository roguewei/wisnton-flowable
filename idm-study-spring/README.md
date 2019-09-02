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

## 启动流程实例时动态设置变量，分配任务方式一（设置固定任务处理人）
##### 参考SpringTaskTest
##### 部署时使用的流程文件是singletask2.bpmn20.xml
##### startProcessInstanceByKey2()

## 启动流程实例时通过任务监听器设置任务处理人，分配任务方式二（监听器设置任务处理人）
##### 参考SpringTaskTest
##### 部署时使用的流程文件是singletask4.bpmn20.xml
##### startProcessInstanceByKey4()

## 分配任务方式三——任务认领
##### 参考SpringTaskTest
##### 部署时使用的流程文件是singletask4.bpmn20.xml
##### setAssigneeTask()

## 查询组任务成员历史列表
##### 参考SpringTaskTest
##### findGroupHisUser()

## 查询组任务
##### 参考SpringTaskTest
##### findGroupTask()

## 拾取、认领任务
##### 参考SpringTaskTest
##### claim()

# 服务任务
##### 参考SpringServiceTaskTest

## 脚本使用
#### SpringServiceScriptTaskTest

# 网关
#### SpringGatewayTest
## 排他网关
#### 根据xml流程文件里面的顺序去执行，比如同时满足多个条件
#### 该网关会选择排在最前面的条件去执行
## 并行网关
#### 并行网关通常是成对出现的，第一个代表分流，第二个表示聚合
#### 只有分流没有聚合，那么整个流程是不完整的，最终无法结束
#### 并行网关上的条件是不生效的
#### 如果某网关跟着多个任务时，需要全部任务都完成之后才会执行下一个任务

