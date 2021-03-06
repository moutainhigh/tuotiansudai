<#import "macro/global.ftl" as global>
<@global.main pageCss="${css.transfer_detail}" pageJavascript="${js.transfer_detail}" activeNav="我要出借" activeLeftNav="转让项目" title="标的详情">
<style type="text/css">
.swiper-container {
    display: none;
}
</style>
<div class="transfer-detail-content" id="transferDetailCon" data-estimate="${estimate???string('true', 'false')}" data-user-role="<@global.role hasRole="'INVESTOR'">INVESTOR</@global.role>"
     data-estimate-type="${(estimate.type)!''}" data-estimate-level="${(estimate.lower)!''}"  data-available-invest-money="${availableInvestMoney?c}"
     data-estimate-limit="${estimateLimit?c}" data-loan-estimate-level="${loanDto.estimateLevel!''}"
     data-price="${transferApplication.transferAmount!}"
>
    <div class="detail-intro">
        <div class="transfer-top">
            <span class="product-name">${transferApplication.name!}</span>
            <span class="product-type">原始项目：<a href="/loan/${transferApplication.loanId?string.computer}" target="_blank">${transferApplication.loanName!}</a></span>
            <div class="tips-wrap">
    <#if loanDto.estimate??>
            <span id="riskTips" class="risk-tips">${loanDto.estimate}<em></em><i class="risk-tip-content extra-rate-popup">该项目适合出借偏好类型为${loanDto.estimate}的用户</i></span>
    </#if>
            <span class="product-tip">拓天速贷提醒您：市场有风险，出借需谨慎！</span>
            </div>
        </div>
        <div class="transfer-info">
            <div class="transfer-info-dl">
                <dl>
                    <dt>转让价格</dt>
                    <dd><em><@percentInteger>${transferApplication.transferAmount!}</@percentInteger></em>
                        <i><@percentFraction>${transferApplication.transferAmount!}</@percentFraction></i>元
                    </dd>
                </dl>
                <dl>
                    <dt>项目本金</dt>
                    <dd><em><@percentInteger>${transferApplication.investAmount!}</@percentInteger></em>
                        <i><@percentFraction>${transferApplication.investAmount!}</@percentFraction></i>元
                    </dd>
                </dl>
                <dl>
                    <dt>约定年化利率</dt>
                    <dd><em>${transferApplication.baseRate!}
                        <#if loanDto.activityRate != '0.0'>
                            <i class="data-extra-rate">+ ${100 * loanDto.activityRate?number}</i>
                        </#if>
                        %
                    </em></dd>
                </dl>
                <dl>
                    <dt>剩余天数</dt>
                    <dd><em>${transferApplication.leftDays!}</em></dd>
                </dl>
            </div>
            <div class="info-detail">
                <ul class="detail-list">
                    <li>
                        <span>项目到期时间：${transferApplication.dueDate?string("yyyy-MM-dd")}</span>
                    </li>
                    <li>
                        <span>下次回款：${transferApplication.nextRefundDate?string("yyyy-MM-dd")}/${transferApplication.nextExpecedInterest!}元</span>
                    </li>
                    <li>
                        <span>出让人：${transferApplication.transferrer}</span>
                    </li>
                    <li>
                        <span>距下架时间：${transferApplication.beforeDeadLine}</span>
                    </li>
                    <li>
                        <span><a href="${commonStaticServer}/images/pdf/transferAgreement-sample.pdf" target="_blank">债权转让协议书(范本)</a></span>
                    </li>
                </ul>
            </div>
        </div>
        <div class="transfer-operat">
            <#if (transferApplication.transferStatus.name() == "SUCCESS")>
                <span class="img-status transfered"></span>
                <p class="status-text">转让完成时间：${transferApplication.transferTime?string("yyyy-MM-dd HH:mm:ss")}</p>
            <#elseif (transferApplication.transferStatus.name() == "CANCEL")>
                <span class="img-status transfercancel"></span>
                <p class="status-text"></p>
            <#else>
                <form action="/transfer/purchase" method="post" id="transferForm" class="clearfix">
                    <p class="get-money">
                        <span class="name-text" id="tipLayer">认购金额：</span>
                        <span class="money-text">
                            <strong>${transferApplication.transferAmount!}</strong>元
                        </span>
                        <#if errorMessage?has_content>
                            <span class="errorTip hide"><i class="fa fa-times-circle"></i>${errorMessage!}</span>
                        </#if>
                    </p>
                    <p><span class="name-text">预计收益：</span><span class="money-text"><strong>${transferApplication.expecedInterest!}</strong>元</span></p>
                    <p class="user-money"><span class="name-text">账户余额：${transferApplication.balance!} 元</span><span class="money-text"><strong><a href="/recharge">去充值 >></a></strong></span></p>
                    <input type="hidden" id="amount" name="amount" value="${transferApplication.transferAmount}"/>
                    <input type="hidden" id="userBalance" name="userBalance" value="${transferApplication.balance!}"/>
                    <input type="hidden" id="loanId" name="loanId" value="${transferApplication.loanId?string.computer}"/>
                    <input type="hidden" id="transferApplicationId" name="transferApplicationId" value="${transferApplication.id?string.computer}"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <p><button id="transferSubmit" class="btn-pay btn-normal" type="button">马上出借</button></p>
                    <input type="hidden" value="${anxinAuthenticationRequired?c}" id="isAnxinAuthenticationRequired">
                    <input type="hidden" value="${anxinUser?c}" id="isAnxinUser">
                    <@global.role hasRole="'INVESTOR'">
                    <#if anxinUser != true>
                    <p class="skip-group">
                        <span class="init-checkbox-style on">
                             <input type="checkbox" id="skipCheck" class="default-checkbox" checked>
                         </span>

                        <label class="skip-text">
                            我已阅读并同意<a href="javascript:void(0)"><span class="anxin_layer link-agree-service">《安心签平台服务协议》</span>、<span class="anxin_layer link-agree-privacy">《隐私条款》</span>、<span class="anxin_layer link-agree-number">《CFCA数字证书服务协议》</span>和<span class="anxin_layer link-agree-number-authorize">《CFCA数字证书授权协议》</span><span class="check-tip" id="checkTip">请勾选</span></a>
                        </label>
                    </p>
                    </#if>
                    </@global.role>
                </form>
            </#if>
            <div class="clearfix" style="width: 290px;">
                <div style="color:#949494;">拓天速贷提醒您：市场有风险，出借需谨慎！</div>
                <div style="padding-bottom: 10px;">点击查看<a style="color: #ff7200;" href="${commonStaticServer}/images/pdf/risk-disclosure.pdf" target="_blank">《风险揭示书》</a></div>
            </div>
        </div>


    </div>
    <div class="detail-record">
        <div class="transfer-top">
            <ul>
                <li class="active">项目详情</li>
                <li>转让记录</li>
            </ul>
        </div>

        <div class="detail-record-info">
            <div class="record-title"><span>原始项目信息</span></div>
            <div class="old-project">
                <span>约定年化利率：${100 * (loanDto.basicRate?number + loanDto.activityRate?number)}%</span>
                <span>项目期限：${30 * loanDto.periods}天（${loanDto.periods}期）</span>
                <span>项目总额：${loanDto.loanAmount}元</span>
                <span>还款方式：${loanDto.type.getRepayType()}</span>
            </div>

            <div class="record-title"><span>债权转让介绍</span></div>
            <p>债权转让服务是指平台用户将自己所持有的债权转让给平台其他用户，由受让该债权的用户享有该债权在剩余存续期间的收益。债权转让为一对一转让。</p>
            <div class="record-title"><span>常见问题</span></div>

            <div class="question-list">
                <dl>
                    <dt>1. 转让项目的优势？<i class="fa fa-chevron-circle-down fr"></i> </dt>
                    <dd>转让债权和原始债权的约定年化利率、还款方式（按月付息，到期还本）是一样的。与普通债权相比，购买转让债权时间短，可以更快的收回出借。同时还可享受到出让人的价格折让。</dd>
                </dl>

                <dl>
                    <dt>2. 债权转让的收益怎么计算？
                        <i class="fa fa-chevron-circle-down fr"></i></dt>
                    <dd>债权转让项目按照原债权的约定年化利率计算收益，转让达成后，该债权当期及之后各期的收益均归债权受让人所有。 <br/>
                        例如：一笔3期的债权，4月1号完成了一次回款，下次回款在5月1号，最后一次回款在5月31号，该笔债权在4月10号成功转让，则受让人将获得2期60天（4月2号至5月31号）的收益。</dd>
                </dl>

                <dl>
                    <dt>3. 债权转让的时效为多久？
                        <i class="fa fa-chevron-circle-down fr"></i></dt>
                    <dd>债权转让的时效为转让当日剩余时间另加5个自然日内；<br/>
                        债权转让的周期为5天，未成功转让，平台将自动撤销该转让申请；<br/>
                        转让债权如在5天内有回款，则该笔资金无法申请转让；<br/>
                        申请债权转让，如当日取消转让，则该笔资金当日不可再申请转让，需等到次日。

                    </dd>
                </dl>

                <dl>
                    <dt>4. 购买的债权可再次转让吗？
                        <i class="fa fa-chevron-circle-down fr"></i></dt>
                    <dd>目前，债权只可转让一次，您接手债权后不可再进行转让。</dd>
                </dl>

                <dl>
                    <dt>5. 债权转让是否收取服务费？
                        <i class="fa fa-chevron-circle-down fr"></i></dt>
                    <dd>转让达成后，平台会向债权出让人收取一定数量的转让服务费，此过程不对债权受让人收取服务费用。<br/>
                        手续费收取标准：<br/>
                        持有30天以内，扣除本金的1%；<br/>
                        持有30-90天以内，扣除本金的0.5%；<br/>
                        持有90天以上转让的，不扣除手续费。
                    </dd>
                </dl>

                <dl>
                    <dt>6. 一般多久可以转让成功？
                        <i class="fa fa-chevron-circle-down fr"></i>
                    </dt>
                    <dd>债权转让速度取决于剩余期限、转让金额、折让收益等多方面因素，受欢迎的转让项目往往出现发布即秒的情况。</dd>
                </dl>
            </div>
        </div>
        <div class="detail-record-info" style="display: none">
            <div class="transfer-table">
                <#if (transferApplicationReceiver.status?string) == "true">
                    <table>
                        <thead>
                        <tr>

                            <th>受让人</th>
                            <th>转让价格(元)</th>
                            <th>交易方式</th>
                            <th>预期收益(元)</th>
                            <th>项目本金(元)</th>
                            <th>交易时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${transferApplicationReceiver.transferApplicationReceiver!}</td>
                            <td>${transferApplicationReceiver.receiveAmount!}</td>
                            <td>
                                <#if transferApplicationReceiver.source == "WEB"><i class="fa fa-internet-explorer" aria-hidden="true"></i>
                                <#elseif transferApplicationReceiver.source == "ANDROID"><i class="fa fa-android" aria-hidden="true">
                                <#elseif transferApplicationReceiver.source == "IOS"><i class="fa fa-apple" aria-hidden="true"></i>
                                <#elseif transferApplicationReceiver.source == "AUTO">自动
                                <#else>
                                </#if>
                            </td>
                            <td>${transferApplicationReceiver.expecedInterest!}</td>
                            <td>${transferApplicationReceiver.investAmount!}</td>
                            <td>${transferApplicationReceiver.transferTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                        </tr>
                        </tbody>
                    </table>
                <#else >
                    <p class="tc text-b">暂无承接记录</p>
                </#if>
            </div>
         </div>

    </div>
    <#include "component/anxin-qian.ftl" />
    <#include "component/anxin-agreement.ftl" />
    <#include "component/coupon-alert.ftl" />
</div>
<#--风险测评-->
<div id="riskAssessment" class="pad-m popLayer" style="display: none; padding-top:50px;padding-bottom: 0">
    <div class="tc text-m">根据监管要求，出借人在出借前需进行出借偏好评估，如果取消将不能参与出借，您是否进行评估？</div>
    <div class="tc person-info-btn" style="margin-top:40px;">
        <button class="btn  btn-cancel btn-close btn-close-turn-on cancelAssessment" type="button">取消</button>&nbsp;&nbsp;&nbsp;
        <button class="btn btn-success btn-turn-off confirmAssessment" type="button">确认</button>
        <p style="margin-top: 20px;color: #A2A2A2">拓天速贷提醒您：市场有风险，出借需谨慎！</p>
    </div>
</div>

<#--风险等级超出提示-->
<div id="riskGradeForm" class="pad-m popLayer" style="display: none; padding-top:50px;padding-bottom: 0">
    <div class="tc text-m">您当前的风险等级为${(estimate.type)!''}，此项目已超<br/>出您的风险等级，是否重新评测？</div>

    <div class="tc person-info-btn" style="margin-top:40px;">
        <button class="btn  btn-cancel btn-close btn-close-turn-on cancelAssessment" type="button">取消</button>&nbsp;&nbsp;&nbsp;
        <button class="btn btn-success btn-turn-off confirmAssessment" type="button">重新测评</button>
    </div>
</div>

<#--最多借出额度提示-->

<div id="riskBeyondForm"  class="pad-m popLayer" style="display: none; padding-top:50px;padding-bottom: 0">
    <div class="tc text-m">您当前的风险等级为${(estimate.type)!''}，最多出借金<br/>额为${(estimateLimit/100)?c}元，是否重新评测？</div>

    <div class="tc person-info-btn" style="margin-top:40px;">
        <button class="btn  btn-cancel btn-close btn-close-turn-on cancelAssessment" type="button">取消</button>&nbsp;&nbsp;&nbsp;
        <button class="btn btn-success btn-turn-off confirmAssessment" type="button">重新测评</button>
    </div>
</div>
<div id="riskTipForm"  class="pad-m popLayer" style="display: none; padding-top:50px;padding-bottom: 0">
    <div class="tc text-m">市场有风险，出借需谨慎！<br/>
        点击查看<a style="color: #ff7200" href="${commonStaticServer}/images/pdf/risk-disclosure.pdf" target="_blank">《风险揭示书》</a>
    </div>

    <div class="tc person-info-btn" style="margin-top:40px;">
        <button class="btn btn-success btn-turn-off confirmInvest" type="button">确定</button>
    </div>
</div>
    <#include "component/red-envelope-float.ftl" />
    <#include "component/login-tip.ftl" />
</@global.main>