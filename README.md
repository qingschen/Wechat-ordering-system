# Wechat-ordering-system 微信点餐系统
*Java SpringBoot框架*

项目功能：

- 该项目分为买家端和买家端。
- 买家在微信内访问链接可进行浏览商品、加购物车、下单商品、支付订单操作，操作成功后微信会推送下单成功通知。
- 卖家在浏览器访问链接，扫码登录后可以对商品及订单数据库进行增删改查，并能够实时接收到买家下单信息。

项目涉及技术点：

1. 使用IDEA搭建，使用SpringBoot框架
2. 数据库采用MySQL,使用JPA方式调用，使用Redis数据库实现卖家登录数据缓存
3. 实现微信网页授权和支付功能
4. 使用AOP实现卖家身份认证
5. 买家端和卖家端通过websocket联通

# 系统使用指南

1. 打开项目sell

2. 打开虚拟机centos7

   ```
   账号root
   密码123456
   ```

3. 查看虚拟机ip地址与项目所在计算机的ip地址。做以下更改：

   1. 修改项目中的mysql数据库地址(application.yml中)，改为虚拟机ip地址

      ```
      使用ifconfig查看虚拟机ip地址
      ```

   2. 设置一个统一的域名用于访问虚拟机和主机

      1. 修改centos配置文件

         1. ```
            vim /usr/local/nginx/conf/nginx.conf
            ```

         2. 将第47行的proxy_pass http://后的ip值改为主机ip

         3. 将36行的server_name 改为sell.com,代表以后通过sell.com访问主机

         4. 重启配置

            ```
            nginx -s reload
            ```

      2. 修改主机配置文件

         编辑C:\Windows\System32\drivers\etc\hosts文件，最后一行添加

         ```
         # 将192.168.1.8的域名设为sell.com
         192.168.1.8	sell.com 
         ```

         此时主机可以通过sell.com访问虚拟机，192.168.1.8为示例虚拟机的ip

4. 手机连接到Fiddler。

5. 启动项目，访问相应地址即可

   1. 买家端通过手机微信访问sell.com，进行图形页面操作
   2. 卖家端，通过浏览器访问网址。
