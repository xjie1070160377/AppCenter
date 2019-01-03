
<div class="msglistbox">
	<div>${msg.pushTime}</div>
	[#list msg.detail as msgDetail] [#if msgDetail.index == 0]
	<div class="msglist msglistpriheight msglistcurrent" data-id="1"
		data-index="0">
		<div class="primdiv imgdiv"
			style="background-image: url('${msgDetail.headImage}')">
			<div class="backgrounddiv"></div>
			<div class="currenttitlediv">
				<h4 class="currenttitle titlediv">${msgDetail.title}</h4>
			</div>
		</div>
	</div>
	[/#if] [/#list] [#list msg.detail as msgDetail] [#if msgDetail.index != 0]
	<div class="msglist" data-id="2" data-index="${msgDetail.index}">
		<div class="seconddiv">
			<div class="backgrounddiv imgdiv"
				style="background-image: url('${msgDetail.headImage}')"></div>
			<div>
				<h4 class="secondtitle titlediv">${msgDetail.title}</h4>
			</div>
		</div>
	</div>
	
</div>

