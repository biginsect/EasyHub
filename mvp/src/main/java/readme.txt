第一次在项目中使用MVP模式
Model 
   对外提供业务数据API
   内部实现本地数据和网络数据的存取等
   只对Presenter 开放访问，隔离View
Presenter
   持有View 对象，对View 操作
   持有Model 层提供的数据接口对象，可以通过依赖注入解耦
   从数据接口对象中获取数据并处理，更新View
View
   UI层，包含所有UI相关组件
   持有Presenter 对象，可通过依赖注入解耦
   通过Presenter 更新UI