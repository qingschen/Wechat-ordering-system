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
                            <th>类目id</th>
                            <th>名字</th>
                            <th>type</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list productCategoryList as productCategory>
                        <tr>
                            <td>${productCategory.categoryId}</td>
                            <td>${productCategory.categoryName}</td>
                            <td>${productCategory.categoryType}</td>
                            <td>${productCategory.createTime}</td>
                            <td>${productCategory.updateTime}</td>
                            <td>
                                <a href="index?categoryId=${productCategory.categoryId}">修改</a>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>