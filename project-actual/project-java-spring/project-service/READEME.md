### 模块依赖

1. common 模块 ：DO相关枚举、等
2. api 模块 ：RPC 暴露
3. biz 模块 ：RPC 实现、Controller 、Service 、Mapper 等
4. integrate 模块 ：Client（调用其它服务的 RPC）

### 模型

```
/**
 * Controller    XxxReqVO / XxxRespVO
 * Service       XxxReqModel / XxxRespModel
 * Mapper        XxxDO or XxxPOJO
 * 
 * Api           XxxReqDTO / XxxRespDTO
 * Client        XxxParam / XxxResult
 */
```