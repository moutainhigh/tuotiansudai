<#import "macro/global.ftl" as global>
<@global.main pageCss="${css.personal_info}" pageJavascript="${js.personal_info}" activeNav="我的账户" activeLeftNav="个人资料" title="个人资料">
<div class="content-container auto-height personal-info" id="personInfoBox">
    <h4 class="column-title"><em class="tc">个人资料</em></h4>
    <ul class="info-list" >
        <li><span class="info-title"> 实名认证</span>
            <#if userName??>
                <em class="info">${userName}</em>
                <span class="binding-set"><i class="fa fa-check-circle ok"></i>已认证</span>
            <#else>
                <em class="info">为了保障您的账户及资金安全，我们会对您进行实名认证，认证后不可更改。</em>
                <span class="binding-set">
                    <i class="fa fa-times-circle no"></i>未认证<a class="setlink setEmail realName" href="/register/account">认证</a>
                </span>
            </#if>
        </li>
        <li><span class="info-title"> 手机</span>
            <em class="info">${mobile}</em>
            <span class="binding-set"><i class="fa fa-check-circle ok"></i>已绑定</span>
        </li>
        <li><span class="info-title"> 邮箱</span>
            <#if email?? && email != "">
                <em class="info">${email}</em>
                <span class="binding-set">
                    <i class="fa fa-check-circle ok"></i>已绑定<a class="setlink setEmail" href="javascript:">修改</a>
                </span>
            <#else>
                <em class="info">绑定邮箱后，您可及时了解交易情况及拓天速贷的最新动态</em>
                <span class="binding-set">
                    <i class="fa fa-times-circle no"></i>未绑定<a class="setlink setEmail" href="javascript:">绑定</a>
                </span>
            </#if>
        </li>
        <li><span class="info-title"> 绑定银行卡</span>
            <#if bankCard??>
                <em class="info">${bankCard?replace("^(\\d{4}).*(\\d{4})$","$1****$2","r")}</em>
                <span class="binding-set">
                    <i class="fa fa-check-circle ok"></i>已绑定<a class="setlink setBankCard" href="javascript:void(0)" id="update-bank-card" data-url="${requestContext.getContextPath()}/bind-card/replace">修改</a>
                </span>
            <#else>
                <em class="info">绑定银行卡后，您可以进行快捷支付和提现操作</em>
                <span class="binding-set">
                    <i class="fa fa-times-circle no"></i>未绑定<a class="setlink setBankCard" href="/bind-card">绑定</a>
                </span>
            </#if>
        </li>
        <li><span class="info-title"> 登录密码</span>
            <em class="info">********</em>
            <span class="binding-set">
               <i class="fa fa-check-circle ok"></i>已设置<a class="setlink setPass" href="javascript:void(0);">修改</a>
            </span>
        </li>
        <#if identityNumber??>
            <li><span class="info-title"> 支付密码</span>
                <em class="info">********</em>
                <span class="binding-set">
               <i class="fa fa-check-circle ok"></i>已设置<a class="setlink setUmpayPass" href="javascript:void(0);">重置</a>
            </span>
            </li>
        </#if>
        <li><span class="info-title"> 免密出借</span>
            <#if noPasswordInvest>
                <em class="info">您已开启免密出借，出借快人一步</em>
                <span class="binding-set">
                    <i class="fa fa-check-circle ok"></i>已开启<a class="setlink setTurnOffNoPasswordInvest" href="javascript:void(0);">关闭</a>
                </span>
            <#elseif autoInvest>
                <em class="info">您已授权自动投标，可直接开启免密出借，及时选择心仪标的，出借快人一步</em>
                <span class="binding-set">
                    <i class="fa fa-times-circle no"></i>未开启<a class="setlink setNoPasswordInvest"  href="javascript:void(0);">开启</a>
                </span>
            <#else>
                <em class="info">开启免密出借后，您可及时选择心仪标的，出借快人一步</em>
                <span class="binding-set">
                    <i class="fa fa-times-circle no"></i>未开启<a class="setlink setTurnOnNoPasswordInvest" href="javascript:void(0);">开启</a>
                </span>
            </#if>
        </li>
        <li>
            <span class="info-title">出借偏好</span>
            <#if estimate??>
                <em class="info">${estimate.summary}。出借限额为${estimateLimit}元</em>
                <span class="binding-set">
                    <i class="fa fa-check-circle ok"></i>已评估<a class="setlink" href="/risk-estimate">重置</a>
                </span>
            <#else>
                <em class="info">还未进行过出借偏好测评，评估一下更了解自己哦！</em>
                <span class="binding-set">
                    <i class="fa fa-times-circle no"></i>未评估<a class="setlink" href="/risk-estimate">评估</a>
                </span>
            </#if>
        </li>
    </ul>
</div>

<div id="resetUmpayPassDOM" class="pad-m popLayer" style="display: none;">
    <form name="resetUmpayPasswordForm" id="resetUmpayPasswordForm">
        <dl class="identityCodeTitle" align="center">
            通过身份证号重置支付密码
        </dl>
        <dl>
            <dt class="requireOpt">请输入您的身份证号</dt>
            <dd><input type="text" id="identityNumber" name="identityNumber" class="input-control">
            </dd>
        </dl>
        <div class="error-box tc"></div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-normal">确认重置</button>
    </form>
</div>

<div id="successUmpayPass" class="pad-m popLayer" style="display: none;">
    <dl>
        <dt>您的支付密码已被重置，请注意查收相关短信，查看新密码！</dt>
    </dl>
    <button type="button" class="btn btn-normal" id="readUmpayPass">我已查看</button>
</div>

