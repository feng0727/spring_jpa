# 使用说明

### 项目介绍
    基于Springboot & Dubbo集成开发脚手架，帮助开发人员快速实现Java微服务应用的创建、开发、本地调试及部署。

### 功能特性
-   集成`swagger`，通过`yaml`摘要方式来声明和管理`REST API`；
-   实现`REST API`统一认证功能；
-   集成`Apollo`分布式配置中心，集中化管理应用不同环境、不同集群的配置，配置修改后能够实时推送到应用端，并且具备规范的权限、流程治理等特性；
-   提供功能权限集成、`ACL`数据权限集成，`JPA`方式访问数据库样例，为实际开发提供指导。

### 目录结构
```
src
  |_main
    |_frontend	                
    |_java
    |  |_${basepackage}	                   // 存放启动类
    |  |_${basepackage}.business           // 业务层的接口声明
    |  |_${basepackage}.business.bo        // 业务对象
    |  |_${basepackage}.business.impl      // 接口实现
    |  |_${basepackage}.common             // 存放通用配置处理类
    |  |_${basepackage}.config             // 处理差异化配置的类（公共的由平台提供）
    |  |_${basepackage}.dao                // 数据层的访问接口声明
    |  |_${basepackage}.dao.po             // 与数据库表结构对应，通过dao层向上传输数据源对象
    |  |_${basepackage}.exception          // 异常处理相关类
    |  |_${basepackage}.frontapi           // 提供给前端调用的 API 接口声明（自动生成）
    |  |_${basepackage}.frontapi.impl      // 接口实现类（即Controller）
    |  |_${basepackage}.frontapi.vo        // 显示层对象
    |  |_${basepackage}.serviceapi         // 同frontapi
    |  |_${basepackage}.serviceapi.dto     // 数据传输对象
    |  |_${basepackage}.serviceapi.impl    // 同frontapi
    |  |_${basepackage}.openapi            // 同frontapi
    |  |_${basepackage}.openapi.dto        // 同serviceapi
    |  |_${basepackage}.openapi.impl       // 同frontapi
    |_resources
      |_api                                // API Swagger yaml描述文件
      |   |_${appLowerCode}-frontapi.yml        
      |   |_${appLowerCode}-serviceapi.yml
      |   |_${appLowerCode}-openapi.yml
      |_i18n                               // 存放国际化文件
      |   |_${appLowerCode}-zh_CN.lang
      |   |_${appLowerCode}-en_US.lang
      |_${appLowerCode}-main-base.yml      	   // 基础配置文件
      |_${appLowerCode}-main-config-dev.yml     // 开发环境配置文件
      |_${appLowerCode}-main-config-prod.yml    // 生成环境配置文件
      |_${appLowerCode}-main-config-test.yml    // 测试环境配置文件
      |_logback-spring.xml                 // 日志配置文件
```
    说明：${basepackage} 为根包名称，${appLowerCode} 为应用编码小写，与 ${appLowerCode}-main-base.yml 中的 biz.app.lower-code 保持一致。

### 开发流程
1.  `IDE Terminal`中执行`umvn generate-sources`生成初始默认源码，避免IDE检测缺少类而提示错误信息；
2.  编写`Swagger API`声明文件（如已有可忽略），语法参考：https://swagger.io/docs/specification/2-0/basic-structure/，语法验证：<https://editor.swagger.io/>
3.  `IDE Terminal`中执行`umvn generate-sources`命令以生成`API`相关源码（接口和实体类）、前端`js`源码（`frontend/src/utils/swagger-api`目录下）以及API说明文档（`target/api`目录下），
     当`API`定义变化时，需要再次执行`umvn generate-sources`；
3.  实现后端`java`业务逻辑；
4.  根据生成的`html API`文档实现前端业务逻辑，[前端说明](src/frontend/README.md) ；
5.  执行`umvn local-test`进行前后端集成测试；
		

### 注意事项
-   配置文件请在 `resources -> ${appCode}-main-base.yml`文件进行查阅，默认`profile`生效`dev`环境，可根据实际情况进行修改；
-   `biz`开头为自定义的业务配置项
-   `apollo`说明
	```
	apollo:
	  meta: http://apollo_configservice:7546
	    bootstrap:
	      enabled: true
	      namespaces: common,${biz.app.lower-code}-main-config.yml
	    autoUpdateInjectedSpringProperties: true
	app:
	  id: ${biz.app.code}
	```
	说明：
		`apollo.meta`: apollo configservice地址
		`apollo.bootstrap.enabled`：是否开启从远处获取配置信息，默认为`true`
		`apollo.bootstrap.namespaces`：`apollo`命名空间，`common`为公共命名空间，`*-main-config`为当前模块命名空间

### 产品注册安全设置
-   默认产品注册以及安全设置使用dubbo sdk
     使用方式如下：
     
      <!-- dubbo 应用产品注册，权限设置，权限验证  dubbo与 rest 只能引用一个-->
         <dependency>
           <groupId>uyun.springboot</groupId>
           <artifactId>own-consumer-spring-boot-starter</artifactId>
           <version>1.1.0</version>
         </dependency>
         
-   使用restfull 进行产品注册以及安全设置 openApi sdk
     使用方式如下：
    <!-- restfull 应用产品注册，权限设置，权限验证 dubbo与 rest 只能引用一个 -->
         <dependency>
           <groupId>uyun.springboot</groupId>
           <artifactId>third-consumer-spring-boot-starter</artifactId>
           <version>1.1.0</version>
         </dependency>
    
-  两种sdk只能引用其中一个，否则可能出现兼容问题。

### 产品打包
-   windows环境 : mvn clean package 或 mvn clean package -P window
-   Linux环境 ：mvn clean package -P linux
    将构建好的`tgz`（`target`目录下）包上传至`OMP`中进行部署。
    更加详细使用请参考：http://kb.uyunsoft.cn/pages/viewpage.action?pageId=33392484
    
### FAQ
1.  应用启动失败如何分析？   
    分析启动日志（`根据实际需要调整日志级别`），一般可能是端口冲突、或连接`apollo`、数据库失败所致，可检查配置项是否正确，调式环境应用是否运行正常。
2.  本地调试如何使用其他数据库而非`H2`？  
    注释掉`H2`相关配置，追加对应数据源配置即可（可参考`MySQL`配置）
3.  本地调试连接数据库失败？  
    本地测试默认激活`dev`环境，数据库采用`H2`，如果`dev`环境所连的`apollo`上同时配置了其它数据源的配置，由于`apollo`自身优先级比较高，导致加载了`apollo`上的配置，而该配置如果本身配置不对或者做了访问控制便会导致应用连接失败；
	该情况可`临时`去掉`apollo.bootstrap.namespaces`中的`config`命名空间只保留`common`。
4.  应用启动时加载的配置与预期不符？  
    `Apollo`客户端会把从服务端获取到的配置在本地文件系统缓存一份，用于在遇到服务不可用，或网络不通的时候，依然能从本地恢复配置，不影响应用正常运行。可能是该原因所致，找到`/opt/data`目录删除后重试。
5.  mvn clean package yarn打包失败？
    如果出现，请检测node yarn 是否正确安装：
    如何解决：手工打包，
    进入项目 src/main/bin 执行；
    windows下执行: build.bat
    linux下执行: build.sh
    

