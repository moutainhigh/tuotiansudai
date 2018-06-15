
<#import "macro/global.ftl" as global>
<@global.main pageCss="${css.invest_success}" pageJavascript="${js.account_success}" activeLeftNav="" title="业务失败">
<div class="head-banner"></div>
<div>
    <div class="callBack_container">
        <div class="success_tip_icon failure"></div>
        <p class="my_pay_tip">${message!('业务处理失败')}</p>
        <div class="handle_btn_container">
            <div class="retry" style="margin-right: 0;">再次尝试</div>
            <form id="retry-form" action="${bankCallbackType.getRetryPath()}" method="${bankCallbackType.getMethod}" style="display: inline-block;float:right">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
        <p class="phone-tip">客服电话：400-169-1188 （工作日 9:00-22:00）</p>
    </div>
</div>
</@global.main>