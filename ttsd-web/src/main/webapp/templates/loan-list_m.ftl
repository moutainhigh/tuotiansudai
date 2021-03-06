<#import "macro/global_m.ftl" as global>

<@global.main pageCss="${m_css.loan_list}" pageJavascript="${m_js.loan_list}" title="出借列表_出借产品_拓天速贷">

<div class="my-loan-content" id="loanList">
    <div class="menu-category">
        <span class="current"><a id="aaa">直投项目</a></span>
        <span><a href="/m/transfer-list">转让项目</a></span>
        <em class="notice-tip-btn"></em>
    </div>

    <div id="wrapperOut"  class="loan-list-frame">
        <div class="loan-list-content">
            <div class="category-box-main">
                <#assign isInserted = false>
                <#list loanItemList as loanItem>
                    <#if transferringCount?? && transferringCount != 0 && !isInserted>
                        <#if !(['PREHEAT', 'RAISING']?seq_contains(loanItem.status))>
                            <#assign isInserted = true>
                            <div class="target-category-box" data-url="/m/transfer-list">
                                <b class="newer-title transferAreaTitle">转让专区<i class="icon-sign">期限短 收益高</i></b>
                                <ul class="transferArea loan-info clearfix">
                                    <li><span class="max-benifit">最高<i><@percentInteger>${maxTransferringRate}</@percentInteger><@percentFraction>${maxTransferringRate}</@percentFraction></i>%</span><em class="note">约定年化利率</em></li>
                                    <li style="position: relative"><em class="duration-day">${transferringCount}</em> 个 <em class="note">可投项目</em><i class="icon-first-get">先到先得</i></li>
                                    <li><a href="/m/transfer-list" class="btn-invest btn-normal">马上抢标</a></li>
                                </ul>
                            </div>
                        </#if>
                    </#if>

                    <div class="target-category-box <#if loanItem.productType == 'EXPERIENCE'>newer-experience</#if>"
                         data-url="/m/loan/${loanItem.id?c}">

                        <b class="newer-title">${loanItem.name}
                        <#if loanItem.productType == 'EXPERIENCE'>
                            <i class="icon-sign <#if ['RECHECK', 'REPAYING', 'OVERDUE', 'COMPLETE']?seq_contains(loanItem.status)>sold</#if>">体验金出借</i>
                        </#if>
                        <#if loanItem.productType != 'EXPERIENCE' && loanItem.activityType == 'NEWBIE'>
                            <i class="icon-sign <#if ['RECHECK', 'REPAYING', 'OVERDUE', 'COMPLETE']?seq_contains(loanItem.status)>sold</#if>">新手专享</i>
                        </#if>
                        <#if loanItem.activity?string("true","false") == "true">
                            <i class="icon-sign <#if ['RECHECK', 'REPAYING', 'OVERDUE', 'COMPLETE']?seq_contains(loanItem.status)>sold</#if>">${loanItem.activityDesc!}</i>
                        </#if>
                        </b>
                        <ul class="loan-info clearfix">
                            <li>
                        <span class="percent-number  <#if ['RECHECK', 'REPAYING', 'OVERDUE', 'COMPLETE']?seq_contains(loanItem.status)>colorChange</#if> ">
                            <i>
                                <#if loanItem.activityType == 'NEWBIE' && loanItem.interestCouponRate gt 0>
                                    <@percentInteger>${loanItem.baseRate+loanItem.activityRate+loanItem.interestCouponRate}</@percentInteger><@percentFraction>${loanItem.baseRate+loanItem.activityRate+loanItem.interestCouponRate}</@percentFraction>
                                <#else>
                                    <@percentInteger>${loanItem.baseRate+loanItem.activityRate}</@percentInteger><@percentFraction>${loanItem.baseRate+loanItem.activityRate}</@percentFraction><#if (loanItem.extraRate > 0)>~<@percentInteger>${loanItem.baseRate + loanItem.activityRate+ loanItem.extraRate}</@percentInteger><@percentFraction>${loanItem.baseRate + loanItem.activityRate+ loanItem.extraRate}</@percentFraction>
                                    </#if>
                                </#if>
                            </i><em>%</em>
                        </span>
                                <em class="note">约定年化利率</em>
                            </li>
                            <li>
                            <#if loanItem.productType != 'EXPERIENCE'>最长 </#if><em
                                    class="duration-day">${loanItem.duration}</em> 天
                                <em class="note">项目期限</em>
                            </li>
                            <li>
                            <#if ['RAISING']?seq_contains(loanItem.status)>
                                <a class="btn-invest btn-normal <#if loanItem.productType != 'EXPERIENCE'>goToDetail</#if> <#if loanItem.productType == 'EXPERIENCE'>goToExDetail</#if>" data-url="/m/loan/${loanItem.id?c}">立即出借</a>
                            <#elseif ['PREHEAT']?seq_contains(loanItem.status)>
                                <a class="btn-invest btn-normal preheat-status preheat-btn" data-url="/m/loan/${loanItem.id?c}" >预热中</a>
                            <#else>
                                <i class="loan-status icon-sellout"></i>
                            </#if>
                            </li>
                        </ul>
                    <#if loanItem.productType != 'EXPERIENCE'>
                        <div class="table-row progress-column">
                        <#if loanItem.status != 'PREHEAT'>
                            <div class="progress-bar">
                                <div class="process-percent">
                                    <div class="percent <#if ['RECHECK', 'REPAYING', 'OVERDUE', 'COMPLETE']?seq_contains(loanItem.status)>colorChange</#if>" style="width:${loanItem.progress}%">
                                    </div>
                                </div>
                            </div>
                        </#if>

                            <#if ['PREHEAT', 'RAISING']?seq_contains(loanItem.status)>
                                <#if loanItem.status == 'PREHEAT'>
                                <div class="progress-bar">
                                    <#if loanItem.preheatSeconds lte 1800>
                                    <span class="preheat" data-time="${loanItem.preheatSeconds?string.computer}" style="color: #FF473C">
                                        <i class="minute_show"></i>分
                                        <i class="second_show"></i>秒后开标
                                        </span>
                                    <#else>
                                        <span class="time">${(loanItem.fundraisingStartTime?string("yyyy-MM-dd HH时mm分"))!}
                                            开标</span>
                                    </#if>
                                </div>
                                </#if>
                                <#if loanItem.status == 'PREHEAT'>
                                <span class="p-title">项目总额：<span class="money"><@amount>${loanItem.alertAmount?c}</@amount></span>元</span>
                                </#if>
                                <#if loanItem.status != 'PREHEAT'>
                                    <span class="p-title">剩余金额：<span class="money"><@amount>${loanItem.alertAmount?c}</@amount></span>元</span>
                                </#if>
                            <#else>
                                <span class="p-title allReady"><i>剩余金额：<em class="money"><@amount>${loanItem.alertAmount?c}</@amount></em>元</i></span>
                            </#if>
                        </div>
                    </#if>
                    </div>
                </#list>
                <#if transferringCount?? && transferringCount != 0 && !isInserted>
                    <#assign isInserted = true>
                    <div class="target-category-box" data-url="/m/transfer-list">
                        <b class="newer-title transferAreaTitle">转让专区<i class="icon-sign">期限短 收益高</i></b>
                        <ul class="transferArea loan-info clearfix">
                            <li><span class="max-benifit">最高<i><@percentInteger>${maxTransferringRate}</@percentInteger><@percentFraction>${maxTransferringRate}</@percentFraction></i>%</span><em class="note">约定年化利率</em></li>
                            <li style="position: relative"><em class="duration-day">${transferringCount}</em> 个 <em class="note">可投项目</em><i class="icon-first-get">先到先得</i></li>
                            <li><a href="/m/transfer-list" class="btn-invest btn-normal">马上抢标</a></li>
                        </ul>
                    </div>
                </#if>
            </div>

            <div id="pullUp">
                <span class="pullUpLabel">上拉加载更多</span>
            </div>


        </div>


    </div>

</div>
<div class="loan-list">
    <div class="footer-wap-container">
        <a class="menu-home" href="/m">
            <i></i>
            <span>首页</span>
        </a>
        <a class="menu-invest current" href="/m/loan-list">
            <i></i>
            <span>出借</span>
        </a>
        <a class="menu-knowledge" href="/m/about/knowledge">
            <i></i>
            <span>发现</span>
        </a>
        <a class="menu-my" >
            <i></i>
            <span>我的</span>
        </a>
    </div>
</div>
</@global.main>
