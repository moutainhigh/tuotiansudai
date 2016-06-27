<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "macro/global.ftl" as global>
<@global.main pageCss="" pageJavascript="banner-list.js" headLab="announce-manage" sideLab="bannerMan" title="banner管理">

<!-- content area begin -->
<div class="col-md-10">

    <div class="table-responsive">
       <a href="/banner-manage/create"> 添加banner</a>
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th>当前顺序</th>
                <th>名称</th>
                <th>大图-WEB</th>
                <th>小图-APP</th>
                <th>链接</th>
                <th>属性</th>
                <th>终端</th>
                <th>上线时间</th>
                <th>下线时间</th>
                <th>是否在线</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                <#if bannerList??>
                    <#list bannerList as banner>
                        <tr>
                            <td>${banner.order!}</td>
                            <td>${banner.name!}</td>
                            <td><img src="http://localhost:9080/${banner.webImageUrl!}" width="100" height="30"/></td>
                            <td><img src="http://localhost:9080/${banner.appImageUrl!}" width="100" height="30"/></td>
                            <td><a href="${banner.url!}" target="_blank">${banner.url!}</td>
                            <td>${banner.authenticated?then('登录后可见','非登录可见')}</td>
                            <td>
                                <#list banner.source as source>${source.name()}<#sep>/</#list>
                            </td>
                            <td>${banner.activatedTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                            <td>
                                <#if banner.deactivatedTime??>
                                    ${banner.deactivatedTime?string('yyyy-MM-dd HH:mm:ss')}
                                <#else>
                                    --
                                </#if>
                            </td>
                            <td>${banner.active?then('是','否')}</td>
                            <td>
                                <@security.authorize access="hasAnyAuthority('OPERATOR_ADMIN','ADMIN')">
                                    <#if banner.active>
                                        <a href="/banner-manage/banner/edit/${banner.id?c}">编辑</a> <a href="/banner-manage/banner/deactivated/${banner.id?c}" onclick="return confirm('确定下线吗?')">下线</a>
                                    <#else>
                                        <a href="/banner-manage/banner/reuse/${banner.id?c}">复用</a> <a href="/banner-manage/banner/del/${banner.id?c}" onclick="return confirm('确定删除吗?')">删除</a>
                                    </#if>
                                </@security.authorize>
                            </td>
                        </tr>
                    </#list>
                </#if>
            </tbody>
        </table>
    </div>

    <!-- pagination  -->
    <nav class="pagination-control">
        <div>
            <span class="bordern">总共${count}条, 每页显示${pageSize}条</span>
        </div>
        <#if bannerList?has_content>
            <ul class="pagination pull-left">
                <li>
                    <#if hasPreviousPage >
                    <a href="?index=${index - 1}" aria-label="Previous">
                    <#else>
                    <a href="#" aria-label="Previous">
                    </#if>
                    <span aria-hidden="true">&laquo; Prev</span>
                </a>
                </li>
                <li><a>${index}</a></li>
                <li>
                    <#if hasNextPage>
                    <a href="?index=${index + 1}" aria-label="Next">
                    <#else>
                    <a href="#" aria-label="Next">
                    </#if>
                    <span aria-hidden="true">Next &raquo;</span>
                </a>
                </li>
            </ul>
        </#if>
    </nav>
    <!-- pagination -->
</div>
<!-- content area end -->
</@global.main>