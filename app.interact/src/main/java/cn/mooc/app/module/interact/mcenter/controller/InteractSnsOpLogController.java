package cn.mooc.app.module.interact.mcenter.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.utils.DateTimeUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.interact.data.entity.SnsOpLog;
import cn.mooc.app.module.interact.model.SnsOpLogEchartsData;
import cn.mooc.app.module.interact.model.SnsOpLogGroupByFtype;
import cn.mooc.app.module.interact.service.SnsOpLogService;

/**
 * InteractSnsOpLogController
 * 日志控制器
 * 
 * @author oyhx
 * @date 2016-05-12
 */
@Controller
@RequestMapping("/interact/snsOpLog")
public class InteractSnsOpLogController extends InteractModuleController{
	@Autowired
	private SnsOpLogService snsOpLogService;
	
	@RequestMapping("/getChartsData")
	@ResponseBody
	public Map<String, Object> getChartsData(String startDate, String endDate){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtil.isNull(startDate)){
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DAY_OF_MONTH, -7);
			startDate = df.format(now.getTime());
		}
		if(StringUtil.isNull(endDate)){
			Calendar now = Calendar.getInstance();
			endDate = df.format(now.getTime());
		}
		Date start = DateTimeUtil.toDateTime(startDate, "yyyy-MM-dd");
		Date end = DateTimeUtil.toDateTime(endDate, "yyyy-MM-dd");
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> dateList = new ArrayList<String>();
		Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        endCalendar.setTime(end);
        while (startCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis()) {
        	dateList.add(df.format(startCalendar.getTime()));
        	startCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
        int lsSize = dateList.size();
        String[] dateLs = new String[lsSize];
        dateLs = dateList.toArray(dateLs);
        result.put("category", dateLs);
//        result.put("legend", new String[]{"评论","点赞","收藏","关注用户","关注栏目","用户登录","关注专题","删除评论"});
        result.put("legend", new String[]{"用户登录", "点赞", "评论","收藏"});
        
        List<SnsOpLogGroupByFtype> list = snsOpLogService.groupByFtypeByDay(start, end);
        Integer[] commentList = initLsData(list, dateList, SnsOpLog.FTYPE_COMMENT);
        SnsOpLogEchartsData commChart = new SnsOpLogEchartsData("评论", commentList);
        result.put("commChart", commChart);
        Integer[] diggList = initLsData(list, dateList, SnsOpLog.FTYPE_DIGG);
        SnsOpLogEchartsData diggChart = new SnsOpLogEchartsData("点赞", diggList);
        result.put("diggChart", diggChart);
        Integer[] collectList = initLsData(list, dateList, SnsOpLog.FTYPE_COLLECT);
        SnsOpLogEchartsData collectChart = new SnsOpLogEchartsData("收藏", collectList);
        result.put("collectChart", collectChart);
        Integer[] attUserList = initLsData(list, dateList, SnsOpLog.FTYPE_ATTENTION_USER);
        SnsOpLogEchartsData attUserChart = new SnsOpLogEchartsData("关注用户", attUserList);
        result.put("attUserChart", attUserChart);
        Integer[] attNodeList = initLsData(list, dateList, SnsOpLog.FTYPE_ATTENTION_NODE);
        SnsOpLogEchartsData attNodeChart = new SnsOpLogEchartsData("关注栏目", attNodeList);
        result.put("attNodeChart", attNodeChart);
        Integer[] loginList = initLsData(list, dateList, SnsOpLog.FTYPE_LOGIN);
        SnsOpLogEchartsData loginChart = new SnsOpLogEchartsData("用户登录", loginList);
        result.put("loginChart", loginChart);
        Integer[] attSpecList = initLsData(list, dateList, SnsOpLog.FTYPE_ATTENTION_SPECIAL);
        SnsOpLogEchartsData attSpecChart = new SnsOpLogEchartsData("关注专题", attSpecList);
        result.put("attSpecChart", attSpecChart);
        Integer[] commDelList = initLsData(list, dateList, SnsOpLog.FTYPE_COMMENT_DEL);
        SnsOpLogEchartsData commDelChart = new SnsOpLogEchartsData("删除评论", commDelList);
        result.put("commDelChart", commDelChart);
        
		return result;
        
//    	public static final Integer FTYPE_COMMENT = 1;// 评论
//    	public static final Integer FTYPE_DIGG = 2;// 点赞
//    	public static final Integer FTYPE_COLLECT = 3;// 收藏
//    	public static final Integer FTYPE_ATTENTION_USER = 4;// 关注用户
//    	public static final Integer FTYPE_ATTENTION_NODE = 5;// 关注栏目
//    	public static final Integer FTYPE_LOGIN = 6;// 用户登录
//    	public static final Integer FTYPE_ATTENTION_SPECIAL = 7;// 关注专题
//    	
//
//    	public static final Integer FTYPE_COMMENT_DEL = 11;// 删除评论
	}

	private Integer[] initLsData(List<SnsOpLogGroupByFtype> list, List<String> dateList, Integer ftypeComment) {
		// TODO Auto-generated method stub
		Integer[] result = new Integer[dateList.size()];
		int flag = 0;
		for (String date : dateList) {
			for(SnsOpLogGroupByFtype group : list){
				if (group.getFtype().equals(ftypeComment+"") && group.getCrateDate().equals(date)) {
					//应李靖宇要求数字*11，2017-9-8
					result[flag] = group.getTotal()*11;
				}
			}
			flag++;
		}
		return result;
	}
}
