最近在学习appium,一直想做一个自动化平台，达到数据驱动，测试案例等这些数据的分离。打算分三步来学习：

熟悉appium的基本使用方法，可以编写简单自动化脚本

appium的数据分离。比如用excel管理数据驱动

appium 的自动化平台,所有数据存放在数据库

分了如下四大模块：

testData 数据准备

testCase 测试用例的编写

testDriver 测试驱动

testRunner 测试运行

测试结果
1.打算用pyh手动拼接html页面的方式，
2.测试结果导出成 Junit 格式的 xml 文件，然后用其他工具生产

关于测试用例的编写：
暂时也还没有想好，robotframework 不支持python3,也有同学推荐使用 testlink 进行二次开发，这个记录下。


此次更新了如下：
整个测试框架目标，python+selenium构建一个跨平台，支持ios,android,web，自动化测试，接口测试，性能测试

testRunner 下面的runner.py 是框架的入口

2015-11-10 更新日志

引用yaml来配置测试案例，代替xml

更新了自定义日志函数

所有的地方只要修改testDriver.webChatHome.py 即可，其他地方无需修改




