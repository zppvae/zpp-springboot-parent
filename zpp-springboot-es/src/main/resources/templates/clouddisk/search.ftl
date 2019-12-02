<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>网盘搜索引擎</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"></script>
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body  style="display: block; margin: 0 auto; width: 50%; " >
<div style="width:100%;height:60px;" align="center">
    <h2 style="color:#985f0d;">搜索引擎</h2>
</div>
<br/>
<div class="bs-example" data-example-id="striped-table">
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th style="text-align:center;" scope="row">链接名称</th>
            <th style="text-align:center;">文件大小GB</th>
            <th style="text-align:center;">分享人</th>
            <th style="text-align:center;">云盘地址</th>
        </tr>
        </thead>
        <tbody>
        <#list page.content as p>
            <tr >
            <th style="text-align: left;" >


            <#if keyword??>
                ${p.name?replace(keyword, '<span style="color: red">${keyword}</span>')}
            <#else>
                ${p.name}
            </#if>


            </th>
            <th style="text-align: center;">${p.fileSize}</th>
            <th style="text-align: center;">${p.shareUser}</th>
            <th style="text-align: center;"><a href="${p.address}">云盘地址</a> </th>
            </tr>
        </#list>
        </tbody>
    </table>
    <div style="font-size: 21px;">
        <#list 1..totalPage as i>
            <#if keyword??>
                <a href="/clouddisk/search?keyword=${keyword}&page=${i-1}" >${i}</a>
            <#else>
                <a href="/clouddisk/search?page=${i-1}" >${i}</a>
            </#if>

        </#list>
        页
    </div>
    <br/>
    <div align="center">
        <span style="font-size: 18px;" >检索出${total}条数据,耗时:${time}毫秒</span>
    </div>
</div>
</body>
</html>
