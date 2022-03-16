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
                            <th>商品id</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list productInfoPage.content as productInfo>
                        <tr>
                            <td>${productInfo.productId}</td>
                            <td>${productInfo.productName}</td>
                            <td><img height="100" width="100" src="${productInfo.productIcon}" alt=""></td>
                            <td>${productInfo.productPrice}</td>
                            <td>${productInfo.productStock}</td>
                            <td>${productInfo.productDescription}</td>
                            <td>${productInfo.categoryType}</td>
                            <td>${productInfo.createTime}</td>
                            <td>${productInfo.updateTime}</td>
                            <td>
                                <a href="index?productId=${productInfo.productId}">修改</a>
                            </td>
                            <td>
                                <#if productInfo.getProductStatusEnum().msg == "在售">
                                    <a href="off_sale?productId=${productInfo.productId}">下架</a>
                                <#else >
                                    <a href="on_sale?productId=${productInfo.productId}">上架</a>
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
                    <#list 1..productInfoPage.getTotalPages() as index>
                    <#--将当前页设置为灰色-->
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#--其他页设置跳转链接-->
                        <#else>
                            <li><a href="list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>
                    <#--gte，>=-->
                    <#if currentPage gte productInfoPage.getTotalPages()>
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
</body>
</html>