# MySQL高级篇

## Linux安装MySQL

### 	虚拟机克隆

- 

![image-20220821234407277](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220821234407277.png)

- 修改mac地址

  ![image-20220821234505004](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220821234505004.png)

-  修改主机名称

  ```bash
  vim /etc/hostname
  ```

- 修改ip地址

  - 查看虚拟网络配置

    ![image-20220822003533173](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220822003533173.png)

    ![image-20220822003626008](C:\Users\asus\AppData\Roaming\Typora\typora-user-images\image-20220822003626008.png)

  - 

  ```bash
  vim /etc/sysconfig/network-scripts/ifcfg-ens33
  
  TYPE="Ethernet"
  PROXY_METHOD="none"
  BROWSER_ONLY="no"
  BOOTPROTO="static"
  DEFROUTE="yes"
  IPV4_FAILURE_FATAL="no"
  IPV6INIT="yes"
  IPV6_AUTOCONF="yes"
  IPV6_DEFROUTE="yes"
  IPV6_FAILURE_FATAL="no"
  IPV6_ADDR_GEN_MODE="stable-privacy"
  NAME="ens33"
  UUID="3c1015a7-c7a1-4630-b218-5fad9d37074e"
  DEVICE="ens33"
  ONBOOT="yes"
  # ip address 上述查看的结果进行配置
  IPADDR-192.168.254.160
  NETMASK=255.255.254.0
  # gateway
  GATEWAY=192.168.254.2
  # DNS1
  DNS1 = 192.168.254.2
  ```

- 修改UUID

- 重启网络

  - 重启

    ```bash
    service network restart 
    ```

  - Redirecting to /bin/systemctl restart network.service
    Failed to restart network.service: Unit network.service not found.

    ```bash
    service network-manager restart
    ```

  - Failed to restart network-manager.service: Unit network-manager.service not

    ```bash
    nmcli c reload
    nmcli networking off
    nmcli networking on
    ```

  - clone之后没有ens33

    ```bash
    ifconfig ens33 主机名
    ```

## 索引及调优篇

## 