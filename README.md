#规划求解组件
##组织结构
- JourneyPlanning
	- JourneyPlanningApplication.java
	- Controller
		- AlgorithmController.java
		- RabbitMqController.java
	- Entity
		- Flight.java
		- FlightOption.java
		- Hotel.java
		- HotelOption.java
		- Result.java
	- Service
		- Algorithm.java
		- Count.java
	- Util
		- Customer.java
		- Producer.java
		- Search.java

##源文件功能解释
- JourneyPlanningApplication.java  
组件启动类
- AlgorithmController.java  
提供规划求解服务
- RabbitMqController.java  
组件主入口文件，处理RabbitMq消息
- Flight.java
航班实体类
- FlightOption.java  
航班选项实体类
- Hotel.java  
酒店实体类
- HotelOption.java  
酒店选项实体类
- Result.java  
规划结果实体类
- Algorithm.java  
规划求解算法实现
- Count.java  
计算航班、酒店价格矩阵
- Customer.java  
RabbitMq消息接收类
- Producer.java  
RabbitMq消息发送类
- Search.java  
航班及酒店数据搜索服务实现