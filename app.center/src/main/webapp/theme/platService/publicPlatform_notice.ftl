[#escape x as (x)!?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>红客移动客户端</title>
	<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/comm.css" />
	<link type="text/css" rel="stylesheet" href="${ctxPath}/static/base/css/bootstrap.min.css"/>
	<link type="text/css" rel="stylesheet" href="${ctxPath}/static/base/font-awesome/css/font-awesome.min.css"/>
	<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/nav.css" /> 
	<link type="text/css" rel="stylesheet" href="${ctxPath}/theme/platService/_files/css/wechat.css" />
	<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/comm.js"></script>
	<script type="text/javascript" src="${ctxPath}/theme/platService/_files/js/index.js"></script>
	<style>
		.menu-box a.notice{
		    background: #e74c3c;
		    color: #fff;
		}
	</style>
</head>
<body>
	[#include "inc_header_publicPlatform.ftl"/]
	<div class="main body notice">
		 <div class="content info_box clearfix">
		    [#include "inc_left_publicPlatform.ftl"/]
		    <div class="right">
		    	<div class="right-box">
		    		<div class="count row">
		    			<div class="col col-lg-4">
		    				<div class="msg">
		    					<a href="javascript:">
		    						<i></i>
		    						<em>3</em>
		    						<p>新消息</p>
		    					</a>
		    					
		    				</div>
		    			</div>
		    			<div class="col col-lg-4">
		    				<div class="new-fans">
		    					<a href="javascript:">
		    						<i></i>
		    						<em>5</em>
		    						<p>新增人数</p>
		    					</a>
		    				</div>
		    			</div>
		    			<div class="col col-lg-4">
		    				<div class="total-fans">
		    					<a href="javascript:">
		    						<i></i>
		    						<em>9</em>
		    						<p>总用户数</p>
		    					</a>
		    				</div>
		    			</div>
		    		</div>
		    	
		    		<div class="notice-box cow">
		    			<div class="title">
		    				<h2>系统公告</h2>
		    			</div>
		    			
		    			
		    			
		    			
		    			<ul class="mp_news_list">
			                <li class="mp_news_item">
					            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1456837226&amp;version=1&amp;lang=zh_CN" target="_blank">
					                <strong>公众号授权流程改版，可以自定义选择权限授权给第三方平台                                        <i class="icon_common new"></i>
					                                    </strong>
					                <span class="date">2016-03-01</span>
					            </a>
			                </li>
			                <li class="mp_news_item">
					            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1453212248&amp;version=6&amp;lang=zh_CN" target="_blank">
					                <strong>微信网页设计样式库发布                                        <i class="icon_common new"></i>
					                                    </strong>
					                <span class="date">2016-01-20</span>
					            </a>
			               </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1453210197&amp;version=4&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众平台关于整顿滥用客服接口和模板消息接口行为的公告                                    </strong>
			                <span class="date">2016-01-19</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1452678137&amp;version=2&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众平台新增接口实时调用量清零功能                                    </strong>
			                <span class="date">2016-01-13</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1452002101&amp;version=9&amp;lang=zh_CN" target="_blank">
			                <strong>公众平台发布微信web开发者工具                                    </strong>
			                <span class="date">2016-01-11</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1451482848&amp;version=3&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众平台关于整顿侵犯影视作品知识产权行为的公告                                    </strong>
			                <span class="date">2015-12-30</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1451394321&amp;version=1&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众平台关于公众号第三方平台安全风险管理的公告                                    </strong>
			                <span class="date">2015-12-29</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1450268542&amp;version=7&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众平台面向开发者新增个性化菜单接口                                    </strong>
			                <span class="date">2015-12-16</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1449636367&amp;version=21&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众平台功能升级                                    </strong>
			                <span class="date">2015-12-10</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1449037396&amp;version=1&amp;lang=zh_CN" target="_blank">
			                <strong>公众平台临时带参数二维码的有效期增加为30天                                    </strong>
			                <span class="date">2015-12-02</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1447989443&amp;version=1&amp;lang=zh_CN" target="_blank">
			                <strong>公众平台文章编辑器改版                                    </strong>
			                <span class="date">2015-11-20</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1447842930&amp;version=8&amp;lang=zh_CN" target="_blank">
			                <strong>微信发布优惠券新玩法                                    </strong>
			                <span class="date">2015-11-18</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1444378622&amp;version=4&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众平台推出针对开发者的微信认证事件推送                                    </strong>
			                <span class="date">2015-10-10</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1441697589&amp;version=18&amp;lang=zh_CN" target="_blank">
			                <strong>微信卡券新功能开放公告                                    </strong>
			                <span class="date">2015-09-08</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1439368008&amp;version=22&amp;lang=zh_CN" target="_blank">
			                <strong>公众平台新增城市服务功能                                    </strong>
			                <span class="date">2015-08-19</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1438332908&amp;version=6&amp;lang=zh_CN" target="_blank">
			                <strong>公众平台新增图文内容中图片上传接口，并过滤外链图片                                    </strong>
			                <span class="date">2015-07-31</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1437657670&amp;version=2&amp;lang=zh_CN" target="_blank">
			                <strong>公众平台支持原创文章选择转载类型                                    </strong>
			                <span class="date">2015-07-24</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1437461442&amp;version=7&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众平台手机版公测                                    </strong>
			                <span class="date">2015-07-21</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1436453021&amp;version=6&amp;lang=zh_CN" target="_blank">
			                <strong>公众号文章新增语音功能                                    </strong>
			                <span class="date">2015-07-10</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1436354276&amp;version=11&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众平台运营管理报告                                    </strong>
			                <span class="date">2015-07-09</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1436333625&amp;version=2&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众平台推出运营中心                                    </strong>
			                <span class="date">2015-07-08</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1435761199&amp;version=3&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众平台关于禁止发布签类测试信息的公告                                    </strong>
			                <span class="date">2015-07-01</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1435633820&amp;version=12&amp;lang=zh_CN" target="_blank">
			                <strong>公众平台基础功能优化                                    </strong>
			                <span class="date">2015-06-30</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1435557582&amp;version=4&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众平台增加批量获取用户基本信息接口                                    </strong>
			                <span class="date">2015-06-29</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1433499635&amp;version=10&amp;lang=zh_CN" target="_blank">
			                <strong>微信公众号文章也可以添加音乐                                    </strong>
			                <span class="date">2015-06-05</span>
			            </a>
			                    </li>
			                <li class="mp_news_item">
			            <a href="/cgi-bin/announce?action=getannouncement&amp;key=1432021564&amp;version=2&amp;lang=zh_CN" target="_blank">
			                <strong>公众平台向认证的政府与媒体类订阅号开放网页授权接口                                    </strong>
			                <span class="date">2015-05-19</span>
			            </a>
			                    </li>
			                       
			    </ul>
			    
			    
			    
			    
			    
			    
		    		</div>
		    	
		    	</div>
			</div>
		  </div>	
	
	
	
	</div>
	
	[#include "inc_footer.ftl"]

</body>














[/#escape]