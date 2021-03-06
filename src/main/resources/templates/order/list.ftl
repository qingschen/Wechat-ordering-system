<html xmlns="http://www.w3.org/1999/html">

    <#--引入头部模板-->
    <#include "../common/header.ftl">
    <body>

        <div id="wrapper" class="toggled">
             <#--边栏sidebar-->
             <#--include可以将后面路径中的模板引入，可ctrl+点击，看看能否点进去，点进去则路径正确-->
             <#include "../common/nav.ftl">

            <#--主要内容content-->
            <div id="page-content-wrapper">
                <#--此处后面要加入list的内容，即之前写的表格之类的主体内容-->
                <div class="container-fluid">
                    <div class="row clearfix">
                    <#--列表-->
                        <div class="col-md-12 column">
                            <table class="table table-bordered table-condensed">
                                <thead>
                                <tr>
                                    <th>订单id</th>
                                    <th>姓名</th>
                                    <th>电话</th>
                                    <th>地址</th>
                                    <th>金额</th>
                                    <th>订单状态</th>
                                    <th>支付状态</th>
                                    <th>创建时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list orderDTOPage.content as orderDTO>
                                    <tr>
                                        <td>${orderDTO.orderId}</td>
                                        <td>${orderDTO.buyerName}</td>
                                        <td>${orderDTO.buyerPhone}</td>
                                        <td>${orderDTO.buyerAddress}</td>
                                        <td>${orderDTO.orderAmount}</td>
                                        <td>${orderDTO.getOrderStatusEnum().msg}</td>
                                        <td>${orderDTO.getPayStatusEnum().msg}</td>
                                        <td>${orderDTO.createTime}</td>
                                        <td>
                                            <a href="detail?orderId=${orderDTO.orderId}">详情</a>
                                        </td>
                                        <td>
                                            <#if orderDTO.getOrderStatusEnum().msg == "新订单">
                                                <a href="cancel?orderId=${orderDTO.orderId}">取消</a>
                                            </#if>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    <#--分页-->
                        <div class="col-md-12 column">
                            <ul class="pagination pull-right">
                            <#--若当前页面<=1，则上一页不能再进行跳转-->
                            <#if currentPage lte 1>
                                <li class="disable"><a href="#">上一页</a></li>
                            <#--否则，跳转到当前页面-1-->
                            <#else>
                            <#--地址不加/则为相对路径，等价于下面的/sell/seller/order/list-->
                                <li><a href="list?page=${currentPage-1}&size=${size}">上一页</a></li>
                            </#if>

                            <#--遍历第1页到第orderDTOPage.getTotalPages()页-->
                            <#list 1..orderDTOPage.getTotalPages() as index>
                            <#--将当前页设置为灰色-->
                                <#if currentPage == index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#--其他页设置跳转链接-->
                                <#else>
                                    <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>
                            <#--gte，>=-->
                            <#if currentPage gte orderDTOPage.getTotalPages()>
                                <li class="disable"><a href="#">下一页</a></li>
                            <#else>
                            <#--地址不加/则为相对路径，等价于下面的/sell/seller/order/list-->
                                <li><a href="list?page=${currentPage+1}&size=${size}">下一页</a></li>
                            </#if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <a id="modal-434860" href="#modal-container-434860" role="button" class="btn" data-toggle="modal">触发遮罩窗体</a>

        <#--弹窗提醒-->
        <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">提醒</h4>
                    </div>
                    <div class="modal-body">你有新的订单</div>
                    <div class="modal-footer">
                        <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
                    </div>
                </div>

            </div>

        </div>

        <#--播放音乐-->
        <audio id="notice" loop="loop">
            <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
        </audio>

        <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script>
            var websocket = null;

            if('WebSocket' in window){
                websocket = new WebSocket('ws://sellproject.nat300.top/sell/webSocket');
            }else{
                alert('该浏览器不支持websocket！');
            }

            websocket.onopen = function (event) {
                console.log('建立连接');
            }

            websocket.onclose = function (event) {
                console.log('连接关闭');
            }

            websocket.onmessage = function (event) {
                console.log('收到消息：' + event.data);
                //弹窗提醒，播放音乐等处理
                $('#myModal').modal('show');

                document.getElementById('notice').play();
            }

            websocket.onerror = function (event) {
                console.log('websocket发生错误！');
            }

            websocket.onbeforeunload = function (event) {
                websocket.close();
            }
        </script>
    </body>
</html>