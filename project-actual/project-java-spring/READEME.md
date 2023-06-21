### 模块

1. common 模块 ：DO相关枚举、等
2. facade 模块 ：rpc interface
3. integrate 模块 ：client（调用其它服务的 RPC）
4. biz 模块 ：rpcImpl 、API 、Service 、Mapper 等

``` yaml
common:
  com.code.xxx:
    mvc:
      dal.domain.enums: 实体类相关枚举

facade:
  com.code.xxx:
    facade: rpc 接口
      dto: req 、resp

integration:
  com.code.xxx:
    client: client 接口
      dimain: param 、result
      impl:

biz:
  com.code.xxx:
    convert: 转换器
    facade.impl: rpcImpl
    job: 
    mvc:
      # 供前端调用的 API
      api: api 接口
        request: req
        response: resp
        impl: 
      # 存储相关
      dal:
        domain: DO 、POJO
        mapper: 
        redis: redis 操作
      service: service 接口
        model: reqDTO 、respDTO
        impl: 
      biz: biz 接口(service/facade公共逻辑)
        model: reqDTO 、respDTO
        impl:
```

说明：

1. service :
   - 只做数据查询操作，业务逻辑交给 apiImpl 、facadeImpl
   - 可共用的业务逻辑写到 biz 中
2. api

### 模型

```
/**
 * Api                  XxxReqVO / XxxRespVO
 * Facade               XxxReqDTO / XxxRespDTO
 * Service / Biz        XxxReqModel / XxxRespModel
 * Mapper               XxxDO or XxxPOJO
 * 
 * Client               XxxParam / XxxResult
 */
```