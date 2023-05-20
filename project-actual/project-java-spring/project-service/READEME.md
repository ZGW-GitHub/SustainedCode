### 模块依赖

1. common 模块 ：DO相关枚举、等
2. facade 模块 ：rpc interface
3. biz 模块 ：rpcImpl 、API 、Service 、Mapper 等
4. integrate 模块 ：Client（调用其它服务的 RPC）

### 模型

```
/**
 * Api                  XxxRequest / XxxResponse
 * Facade               XxxReqDTO / XxxRespDTO
 * Service / Biz        XxxReqModel / XxxRespModel
 * Mapper               XxxDO or XxxPOJO
 * 
 * Client               XxxParam / XxxResult
 */
```