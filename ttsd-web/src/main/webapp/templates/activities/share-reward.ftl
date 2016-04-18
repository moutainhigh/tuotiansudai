<#import "../macro/global.ftl" as global>
<@global.main pageCss="${css.company_activity}" pageJavascript="${js.share_reward}" activeNav="" activeLeftNav="" title="推荐奖励_拓天活动_拓天速贷" keywords="拓天速贷,拓天活动.推荐奖励" description="拓天速贷专属生日月特权,生日月投资收益翻倍,拓天速贷专属活动超高收益等你拿.">
<div class="share-reward-container">
	<div class="rank-phone-model">
        <img src="${staticServer}/images/sign/actor/sharereward/share-top-bgg.png" width="100%">
    </div>
   <div class="wp clearfix">
   		<div class="left-bg"></div>
   		<div class="right-bg"></div>
   		<div class="reward-info-title">
   			<div class="title-text">
   				天天带领亲朋好友富贵同享，一见钟情月月领佣金。
   			</div>
   			<p>
   				平台用户邀请好友投资即可享受1%的年化投资额佣金；
				好友继续推荐投资用户，仍可享受1%的年化投资额佣金
				折合2%的收益率，多多推荐赢取丰厚奖金...&nbsp;...
   			</p>
   		</div>
   		<div class="share-example">
   			<div class="example-title">
   				<span class="title-mole"></span>
   				<span class="title-text"></span>
   			</div>
   			<p>A邀请B注册，B买入<span>10</span>万元6月期产品，A获得<span>500</span>元现金奖励；</p>
   			<p>B邀请C注册，C买入<span>1</span>万元3月期产品，B获得<span>25</span>元现金奖励，A也获得<span>25</span>元现金奖励；</p>
   			<div class="example-case">如果每人可以邀请3个朋友投资：</div>
   			<div class="example-case-detail"></div>
   		</div>
   		<div class="share-recommend">
			<a href="/referrer/refer-list">立即推荐</a>
   		</div>
   		<div class="share-rules">
   			<div class="rules-title">
   				活动规则
   			</div>
   			<ul class="rules-list">
   				<li>平台用户享受2级推荐奖励；</li>
   				<li>推荐好友成功投资享有1%的年化投资额佣金奖励；</li>
   				<li>推荐奖励结算不限买入金额，不限购买笔数；</li>
   				<li>佣金奖励在放款后一次性以现金形式直接发放；</li>
   				<li>用户可在&nbsp;“我的账户”&nbsp;中查询。</li>
   			</ul>
   			<p class="rules-sign">***活动遵守拓天速贷法律声明，最终解释权归拓天速贷平台所有。</p>
   		</div>
   </div>
</div>
<div class="share-reward-phone">
	<div class="rank-phone-model">
        <img src="${staticServer}/images/sign/actor/sharereward/share-phone-top.png" width="100%">
    </div>
    <div class="wp clearfix">
    	<#if isAppSource>
    	<div id="reward-code" class="reward-code">
    		<input id="address" class="address" value="http://www.baidu.com" />
    		<div id="code" class="code"></div>
    		<div class="code-text">
				<p>长按二维码</p>
				<p>轻松注册&nbsp;坐享收益</p>
    		</div>
    	</div>
    	</#if>
	    <div class="reward-info-title">	</div>
	    <div class="share-example"></div>
	    <div class="share-recommend">
			<a href="/referrer/refer-list">立即推荐</a>
	    </div>
	    <div class="share-rules"></div>
	</div>
</div>
</@global.main>