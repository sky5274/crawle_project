# data batch handel server
1: 数据批处理平台

	1.1：数据处理过程：  分布式 数据收集器-> 数据处理调度--> 数据过滤、数据转换 （js）—> 数据汇总,按公式计算数据 ->分布式数据处理客户端
	
	1.2：数据处理模式：1).分布式客户端处理完所有流程，2).单个客户端收集到所有数据
	
	1.3：数据分割原则：1).按条数进行分割，2).平均分割    
	
	1.4：本地缓存：本地缓存任务公式与script计算引擎
	
	1.5：使用quartz管理批处理任务