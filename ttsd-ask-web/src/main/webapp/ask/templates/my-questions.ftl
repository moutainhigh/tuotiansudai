<#import "macro/global.ftl" as global>
<@global.main pageCss="${(css.mainSite)!'mainSite.css'}" pageJavascript="${(js.mainSite)!'mainSite.js'}" title="拓天问答_投资问答_拓天速贷" keywords="投资问答,网贷问答,投资知识,金融问答" description="拓天速贷投资问答系统,为您解答金融行业最新最快的投资知识,让您放心投资、安全投资,拓天速贷为投资人答疑解惑.">
<div class="article-content fl" id="myQAnswer">
    <ul class="switch-menu clearfix" id="my-questions-tab">
        <li class="active"><a href="/question/my-questions">我的提问</a></li>
        <li><a href="/answer/my-answers">我的回答</a></li>
    </ul>
    <div class="border-ask-box clearfix">
        <div class="answers-box">
            <#list questions.data.records as question>
                <dl class="answers-list">
                    <dt><a href="/question/${question.id?string.computer}" target="_blank">${question.question}</a></dt>
                    <dd class="detail"><a href="/question/${question.id?string.computer}" target="_blank">${question.addition?replace('\\n','<br/>','i')?replace('\\r','<br/>','i')}</a></dd>
                    <dd><span>${question.mobile}</span>
                        <span class="answerNum">回答：${question.answers}</span>
                        <span class="datetime">${question.createdTime?string("yyyy年MM月dd日 HH:mm")}</span>

                            <#list question.tags as tag>
                            <em class="fr tag">
                                <a ref="/question/category/${tag.name()}">${tag.description}</a>
                             </em>
                            </#list>
                    </dd>
                </dl>
            </#list>
        </div>
    </div>

    <div class="pagination">
        <#if questions.data.hasPreviousPage>
            <a href="/question/my-questions">首页</a>
        </#if>
        <#if questions.data.index &gt; 3>
            <a href="/question/my-questions?index=${questions.data.index-1}"> < </a>
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
            <a href="/question/my-questions?index=${page}" <#if page == questions.data.index>class="active"</#if>> ${page} </a>
        </#list>

        <#if questions.data.maxPage - questions.data.index &gt; 2>
            <a href="/question/my-questions?index=${questions.data.index+1}"> > </a>
        </#if>
        <#if questions.data.hasNextPage>
            <a href="/question/my-questions?index=${questions.data.maxPage}">末页</a>
        </#if>
    </div>
</div>


</@global.main>