<div id="changePassDOM" class="pad-m popLayer" style="display: none;">
    <form name="changePasswordForm" id="changePasswordForm">
        <dl>
            <dt class="requireOpt">请输入原密码</dt>
            <dd><input type="password" id="originalPassword" name="originalPassword" class="input-control" placeholder="请输入密码"></dd>
        </dl>
        <dl>
            <dt class="requireOpt">请输入新密码</dt>
            <dd><input type="password" id="newPassword" name="newPassword" class="input-control" placeholder="6位至20位，不能全为数字"></dd>
        </dl>
        <dl>
            <dt class="requireOpt">请确认新密码</dt>
            <dd><input type="password" id="newPasswordConfirm" name="newPasswordConfirm" class="input-control" placeholder="6位至20位，不能全为数字"></dd>
        </dl>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-normal">确认修改</button>
    </form>
</div>

<div id="changeEmailDOM" class="pad-m popLayer" style="display: none;">
    <form name="changeEmailForm" id="changeEmailForm" >
        <dl>
            <dt class="requireOpt">请输入邮箱</dt>
            <dd><input type="email" name="email" class="input-control" placeholder="请输入邮箱">

            </dd>
        </dl>
        <div class="error-box tl"></div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <button type="submit" class="btn btn-normal">绑定</button>
    </form>
</div>

<div id="change-email-success" class="pad-m popLayer" style="display: none;">
    验证邮件已发送至您的邮箱，请登录邮箱完成验证。
</div>

<div id="turnOnNoPasswordInvestDOM" class="pad-m popLayer" style="display: none;">
    <form id="get_authorization" name="turnOnNoPasswordInvestForm" action="${requestContext.getContextPath()}/agreement" method="post"  <@global.role hasRole="'INVESTOR', 'LOANER'">target="_blank"</@global.role>>
        <div class="tc text-m">推荐您开通免密出借功能，简化出借过程，出借快人一步。</div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="noPasswordInvest" value="true"/>
        <div class="tc person-info-btn" style="margin-top:50px;">
            <button class="btn  btn-cancel btn-close btn-close-turn-on" type="button">取消</button>
            <button class="btn btn-success btn-turn-on" type="submit">去联动优势授权</button>
        </div>
    </form>
</div>
<div id="turnOnNoPasswordInvestDOM_identified" class="pad-m popLayer" style="display: none; ">
    <form id="imageCaptchaForm" name="imageCaptchaForm"  method="post">
        <dl>
            <dt>推荐您开通免密出借功能，简化出借过程，出借快人一步，确认开启吗？</dt>
            <dd class="mt-20">
                <span>图形验证码：</span>
                <input type="text" class="input-control img-captcha image-captcha-text" name="imageCaptcha" maxlength="5" placeholder="请输入图形验证码"/>
                <img src="/no-password-invest/image-captcha" alt="" class="image-captcha" id="imageCaptcha"/>
                <input type="hidden" name="mobile" value="${mobile}"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </dd>
        </dl>
    </form>
    <form id="turnOnNoPasswordInvestForm" name="turnOnNoPasswordInvestForm" >
        <dl style="position: relative;margin-bottom: 0px;">
            <dd class="code-number code-number-hidden">验证码发送到${mobile?replace("^(\\d{3}).*(\\d{4})$","$1****$2","r")}</dd>
            <dd>
                <span>验&nbsp;&nbsp;&nbsp;证&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
                <input type="captcha" name="captcha" class="input-control sms-captcha captcha" placeholder="请输入验证码" maxlength="6">
                <input type="hidden" name="mobile" value="${mobile}"/>
                <button type="button" class="btn-normal get-captcha" disabled="disabled" data-url="/no-password-invest/send-captcha">获取验证码</button>
                <span class="voice-captcha" id="voice_captcha" style="display: none">如收不到短信，可使用 <a href="javascript:;" id="voice_btn">语音验证</a> </span>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            </dd>
        </dl>
        <div class="error-box" ></div>
        <div class="tc person-info-btn">
            <button class="btn btn-cancel" type="button">取消</button>
            <button class="btn btn-success btn-close-turn-off" type="submit">我要开启</button>
        </div>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
<#--关闭免密支付 -->
<div id="turnOffNoPasswordInvestDOM" class="pad-m popLayer" style="display: none; padding-top:20px;padding-bottom: 0">
        <div class="tc text-m">免密支付可以帮助您在投出借时快速购买标的，<br/>您是否确认关闭免密支付？</div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="tc person-info-btn" style="margin-top:40px;">
            <button class="btn  btn-cancel btn-close btn-close-turn-on" type="button">取消</button>
            <button class="btn btn-success btn-turn-off" type="button">我要关闭</button>
        </div>
</div>
<#--已关闭免密支付 -->
<div id="alreadyTurnOffDOM" class="pad-m popLayer" style="display: none;">

        <div class="font-icon"></div>
        <div class="tc text-m">免密支付已关闭</div>
        <div class="tc person-info-btn" style="margin-top:10px;">
            <button class="btn  btn-success" type="button">我知道了</button>
        </div>

</div>
<div id="noPasswordInvestDOM" class="pad-m" style="display: none;">
    <p>请在新打开的联动优势页面充值完成后选择：</p>
    <p><a href="/personal-info" class="btn-success" data-category="确认成功" data-label="noPasswordInvest">继续</a>(授权后视情况可能会有一秒或更长的延迟)</p>
    <span>遇到问题请拨打我们的客服热线：400-169-1188（工作日 9:00-20:00）</span>
</div>
<div class="risk-tip-item" id="riskTip">
    <i class="close-risk"></i>
    <a href="/risk-estimate" class="to-risk">立即评测</a>
</div>
</@global.main>


