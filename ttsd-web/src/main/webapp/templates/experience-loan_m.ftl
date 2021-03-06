<#import "macro/global_m.ftl" as global>
<@global.main pageCss="${m_css.experience_loan}" pageJavascript="${m_js.experience_loan}" activeNav="我要出借" activeLeftNav="" title="拓天速贷-互联网金融信息服务平台" >
<div class="my-account-content experience-amount" id="experienceAmount" style="display: none" data-estimate="${estimate?string('true', 'false')}">
    <div class="account-summary">
        <div style="height: 44px;line-height: 44px;font-size: 17px; width: 100%;text-align: center;">
            <a class="go-back-container" id="goBack_experienceAmount" href="javascript:history.go(-1)" style="top: 6px;">
                <span class="go-back"></span>
            </a>
            <span class="title">${loan.name}</span>
        </div>
        <div class="collection">
            <span class="risk-tip">该项目适合出借偏好类型为<i class="risk-type">保守型</i>的用户<em id="closeRisk"></em></span>
            <span class="summary-box">
                <b><@percentInteger>${loan.baseRate}</@percentInteger><@percentFraction>${loan.baseRate}</@percentFraction>
                    <i>%</i></b>
                <em>约定年化利率</em>
            </span>
        </div>

        <div class="amount-balance">
            仅限体验金购买 不支持债权转让
        </div>
    </div>

    <div class="experience-total">
        <span>
            <b><em><@amount>${loan.minInvestAmount?string.computer}</@amount></em>元</b><br/>
            <i>起投金额</i>
        </span>
        <span>
            <b>${loan.duration}天</b><br/>
            <i>项目期限</i>
        </span>
        <span>
            <b>0.00元</b><br/>
            <i>万元收益</i>
        </span>
    </div>

    <ul class="detail-list">
        <li>
            <label>计息方式</label>
            <span>按天计息，即投即生息</span>

        </li>
        <li>
            <label>还款方式</label>
            <span>
            体验金收回，收益归您
            </span>
        </li>
        <li>
            <label>发布日期</label>
            <span>${.now?string('yyyy-MM-dd')} 00:00:00</span>
        </li>
    </ul>
    <br/>
    <div class="invest-tips-m" style="text-align: center;color: #A2A2A2">市场有风险，出借需谨慎！</div>
    <button class="to-invest-project" type="button" id="investment_btn">立即出借</button>
</div>

<div class="my-account-content apply-transfer" id="applyTransfer" style="display: none">
    <div class="goBack_applyTransfer">
        购买详情
        <a class="go-back-container" id="goBack_applyTransfer" href="javascript:history.go(-1)">
            <span class="go-back"></span>
        </a>
    </div>
    <div class="benefit-box">
        <div class="target-category-box" data-url="">
            <div class="newer-title">
                <span class="line_icon"></span>
                <span>${loan.name}</span>
            </div>
            <ul class="loan-info clearfix">
                <li class="experience-total1">
                    <span>
                         <b>${loan.duration}天</b>
                         <i>项目期限</i>
                    </span>
                </li>
                <li class="experience-total1">
                    <span>
                        <b><@percentInteger>${loan.baseRate}</@percentInteger><@percentFraction>${loan.baseRate}</@percentFraction>
                            %</b>
                         <i>约定年化利率</i>
                    </span>
                </li>
                <li class="experience-total1">
                    <span>
                        <b>0.00元</b>
                        <i>万元收益</i>
                    </span>
                </li>
            </ul>
        </div>
        <div class="bg-square-box"></div>
    </div>
    <form id="investForm">
        <div class="input-amount-box">
            <ul class="input-list">
                <li class="investmentAmount">
                    <span style="display: none" id="my_experience_balance">${(experienceBalance / 100)}</span>
                    <label>出借金额</label>
                    <input id="experience_balance" type="text" data-start_investment="${loan.minInvestAmount / 100}"
                           data-experience_balance="${(experienceBalance / 100)}"
                           value="${(experienceBalance / 100)?string("0.00")}" name="price" class=""
                           placeholder=${(loan.minInvestAmount / 100)?string("0.00")}元起投>
                    <span class="close_btn" id="close_btn"></span>
                    <em>元</em>
                </li>
                <li class="mt-10">
                    <label style="font-family: PingFangSC-Light;">预期收益</label>
                    <span class="number-text"><strong id="expect-amount">0.00</strong>元</span>
                </li>
            </ul>
        </div>
        <div class="invest-tips-m exper_buy" style="text-align: center;color: #A2A2A2">市场有风险，出借需谨慎！</div>

        <button type="submit" class="btn-wap-normal" id="submitBtn">立即体验</button>
        <div class="shade_mine" style="display: none"></div>
    </form>
    <div class="transfer-notice" style="font-family: PingFangSC-Light;">

        <b>温馨提示:</b>
        用户首次提现体验金出借所产生的收益时，需要出借其他定期项目（债权转让项目除外）累计满1000元才可以提现。
    </div>
    <div id="freeSuccess" style="display: none">
        <div class="success-info-tip">
            <div class="pop_title">温馨提示</div>
            <div class="pop_content">体验金余额不足，<br/>快去参与活动赢取体验金吧！</div>
        </div>
    </div>
</div>
<div id="investmentSuc" style="display: none">
    <div class="goBack_applyTransfer">
        出借成功
    </div>
    <div class="my-account-content apply-transfer-success">
        <div class="info">
            <div class="icon-success-wrapper">
                <i class="icon-success"></i>
                <div class="investment-success-text">出借成功</div>
            </div>
            <ul class="input-list">
                <li class="item">
                    <label>出借金额</label>
                    <em id="investment_suc_amount"></em>
                </li>
                <li class="item">
                    <label>所投项目</label>
                    <em>${loan.name}</em>
                </li>
            </ul>
        </div>
    </div>
    <div class="button-note">
        <a href="/m/investor/invest-list" class="btn-wap-normal next-step">确定</a>
    </div>
</div>
</@global.main>
