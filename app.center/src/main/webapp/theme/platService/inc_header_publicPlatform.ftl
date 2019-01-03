
<!-- Header Start -->
<div class="header">
	<div class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
		<div class="banner-publicPlatform">
			<div class="inner wrap clearfix">
				<div class="logo"></div>
				<ul class="navbar-top-links navbar-right">
					[#if appService??]
					<li>
						<a href="">
							<img id="user-photo" [#if appService.headImg??] src="${appService.headImg}"  [#else] src="" [/#if] onerror="this.src='${ctxPath}/theme/platService/_files/images/getheadimg.jpg'" />
						</a>
						<div class="account">
							<div class="row">
								<div class="col-md-6 type"><span class="pull-right label label-primary">订阅号</span></div>
								<div class="col-md-6 status"><span class="pull-right label label-success">已认证</span></div>
							</div>
							<div class="row username">${appService.serviceName}</div>
						</div>
						
					</li>
					[/#if]
					<li>
						<a href="/" class="home"><i class="i-home"></i>首页</a>
					</li>
					<li>
						<a href="/logout.htx" class="sign-out"><i></i>退出登录</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<!-- Header End -->



