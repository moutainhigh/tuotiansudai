<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign applicationContext=requestContext.getContextPath() />

<#macro role hasRole>
    <@security.authorize access="hasAnyAuthority(${hasRole})">
        <#nested>
    </@security.authorize>
</#macro>

<#macro isAnonymous>
    <@security.authorize access="!isAuthenticated()">
        <#nested>
    </@security.authorize>
</#macro>

<#macro isNotAnonymous>
    <@security.authorize access="isAuthenticated()">
        <#nested>
    </@security.authorize>
</#macro>

<#macro main pageCss pageJavascript="" activeNav="" activeLeftNav="" title="拓天速贷" keywords="" description="" site='main'>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="renderer" content="webkit">
    <title>${title}</title>
    <meta name="keywords" content="${keywords}">
    <meta name="description" content="${description}">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no, email=no"/>
    <meta name="_csrf" content="${(_csrf.token)!}"/>
    <meta name="_csrf_header" content="${(_csrf.headerName)!}"/>
    <meta name="360-site-verification" content="a3066008a453e5dfcd9f3e288862c9ef"/>
    <meta name="sogou_site_verification" content="lXIPItRbXy"/>
    <meta name="baidu-site-verification" content="xE3BgFFio5" />
    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-115616275-1"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());

        gtag('config', 'UA-115616275-1');
    </script>
    <link href="/favicon.ico" id="icoFavicon" rel="shortcut icon" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="${(css.globalFun_page)!}" charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${m_css.wap_global}" charset="utf-8"/>
    <#if pageCss?? && pageCss != "">
        <link rel="stylesheet" type="text/css" href="${pageCss}" charset="utf-8"/>
    </#if>
    <#include "../pageLayout/baidu-webmaster.ftl"/>
</head>
<body>

<div class="main-frame-wap" id="mainFrameWap">
    <#nested>
</div>

<div class="footer-wap-container">
    <a class="menu-home current" href="/m">
        <i></i>
        <span>首页</span>
    </a>
    <a class="menu-invest" href="/m/loan-list">
        <i></i>
        <span>出借</span>
    </a>
    <a class="menu-knowledge" href="/m/about/knowledge">
        <i></i>
        <span>发现</span>
    </a>
    <a class="menu-my">
        <i></i>
        <span>我的</span>
    </a>
</div>

<script>
    window.commonStaticServer = '${commonStaticServer}';
</script>

<#if (js.jquerydll)??>
<script src="${js.jquerydll}" type="text/javascript"></script>
</#if>

<#if (js.globalFun_page)??>
<script src="${js.globalFun_page!}" type="text/javascript"></script>
</#if>

<#if (js.wap_global)??>
<script src="${js.wap_global!}" type="text/javascript"></script>
</#if>

<#if pageJavascript??>
<script src="${pageJavascript}" type="text/javascript" id="currentScript"></script>
</#if>

</body>
</html>
</#macro>
