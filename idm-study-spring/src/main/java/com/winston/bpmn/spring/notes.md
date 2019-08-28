# spring配置风格--SmartLifecycle

### 在使用spring开发时，所有bean都交给spring容器来管理，其中包括每一个bean的加载和初始化，
### 有时候需要在spring加载和初始化所有bean后，接着执行一些任务或者启动需要的异步服务，
### 这时可以使用SmarLifecycle来做。
### SmartLifecycle是一个接口，当spring容器加载所有bean并完成初始化后，会这接着回调该接口类中对应的方法（start()）

# spring配置风格-- 自动部署
<property name="deploymentResources" value="classpath*:.*.bpmn20.xml"/>
<property name="deploymentMode" value=default"/>
### default，resource-parent-folder， single-resource

# spring配置风格不支持同时构造多个流程引擎

# 引擎类与引擎配置类
## 7大引擎配置类，统一继承自AbstractEngineConfiguration类
* IdmEngineConfiguration
* CmmnEngineConfiguration
* FormEngineConfiguration
* DmnEngineConfiguration
* ContentEngineConfiguration
* ProcessEngineConfiguration
* AppEngineConfiguration

## 每一个引擎配置类都有两个配置风格（标准配置风格以及spring配置风格）
## 每一个引擎类都是有引擎配置类构造
