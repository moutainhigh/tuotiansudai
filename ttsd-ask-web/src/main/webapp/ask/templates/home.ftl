<#import "macro/global.ftl" as global>
<@global.main pageCss="${(css.main)!'main.css'}" pageJavascript="${(js.main)!'main.js'}">
<div class="article-content fl" id="homeTagContainer">
    <ul class="switch-menu clearfix">
        <li><a href="/">全部问题</a></li>
        <li><a href="/?group=UNRESOLVED&index=1">待解决问题</a></li>
        <li><a href="/?group=HOT&index=1">热门问题</a></li>
    </ul>
    <div class="borderBox clearfix">
        <div class="answers-box">
            <#list questions.data.records as question>
                <dl class="answers-list">
                    <dt><a href="/question/${question.id?string.computer}" target="_blank">${question.question}</a></dt>
                    <dd class="detail"><a href="/question/${question.id?string.computer}" target="_blank">${question.addition}</a></dd>
                    <dd><span>${question.mobile}</span>
                        <span class="answerNum">回答：${question.answers}</span>
                        <span class="datetime">${question.createdTime?string("yyyy年MM月dd日 HH:mm")}</span>
                        <span class="fr tag">
                            <#list question.tags as tag>
                                <a href="/question/category?tag=${tag.name()}">${tag.description}</a>
                            </#list>
                        </span>
                    </dd>
                </dl>
            </#list>
        </div>
    </div>

    <div class="pagination">
        <#if questions.data.hasPreviousPage>
            <a href="/?group=${group}">首页</a>
        </#if>
        <#if questions.data.index &gt; 3>
            <a href="/?group=${group}&index=${questions.data.index-1}"> < </a>
        </#if>

        <#assign lower = 1>
        <#assign upper = questions.data.maxPage>
        <#if questions.data.maxPage &gt; 5>
            <#assign lower = questions.data.index>
            <#assign upper = questions.data.index>
            <#list 1..2 as index>
                <#if questions.data.index - index &gt; 0>
                    <#assign lower = lower - 1>
                <#else>
                    <#assign upper = upper + 1>
                </#if>
            </#list>
            <#list 1..2 as index>
                <#if questions.data.index + index <= questions.data.maxPage>
                    <#assign upper = upper + 1>
                <#else>
                    <#assign lower = lower - 1>
                </#if>
            </#list>
        </#if>

        <#list lower..upper as page>
            <a href="/?group=${group}&index=${page}" <#if page == questions.data.index>class="active"</#if>> ${page} </a>
        </#list>

        <#if questions.data.maxPage - questions.data.index &gt; 2>
            <a href="/?group=${group}&index=${questions.data.index+1}"> > </a>
        </#if>
        <#if questions.data.hasNextPage>
            <a href="/?group=${group}&index=${questions.data.maxPage}">末页</a>
        </#if>
    </div>
</div>
</@global.main>