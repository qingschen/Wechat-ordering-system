<html>
<#--引入头部模板-->
<#include "../common/header.ftl">
<body>
    <div id="wrapper" class="toggled">
        <#--边栏sidebar-->
        <#--include可以将后面路径中的模板引入，可ctrl+点击，看看能否点进去，点进去则路径正确-->
        <#include "../common/nav.ftl">

        <#--主要内容content-->
        <div id="page-content-wrapper">
            <div class="container">
                <div class="row clearfix">
                    <div class="col-md-4 column">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>订单id</th>
                                <th>订单总金额</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.orderAmount}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-12 column">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>商品id</th>
                                <th>商品名称</th>
                                <th>价格</th>
                                <th>数量</th>
                                <th>总额</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list orderDTO.getOrderDetailList() as orderDetail>
                            <tr>
                                <td>${orderDetail.productId}</td>
                                <td>${orderDetail.productName}</td>
                                <td>${orderDetail.productPrice}</td>
                                <td>${orderDetail.productQuantity}</td>
                                <td>${orderDetail.productPrice * orderDetail.productQuantity}</td>
                            </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-12 column">
                    <#if orderDTO.getOrderStatusEnum().msg == "新订单">
                        <a href="finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-primary btn-lg">完结订单</a>
                        <a href="cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-danger btn-lg">取消订单</a>
                    </#if>
                    </div>
                </div>
            </div>
        </div>

    </div>
</body>
</html>