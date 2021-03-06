<#import "../macro/global_m.ftl" as global>
    <@global.main pageCss="${m_css.loan_knowledge}" pageJavascript="${m_js.loan_knowledge}" activeNav="信息披露" activeLeftNav="网贷知识" title="网贷知识详情">
    <div class="knowledge-detail-container" id="knowledgeDetail">
        <#if isAppSource>
            <a class="back-link" href="/m/about/knowledge"></a>
            </#if>
        <h4 class="title">
        ${knowledge.title}
        </h4>
        <div class="article-origin clearfix">
            <span class="origin">来源：&nbsp;中新网</span>
            <span class="time">发表时间：${(knowledge.updatedTime?string("yyyy-MM-dd"))!} </span>
        </div>
        <#if knowledge.showPicture??>
            <img style="margin-top: 5px;" width="100%" src="${knowledge.showPicture}" alt="">
        </#if>
        <div class="detail-content-m">
        ${knowledge.content}
        </div>


    </div>

</@global.main>