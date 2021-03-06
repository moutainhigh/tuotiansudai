<#import "macro/global_m.ftl" as global>

<@global.main pageCss="${m_css.loan_detail}" pageJavascript="${m_js.loan_detail}" title="项目详情－转让详情">
<input id="whichPage" type="hidden" data-page="transfer">
<div class="my-account-content experience-amount" id="transferingDetail" style="display: none">
    <div class="m-header"><em id="iconTransferM" class="icon-left"><i></i></em>${transferApplication.name!}
        </span> </div>



    <div class="account-summary">
        <div class="collection">
    <#if loanDto.estimate??>
            <span class="risk-tip">该项目适合出借偏好类型为<i class="risk-type">${loanDto.estimate}</i>的用户<em id="closeRisk"></em></span>
    </#if>
            <span class="summary-box">
                 <b>
                    <@percentInteger>${transferApplication.baseRate+transferApplication.activityRate}</@percentInteger><@percentFraction>${transferApplication.baseRate+transferApplication.activityRate}</@percentFraction>
                     <#if extraLoanRates??>~<@percentInteger>${transferApplication.baseRate+transferApplication.activityRate+transferApplication.maxExtraLoanRate}</@percentInteger><@percentFraction>${transferApplication.baseRate+transferApplication.activityRate+transferApplication.maxExtraLoanRate}</@percentFraction></#if>
                     <i>%</i>
                </b>

                <em>约定年化利率</em>
            </span>
        </div>

        <div class="amount-balance transfer-hack">
            <span>剩余期限<br/>${transferApplication.leftDays!}天</span>
            <span>转让价格(元)<br/><em class="money">${transferApplication.transferAmount!}</em></span>
        </div>

    </div>

    <#if (transferApplication.transferStatus.name() == "TRANSFERRING")>
    <div class="leave-time">距项目下架时间：${transferApplication.beforeDeadLine}</div>
    <#else>
    <div class="leave-time">${transferApplication.beforeDeadLine}</div>
    </#if>

    <ul class="detail-list">
        <li>
            <label>项目本金</label>
            <span><em class="money">${transferApplication.investAmount!}</em>元</span>

        </li>
        <li>
            <label>预期收益</label>
            <span>
               <em class="money">${transferApplication.expecedInterest!}</em>元
            </span>
        </li>
        <li>
            <label>项目到期时间</label>
            <span>${transferApplication.dueDate?string("yyyy-MM-dd")}</span>
        </li>
        <li class="related-expenses" data-expenses="${investFeeRate*100}">
            <label>相关费用</label>
            <span>${investFeeRate*100}%技术服务费<em class="icon-mark" id="relatedTip"></em></span>
        </li>
        <#if (transferApplication.transferStatus.name() == "TRANSFERRING")>
        <li class="repay-plan" id="look_repay_plan">
            <label>回款计划</label>
            <span><i class="iconRight"></i> </span>
        </li>
        </#if>

        <#if (transferApplication.transferStatus.name() == "SUCCESS")>
        <li class="repay-plan" id="look_continue_record">
            <label>债权承接记录</label>
            <span><i class="iconRight"></i> </span>
        </li>
        </#if>
    </ul>

    <div class="history-record">
        <a id="lookOld" data-url="/m/loan/${transferApplication.loanId?string.computer}">查看原始项目</a>
    </div>
    <div class="invest-tips-m" style="text-align: center;color: #A2A2A2">市场有风险，出借需谨慎！</div>

    <#if (transferApplication.transferStatus.name() == "SUCCESS")>
        <button class="to-invest-project" type="button" disabled>已转让</button>
    <#elseif (transferApplication.transferStatus.name() == "CANCEL")>
        <button class="to-invest-project" type="button" disabled>已取消</button>
    <#else>
        <button id="to_buy_transfer" class="to-invest-project" type="button">立即出借</button>
    </#if>

</div>
<#--回款计划-->
<div class="my-account-content amount-detail" id="repay_plan" style="display: none">
    <div class="m-header"><em id="iconReplay" class="icon-left"><i></i></em>回款计划 </div>
    <div class="amount-detail-inner">

        <dl class="payment-plan title">


                <dd class="font-left">回款时间</dd>
                <dd class="font-center">金额</dd>
                <dd class="font-right">还款状态</dd>
        </dl>

        <#list investRepay as repay>
            <dl class="payment-plan">
                <dd class="font-left">${repay.repayDate?string("yyyy-MM-dd")}</dd>
                <dd class="font-center"><@amount>${repay.expectedInterest?string.computer}</@amount></dd>

                <dd class="status font-right">${repay.statusDesc}</dd>
            </dl>
        </#list>
        </dl>
    </div>
</div>
<#--承接记录-->
<div id="continue_record" class="amount-detail-list" style="display: none">
    <div class="m-header"><em id="iconContinue" class="icon-left"><i></i></em>债权承接记录 </div>
    <#if (transferApplicationReceiver.status?string) == "true">
        <div class="box-item">
            <dl>
                <dt>${transferApplicationReceiver.transferApplicationReceiver!}</dt>
                <dd>${transferApplicationReceiver.transferTime?string("yyyy-MM-dd HH:mm:ss")}</dd>
            </dl>
            <span class="amount"><em class="money">${transferApplicationReceiver.receiveAmount!}</em>元</span>
        </div>
    <#else >
        <p class="tc text-b">暂无承接记录</p>
    </#if>
</div>
<#--转让购买详情-->
    <#include 'buy-transfer_m.ftl'>
</@global.main>

