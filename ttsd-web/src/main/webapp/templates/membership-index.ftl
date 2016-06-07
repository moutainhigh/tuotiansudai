<#import "macro/global.ftl" as global>
<@global.main pageCss="${css.membership}" pageJavascript="" activeNav="会员中心" activeLeftNav="" title="会员中心" menuSwitch="membershipMenus">

<div class="global-member-ship">
	<#-- 登录 -->
	<div class="user-info-block page-width">
		<div class="info clearfix">
			<div class="avatar fl">
				<img src="${staticServer}/images/sign/head.png" />
				<#-- 更改下面这个i标签的class值来改变显示的等级 0级对应vip-0 1级对应vip-1 以此类推-->
				<i class="vip-no-bg vip-${membershipLevel!}"></i>
			</div>
			<div class="text">
				<p><span class="orange font20">您好！${loginName!}</span><span class="font14">会员有效期还有：<strong class="font22">300</strong>天</span></p>
				<p class="font14">我的成长值：<strong class="font22">${membershipPoint!}</strong></p>
			</div>
		</div>
		<div class="progress">
			<div class="progress-bar">
				<#-- 下面这个标签的style属性里的width的值为 用户当前成长值占总值的百分比 例如30% -->
				<div class="progress-bar-fill" style="width: ${progressBarPercent!}%"></div>
				<div class="vip-bg vip-0"></div>
				<div class="vip-bg vip-1"></div>
				<div class="vip-bg vip-2"></div>
				<div class="vip-bg vip-3"></div>
				<div class="vip-bg vip-4"></div>
				<div class="vip-bg vip-5"></div>
				<#-- 更改下面这个div标签的class值来改变显示的等级 0级对应popup-0 1级对应popup-1 以此类推-->
				<#-- 更改下面这个i标签的class值来改变显示的等级 0级对应vip-0 1级对应vip-1 以此类推-->
				<div class="popup popup-1">还需<strong>${membershipNextLevelValue!}</strong>成长值就能就能尊享<i class="vip-no-bg vip-${membershipNextLevel!}"></i>特权了哦！<i class="triangle"></i></div>
			</div>
		</div>
	</div>
	<#-- 未登录 -->
	<div class="user-info-block page-width">
		<div class="info clearfix">
			<div class="avatar fl">
				<img src="${staticServer}/images/sign/head.png" />
			</div>
			<div class="text">
				<p><span class="orange font20">您好！水电费水电费</span><span class="font14">会员有效期还有：<strong class="font22">300</strong>天</span></p>
				<p class="font14">我的成长值：<strong class="font22">2000</strong></p>
			</div>
		</div>
		<div class="progress">
			<div class="progress-bar">
				<div class="vip-bg vip-0"></div>
				<div class="vip-bg vip-1"></div>
				<div class="vip-bg vip-2"></div>
				<div class="vip-bg vip-3"></div>
				<div class="vip-bg vip-4"></div>
				<div class="vip-bg vip-5"></div>
			</div>
		</div>
	</div>

	<#-- 登录 -->
	<div class="my-level">
		<#-- 下面这些i标签 用户有哪个权限就显示哪个权限 -->
		拥有特权
		<i class="icon multi-ensuring"></i>
		<i class="icon anytime-withdraw"></i>
		<i class="icon membership-giftbag"></i>
		<i class="icon birthday-privilege"></i>
		<i class="icon vip-service"></i>
		<i class="icon spec-financial-advisor"></i>
		<i class="icon birthday-benefits"></i>
		<i class="icon service-fee-discount"></i>
		想提高等级? <a href="#">去投资></a>
	</div>

	<#-- 未登录 -->
	<div class="my-level">
		特权福利
		<i class="icon multi-ensuring"></i>
		<i class="icon anytime-withdraw"></i>
		<i class="icon membership-giftbag"></i>
		<i class="icon birthday-privilege"></i>
		<i class="icon vip-service"></i>
		<i class="icon spec-financial-advisor"></i>
		<i class="icon birthday-benefits"></i>
		<i class="icon service-fee-discount"></i>
		想提高等级? <a href="#">去投资></a>
	</div>

	<div class="levels">
		<div class="main-title">
			<div class="inner">
				<h2>我的特权</h2>
			</div>
		</div>
		<div class="levels-list">
			<#-- 下面这些li标签 如果想让图标变灰色就把className最后加一个 “-hui” 例如第一个 -->
			<ul class="clearfix">
				<li class="multi-ensuring-hui">
					<h3>多重保障</h3>
					<p>三方托管、抵押模式、严格风控体系、平台信息透明、数据信息安全等保障</p>
				</li>
				<li class="anytime-withdraw">
					<h3>随时提现</h3>
					<p>24小时随时提现，16:00点前提现当日到账，16:00后提现次日到账</p>
				</li>
				<li class="birthday-privilege">
					<h3>生日特权</h3>
					<p>生日月投资，投90天产品首期收益翻1.5倍，投180天、360天产品首期收益翻2倍</p>
				</li>
				<li class="membership-giftbag">
					<h3>会员礼包</h3>
					<p>每月发放投资红包，588、688、788、888元随机派送</p>
				</li>
				<li class="service-fee-discount">
					<h3>服务费折扣</h3>
					<p>平台向V0、V1会员收取利息的10%作为服务费，V2收取9%，V3、V4收取8%，V5仅收取7%</p>
				</li>
				<li class="vip-service">
					<h3>贵宾专线</h3>
					<p>贵宾级客服服务，投资问题，意见建议专享直达</p>
				</li>
				<li class="spec-financial-advisor">
					<h3>专享理财顾问</h3>
					<p>发标时间，平台活动，理财顾问第一时间通知到您</p>
				</li>
				<li class="birthday-benefits">
					<h3>生日福利</h3>
					<p>V5专享，平台将会在会员生日时送上神秘礼包</p>
				</li>
			</ul>
		</div>
	</div>
	<#-- 登录后显示这个 -->
	<div class="buy-vip">
		<div class="main-title">
			<div class="inner">
				<h2>会员购买</h2>
			</div>
		</div>
		<div class="buy-vip-list">
			<table>
				<thead>
					<tr>
						<th>会员购买</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>有效期</td>
						<td>1个月</td>
						<td>1年</td>
					</tr>
					<tr>
						<td>价格</td>
						<td>25元</td>
						<td>180元</td>
					</tr>
				</tbody>
			</table>
			<p class="text-info">购买渠道即将开放，敬请期待！</p>
		</div>
	</div>
	<#-- 没登录显示这个 -->
	<div class="level-table">
		<div class="main-title">
			<div class="inner">
				<h2>会员特权</h2>
			</div>
		</div>
		<div class="table">
			<table>
				<thead>
					<tr>
						<th>特权</th>
						<th><i class="vip-badge vip-0"></i></th>
						<th><i class="vip-badge vip-1"></th>
						<th><i class="vip-badge vip-2"></th>
						<th><i class="vip-badge vip-3"></th>
						<th><i class="vip-badge vip-4"></th>
						<th><i class="vip-badge vip-5"></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>多重保障</td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
					</tr>
					<tr>
						<td>随时提现</td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
					</tr>
					<tr>
						<td>生日特权</td>
						<td></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
					</tr>
					<tr>
						<td>会员礼包</td>
						<td></td>
						<td></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
					</tr>
					<tr>
						<td>服务费折扣（折后费率）</td>
						<td>10%（基础费率）</td>
						<td>10%（基础费率）</td>
						<td>9%</td>
						<td>8%</td>
						<td>8%</td>
						<td>7%</td>
					</tr>
					<tr>
						<td>贵宾专线</td>
						<td></td>
						<td></td>
						<td></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
					</tr>
					<tr>
						<td>专享理财顾问</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
					</tr>
					<tr>
						<td>生日福利</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td><i class="fa fa-check-circle-o" aria-hidden="true"></i></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>

</@global.main>