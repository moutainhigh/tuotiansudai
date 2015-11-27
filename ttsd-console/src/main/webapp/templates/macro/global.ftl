<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#assign menus=
        [
            {
                "name":"sysMain",
                "header":{"text":"系统主页","link":"/"},
                "sidebar":[]
            },
            {
                "name":"projectMain",
                "header":{"text":"项目管理","link":"/loanList/console"},
                "sidebar":
                [
                    {"name":"ALL","text":"所有的借款","link":"/loanList/console"},
                    {"name":"start","text":"发起借款","link":"/loan"},
                    {"name":"WAITING_VERIFY","text":"初审的借款","link":"/loanList/console?status=WAITING_VERIFY"},
                    {"name":"RAISING","text":"筹款中的借款","link":"/loanList/console?status=RAISING"},
                    {"name":"RECHECK","text":"复审的借款","link":"/loanList/console?status=RECHECK"},
                    {"name":"REPAYING","text":"还款中的借款","link":"/loanList/console?status=REPAYING"},
                    {"name":"COMPLETE","text":"完成还款的借款","link":"/loanList/console?status=COMPLETE"},
                    {"name":"CANCEL","text":"已经流标的借款","link":"/loanList/console?status=CANCEL"},
                    {"name":"OVERDUE","text":"逾期的借款","link":"/loanList/console?status=OVERDUE"},
                    {"name":"repaymentInfoList","text":"项目还款明细表","link":"/loan-repay"}
                ]
            },
            {
                "name":"userMain",
                "header":{"text":"用户管理","link":"/users"},
                "sidebar":[
                    {"name":"addUser","text":"添加用户","link":"/user/create"},
                    {"name":"userMan","text":"用户管理","link":"/users"},
                    {"name":"referMan","text":"推荐人管理","link":"/referrerManage"}
                ]
            },
            {
                "name":"finaMan",
                "header":{"text":"财务管理","link":"/invests"},
                "sidebar":[
                    {"name":"userInvest","text":"用户投资管理","link":"/invests"},
                    {"name":"debtRepay","text":"债权还款计划","link":"/debt-repayment-plan"},
                    {"name":"recharge","text":"充值记录","link":"/recharge"},
                    {"name":"withdraw","text":"提现记录","link":"/withdraw"},
                    {"name":"userFund","text":"用户资金查询","link":"/user-funds"},
                    {"name":"systemBill","text":"系统账户查询","link":"/system-bill"},
                    {"name":"adminIntervention","text":"管理员修改账户余额","link":"/admin-intervention"},
                    {"name":"realTimeStatus","text":"联动优势余额查询","link":"/real-time-status"}
                ]
            },
            {
                "name":"announceMan",
                "header":{"text":"公告管理","link":"/announce"},
                "sidebar":[
                    {"name":"announceMan","text":"公告管理","link":"/announce"}
                ]
            },
            {
                "name":"security",
                "header":{"text":"安全管理","link":"/security-log/login-log"},
                "sidebar":[
                    {"name":"loginLog","text":"登录日志","link":"/security-log/login-log"},
                    {"name":"auditLog","text":"管理日志","link":"/security-log/audit-log"}
                ]
            }
        ]
>

<#macro main pageCss pageJavascript headLab="" sideLab="" title="拓天速贷">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>${title}</title>
    <!-- link bootstrap css -->
    <link href="${requestContext.getContextPath()}/style/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${requestContext.getContextPath()}/style/libs/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="${requestContext.getContextPath()}/style/libs/bootstrap-datepicker.css" rel="stylesheet">
    <link href="${requestContext.getContextPath()}/style/libs/bootstrap/bootstrap-datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet">
    <link href="${requestContext.getContextPath()}/style/libs/bootstrap-select.css" rel="stylesheet" type="text/css" charset="utf-8"/>
    <link href="${requestContext.getContextPath()}/style/libs/jquery-ui/jquery-ui-1.11.4.min.css" rel="stylesheet" charset="utf-8"/>
    <link href="${requestContext.getContextPath()}/style/index.css" rel="stylesheet">
    <link href="${requestContext.getContextPath()}/style/libs/fileinput.css" rel="stylesheet"/><!--上传图片插件-->
    <#if pageCss?? && pageCss != "">
        <link rel="stylesheet" type="text/css" href="${requestContext.getContextPath()}/style/dest/${pageCss}" charset="utf-8"/>
    </#if>

    <!-- link bootstrap js -->
    <#if pageJavascript?? && pageJavascript != "">
        <script src="${requestContext.getContextPath()}/js/libs/config.js"></script>
        <script src="${requestContext.getContextPath()}/js/libs/require-2.1.20.min.js" defer="defer" async="async"
                data-main="${requestContext.getContextPath()}/js/${pageJavascript}"></script>
    </#if>
</head>

<body>
<!-- header begin -->
<header class="navbar navbar-static-top bs-docs-nav" id="top" role="banner">
    <div class="container-fluid">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target="#bs-navbar"
                    aria-controls="bs-navbar" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="${requestContext.getContextPath()}" class="navbar-brand"><img
                    src="${requestContext.getContextPath()}/images/logo.jpg" alt=""></a>
        </div>
    </div>
    <nav id="bs-navbar" class="collapse navbar-collapse">
        <div class="container-fluid">
            <ul class="nav navbar-nav">
                <#list menus as menu>
                    <li <#if menu.name == headLab>class="active"</#if>>
                        <a href="${menu.header.link}">${menu.header.text}</a>
                    </li>
                </#list>
            </ul>
        </div>
    </nav>
</header>
<!-- header end -->

<!-- main begin -->
<div class="main">
    <div class="container-fluid">
        <div class="row">

            <!-- menu sidebar -->
            <div class="col-md-2">
                <ul class="nav bs-docs-sidenav">
                    <#list menus as menu>
                        <#if menu.name=headLab>
                            <#list menu.sidebar as item>
                                <li <#if item.name == sideLab>class="active"</#if>><a
                                        href="${item.link}">${item.text}</a></li>
                            </#list>
                        </#if>
                    </#list>
                </ul>
            </div>
            <!-- menu sidebar end -->

            <!-- content area begin -->
            <#nested>
            <!-- content area end -->
        </div>
    </div>
</div>
<!-- main end -->

</body>
</html>
</#macro>
