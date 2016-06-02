## 端口受限设置
### windows –客户端
* 打开注册表：regedit HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\ Services\TCPIP\Parameters
* 新建 DWORD值，name：TcpTimedWaitDelay，value：0（十进制） –> 设置为0
* 新建 DWORD值，name：MaxUserPort，value：65534（十进制） –> 设置最大连接数65534 重启系统

### liunx客户端
* cat /proc/sys/net/ipv4/ip_local_port_range  值为32768 61000
	* 大概也就是共61000-32768=28232个端口可以使用，单个IP对外只能发送28232个TCP请求。以管理员身份，把端口的范围区间增到最大：
	* echo "1024 65535"> /proc/sys/net/ipv4/ip_local_port_range  现在有64511个端口可用. 
	* 以上做法只是临时，系统下次重启，会还原。 更为稳妥的做法是修改/etc/sysctl.conf文件，增加一行内容 net.ipv4.ip_local_port_range= 1024 65535
* sysctl -p
	* 现在可以使用的端口达到64510个（假设系统所有运行的服务器是没有占用大于1024的端口的，较为纯净的centos系统可以做到），要想达到50万请求，还得再想办法。

* 增加IP地址
* 一般假设本机网卡名称为 eth0，那么手动再添加几个虚拟的IP：
* ifconfig eth0:1 192.168.190.151
* ifconfig eth0:2 192.168.190.152 ......
* 或者偷懒一些：
	* for i in `seq 1 9`; do ifconfig eth0:$i 192.168.190.15$i up ; done
	* 这些虚拟的IP地址，一旦重启，或者 service network restart 就会丢失。
	* 为了模拟较为真实环境，在测试端，手动再次添加9个vmware虚拟机网卡


