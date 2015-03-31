<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>

    <link href="${rc.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="//cdn.datatables.net/plug-ins/f2c75b7247b/integration/bootstrap/3/dataTables.bootstrap.css"/>

    <script src="${rc.contextPath}/resources/js/jquery-2.1.1.min.js"></script>
    <script src="${rc.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="//cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>
    <script src="//cdn.datatables.net/plug-ins/f2c75b7247b/integration/bootstrap/3/dataTables.bootstrap.js"></script>
    <script src="${rc.contextPath}/resources/js/script.js"></script>

</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-xs-4">
            <ul>
            <#list forms?keys as key>
                <#assign form = forms[key]/>
                <li class="js-form-selector-wrapper">
                    <a class="js-form-selector<#if key != current> active</#if>" data-form="${key}">
                    ${form.tableName}
                    </a>
                </li>
            </#list>
            </ul>
        </div>
        <div class="col-xs-8 js-table-content">
            <#include "table.ftl"/>
        </div>
    </div>
</div>


</body>
</html>