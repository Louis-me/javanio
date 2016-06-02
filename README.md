#java nio 非阻塞压力测试客户端

* 非阻塞 IO
* 其实无论是用 Java NIO 还是用 Netty，达到百万连接都没有任何难度。因为它们都是非阻塞的 IO，不需要为每个连接创建一个线程了。
* nio 和 io的区别
* nio的核心：Channel 通道,Buffer 缓冲区,Selector 选择器

## 如果是百万级测试，怎么去找那么多机器？

* 按照nio上面的做法，单机最多可以有 6W 的连接，百万连接起码需要17台机器！
* 如何才能突破这个限制呢？其实这个限制来自于网卡。 可以通过使用虚拟机，并且把虚拟机的虚拟网卡配置成了桥接模式解决了问题。
	* 根据物理机内存大小，单个物理机起码可以跑4-5个虚拟机，所以最终百万连接只要4台物理机就够了。
	
* 此次学习用的是netty 的方式，netty可以写server和client，代码中是client的学习
 * http://netty.io/wiki/user-guide-for-4.x.html#wiki-h3-10 
 
 ### 端口受限设置参考[说明文档](https://github.com/284772894/javanio/blob/master/mark.md)