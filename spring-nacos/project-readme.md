### 网关配置
server:
  port: 10005
 ---
### USER 服务
application.name = user-service
server:
  port: 10000
 ---
### ORDER 服务
application-name = order-service
server:
  port: 10001

---

### 工具类
现在所有的相关工具全都在authority这个模块中，可以考虑单独将一些工具建立一个JAR

### jerry模块
该模块无实际用途，作为代码存放