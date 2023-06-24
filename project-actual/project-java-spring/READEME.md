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
    facade:
      domain: param 、result

integration:
  com.code.xxx:
    client: client 接口
      dimain: req 、resp

biz:
  com.code.xxx:
    framework:
      config:
      configuration:
      exception:
    convert:
    facade.impl:
    mvc:
      api:
        domain: req 、resp
      service: 
        domain: BO 、DTO
      biz:
        domain: BO 、DTO
      dal:
        domain: 
          dos: DO
          pojo: Query 、POJO
        mapper: 
        redis: redis 操作
    job: 
    util:
```

#### 说明：

操作：find 、list 、page 、save 、update 、remove

1. api [ req/resp ]:
   - 直接调用 service
2. facade [ param/result ]:
   - 直接调用 service
3. service [ BO/DTO ]:
   - 业务逻辑
4. biz [ BO/DTO ]:
   - 复杂业务逻辑
5. mapper [ DO/XxxQuery/XxxPOJO ]:
   - 数据库操作
6. client [ req/resp ]:
   - 调用 rpc

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

Model 、