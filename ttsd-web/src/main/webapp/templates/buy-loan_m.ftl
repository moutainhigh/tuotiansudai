
<#--<@global.main pageCss="${css.buy_loan}" pageJavascript="${js.buy_loan}" title="直投项目购买详情">-->

<div class="my-account-content apply-transfer show-page"  id="buyDetail" style="display: none" data-loan-status="${loan.loanStatus}" data-loan-progress="${loan.progress?string.computer}" data-loan-countdown="${loan.countdown?string.computer}"
     data-authentication="<@global.role hasRole="'USER'">USER</@global.role>" data-user-role="<@global.role hasRole="'INVESTOR'">INVESTOR</@global.role>">
    <input id="leftInvest" type="hidden" value="${loan.amountNeedRaised?string.computer}">
    <input type="hidden" id="minAmount" value="${interestPerTenThousands?string.computer}">
    <div class="benefit-box">
        <div class="target-category-box" data-url="loan-transfer-detail.ftl">
            <div class="newer-title">
                <span>${loan.name}</span>
                <span class="tip-text">剩余可投 : <@amount>${loan.amountNeedRaised?string.computer}</@amount>元</span>
            </div>
            <ul class="loan-info clearfix">
                <li>
                    <span>
                        <i>${loan.raisingDays}天</i>
                    </span>
                    <em>项目期限</em>
                </li>
                <li>
                    <span>
                        <i>${loan.baseRate}+${loan.activityRate}%</i>
                    </span>
                    <em>预期年化收益</em>
                </li>
                <li>
                    <span>
                        <i><@amount>${interestPerTenThousands?string.computer}</@amount></i>元
                    </span>
                    <em>起投金额</em>
                </li>
            </ul>
        </div>
        <div class="bg-square-box"></div>
    </div>
    <form id="investForm" action="/m/invest" method="post">
    <div class="input-amount-box">
        <ul class="input-list">
            <li>
                <label>投资金额</label>
                <input type="text" value="" name="price" class="input-amount" placeholder="50.00起投">
                <em>元</em>
            </li>
            <li class="mt-10">
                <label>预期收益</label>
                <span class="number-text"><strong>0</strong>元</span>
            </li>
            <li id="select_coupon" class="select-coupon" data-user-coupon-id="-1">
                <label>优惠券</label>
                <input id="couponText" type="text" value=""  placeholder="无可用优惠券" readonly="readonly">
                <input id="couponId" type="hidden" value="" name="userCouponIds">
                <em><i class="fa fa-angle-right"></i></em>
            </li>

        </ul>
    </div>

    <button type="submit" class="btn-wap-normal" disabled>立即投资</button>

    </form>


    <div class="transfer-notice">
        <div class="agreement-box">
            <span class="init-checkbox-style on">
                 <input type="checkbox" id="readOk" class="default-checkbox" checked>
             </span>
            <lable for="agreement">我已阅读并同意<a href="javascript:void(0)" class="link-agree-service">《安心签服务协议》</a>、<a href="javascript:void(0)" class="link-agree-privacy">《隐私条款》</a> 和<a href="javascript:void(0)" class="link-agree-number"> 《CFCA数字证书服务协议》</a></lable>
        </div>
        <a href="">查看《借款转让协议样本》</a>
    </div>


</div>

<#--</@global.main>-->
