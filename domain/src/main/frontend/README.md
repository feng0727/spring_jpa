# uyun-web-template

基于 react、react-router-dom、mobx、uyd 搭建的基础脚手架。

## 使用

### 本地开发

``` bash
# 默认打开 8000 端口
$ everest start

# 指定端口
$ cross-env PORT=5000 everest start
将 http://localhost:8000/tenant/#/ 修改为 http://localhost:8000 并回车运行。
```

### 本地构建

``` bash
$ everest build
```

## 目录结构

```
app
├── public
│   ├── frontend             # 前端资源，如 Layout
│   ├── static               # 静态资源，如 jQuery
│   └── index.html           # HTML 模版文件
├── src
│   ├── common               # 应用公用配置
│   ├── components           # 业务通用组件
│   ├── layouts              # 通用布局
│   ├── routes               # 业务页面
│   ├── services             # 后台接口服务
│   ├── stores               # 业务逻辑
│   └── utils                # 通用工具函数
├── tests                    # 测试工具
├── README.md
└── package.json
```

## 功能

模板包含基础的数据流、换肤、国际化等方案。

- [数据流设计](http://view.uyundev.cn/docs/scaffold/mobx-pratice-cn)  
- [换肤方案](http://npm.uyundev.cn/package/@uyun/everest-styles) 
- [国际化](http://npm.uyundev.cn/package/@uyun/everest-i18n)
