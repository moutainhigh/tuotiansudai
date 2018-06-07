<#import "macro/global_m.ftl" as global>

<@global.main pageCss="${m_css.recharge}" pageJavascript="${m_js.recharge}" title="充值">

<div class="my-account-content bank-card-manage" id="cashMoneyConatiner">
    <div class="m-header"><em class="icon-left" id="goBackIcon"><i></i></em>充值</div>
    <#if isBindCard>
    <form action="/recharge" method="post" class="form-cash">
        <div class="int-item">
            <label for="name">充值金额</label>
            <input type="text" class="money-item" id="cashMoney" value="" placeholder="充值金额最少为1元"/>
        </div>
        <div class="user-money">
            <p>账户余额：<span>${balance}</span>元</p>
        </div>
        <input type="hidden" name="amount" value=""/>
        <input type="hidden" name="source" value="M"/>
        <input type="hidden" name="PayType" value="FAST_PAY"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" class="btn-wap-normal" id="toCash" disabled>确认提交</button>
    </form>
    <#else>
    <div class="manage-note">
        <a class="btn-app-link" href="https://tuotiansudai.com/app/download" target="_blank">下载APP</a>
        <p>手机端仅支持快捷支付</p>
        <p>请前往app开通快捷支付后再进行充值</p>
    </div>
    </#if>
</div>
</@global.main>
