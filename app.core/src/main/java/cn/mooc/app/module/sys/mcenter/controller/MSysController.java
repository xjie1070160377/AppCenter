package cn.mooc.app.module.sys.mcenter.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import cn.mooc.app.core.annotation.SameUrlData;
import cn.mooc.app.core.data.entity.SysLogEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.data.entity.SysUserVCoin;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.exception.WebShowException;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.DateTimeUtil;
import cn.mooc.app.core.utils.ExcelUtil;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.HttpUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.sys.service.WebSysService;
import javapns.org.json.JSONObject;
@Controller
@RequestMapping("/sys")
public class MSysController extends SysModuleController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private WebSysService webSysService;
	
	@RequestMapping("/statistics")
	public String statistics() {
		
		return ModuleView("/statistics");
		
	}

	@RequestMapping(value = "/heartBeat")
	@ResponseBody
	public Map<String, Object> heartBeat(Model model) {
		
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/serverTime")
	@ResponseBody
	public Map<String, Object> serverTime(Model model) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		resMap.put("serverTime", DateTimeUtil.getCurrentTime());
		
		return HttpResponseUtil.resMapJson(resMap, true, "");
		
	}
	
	@RequestMapping("/changePwd")
	public String changePwd(Model model) {
		
		return ModuleView("/changePwd");
	}

	@RequestMapping("/sysUserList")
	public String sysUserList(Model model, PagerParam pageParam) {		
		
		return ModuleView("/sysUserList");
	}
	
	@RequestMapping("/sysUserListJson")
	@ResponseBody
	public Map<String, Object> sysUserListJson(Model model,PagerParam pageParam,String columnFiled,String keyWord,String rolename) {
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		if(StringUtils.isNotEmpty(columnFiled)){
			if(columnFiled.endsWith("_superUser")){
				searchParams.put(columnFiled, Boolean.parseBoolean(keyWord));
			}else {
				searchParams.put(columnFiled, keyWord);
			}
		}
		
		
		Page<SysUserEntity> pageData = sysDataService.findSysUserList(searchParams, pageParam, rolename);
        		 
		return HttpResponseUtil.successJson(pageData, pageParam);
	}
	
	@RequestMapping("/sysUserAdd")
	public String sysUserAdd(Model model, SysUserEntity userEntity) {
		userEntity.setStatus(1);
		model.addAttribute("entity", userEntity);
		
		return ModuleView("/sysUserForm");
	}
	
	@RequestMapping("/sysUserEdit")
	public String sysUserEdit(Model model, long userId) {
		
		SysUserEntity userEntity = sysDataService.getUserInfoById(userId);
		model.addAttribute("entity", userEntity);
		
		return ModuleView("/sysUserForm");
	}
	

	@RequestMapping(value = "/sysUserSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sysUserSave(Model model, SysUserEntity userEntity) {

		long userId = userEntity.getId();
		
		if (userId == 0) {			
			//新增
			if(StringUtils.isBlank(userEntity.getUserName())){
				return HttpResponseUtil.failureJson("用户名不能为空");
			}
			
			if(StringUtils.isBlank(userEntity.getPassWord())){
				return HttpResponseUtil.failureJson("密码不能为空");
			}
		}
		
		try{
			userEntity = this.sysDataService.saveSysUser(userEntity);
			this.sysDataService.syncEmptySysUserExt(userEntity.getId(), userEntity.getUserName());
			webContext.sysUserLog(LogType.UserOpr, "保存 " +userEntity.getUserName() +" 的用户信息");
		}catch(Exception e){
			logger.error("sysUserSave", e);
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}
	
	/** 从Excel 导入用户信息
	 * @param model
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/importSysUsersFromExcel", method = RequestMethod.POST)
	@SameUrlData
	@ResponseBody
	public Map<String, Object> importSysUsersFromExcel(HttpServletRequest request, 
			HttpServletResponse response, Model model, String filePath) {
		try {
			ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			String serverRootPath = servletContext.getRealPath("/");
			File file = new File(serverRootPath.concat(filePath));
			String ext = FilenameUtils.getExtension(file.getName()).toLowerCase();
			List<SysUserEntity> userList = new ArrayList<SysUserEntity>();
			if(ext.equals("xls")){
				userList = readXls(filePath);
			}else{
				userList = readXlls(filePath);
			}
			List<SysUserEntity> users = this.sysDataService.saveSysUsers(userList, 1, SysUserEntity.inner_user, false);
			return HttpResponseUtil.successJson(users, users.size(), 1, new PagerParam());
		} catch (WebShowException e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("IOException：导入失败稍后重试");
		}catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("SaveFailureException：导入失败稍后重试");
		}catch(Exception e){
			e.printStackTrace();
			return HttpResponseUtil.failureJson("导入失败稍后重试");
		}
	}
	
	/** 导出学员信息
	 * @param model
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/exportSysUsersExcel")
	@SameUrlData
	public void  exportSysUsersExcel(HttpServletRequest request, 
			HttpServletResponse response, Model model, String ids, String filePath) {
		if(StringUtils.isNotEmpty(ids) && StringUtils.isNotEmpty(filePath)){
			try {
				filePath = java.net.URLDecoder.decode(filePath, "UTF-8");
				ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
				String serverRootPath = servletContext.getRealPath("/");
				File file = new File(serverRootPath.concat(filePath));
				String ext = FilenameUtils.getExtension(file.getName()).toLowerCase();
				List<SysUserEntity> userList = new ArrayList<SysUserEntity>();
				if(ext.equals("xls")){
					userList = readXls(filePath);
				}else{
					userList = readXlls(filePath);
				}
				//
				String[] ids_string = ids.split(",");
				Long[] userIds = new Long[ids_string.length];
				for(int i = 0; i < ids_string.length; i++){
					userIds[i] = StringUtil.strnull2Long(ids_string[i]);
				}
				List<SysUserEntity> users = sysDataService.getUserInfoByIds(userIds);
				List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
				for(SysUserEntity user : userList){
					for(SysUserEntity user1 : users){
						if(user1.getUserName().indexOf(user.getUserName()) >= 0){
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("userName", user1.getUserName());
							map.put("passWord", user.getPassWord().toString());
							records.add(map);
						}
					}
				}
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("records", records);
				ExcelUtil.expExcel(request, response, getJxslTemplate("users.xls"), map, "本次导入的用户列表.xls");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "/sysUserDel", method = RequestMethod.POST)
	@ResponseBody
	public boolean sysUserDel(long userId) {
		//删除
		boolean result = false;
		try {
			result = sysDataService.delSysUser(userId);
			webContext.sysUserLog(LogType.UserOpr, "删除用户：" + userId);
		} catch (Exception e) {
			logger.error("sysUserDel",e);
		}
		return result;
	}
	
	@RequestMapping(value = "/sysUserDels", method = RequestMethod.POST)
	@ResponseBody
	public boolean sysUserDels(Long[] userIds) {
		//删除
		int result = 0;
		try {
			result = sysDataService.delSysUsers(userIds);
			webContext.sysUserLog(LogType.UserOpr, "删除用户：" + StringUtils.join(userIds, ","));
		} catch (Exception e) {
			logger.error("sysUserDels",e);
		}
		return result > 0;
	}
	
	@RequestMapping("/sysUserExtList")
	public String sysUserExtList(Model model, PagerParam pageParam) {		
		
		return ModuleView("/sysUserExtList");
	}
	
	@RequestMapping("/sysUserExtListJson")
	@ResponseBody
	public Map<String, Object> sysUserExtListJson(Model model, PagerParam pageParam,String columnFiled,String keyWord) {
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		
		Page<SysUserExt> pageData = this.webSysService.findUserExtList(searchParams, pageParam);
        		 
		return HttpResponseUtil.successJson(pageData, pageParam);
	}
	
	@RequestMapping(value = "/saveUserPwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveUserPwd(Model model, String oldPassWord, String passWord,String rePassWord) {
		
		long userId = this.webContext.getCurrentUserId();
		
		boolean resultStatus = true;
		
		if(StringUtils.isBlank(oldPassWord)){
			return HttpResponseUtil.failureJson("原密码不能为空");
		}
		
		if(StringUtils.isBlank(passWord) || StringUtils.isBlank(rePassWord)){
			return HttpResponseUtil.failureJson("密码不能为空");
		}
		
		if(!passWord.equals(rePassWord)){
			return HttpResponseUtil.failureJson("两次输入的密码不一致");
		}
		
		if(!this.sysDataService.checkSysUserPwdSafe(passWord)){
			//增加该逻辑的目的是因为需要进入系统后台的用户，密码不能过于简单
			return HttpResponseUtil.failureJson("新的密码过于简单，修改失败");
		}
		
		try {
			resultStatus = this.sysDataService.changeSysUserPwd(userId, oldPassWord, passWord);
			webContext.sysUserLog(LogType.UserOpr, "修改用户 " + userId+ " 的密码： "+ (resultStatus?"成功":"修改密码失败"));
			return HttpResponseUtil.resMapJson(resultStatus, resultStatus?"":"修改密码失败");
		} catch (Exception e) {
			logger.error("saveUserPwd", e);
			return HttpResponseUtil.resMapJson(false, e.getMessage());
		}
		
		
	}
	
	@RequestMapping("/sysUCoinList")
	public String sysUCoinList(Model model) {
		
		return ModuleView("/sysUCoinList");
	}
	
	@RequestMapping("/sysUCoinListJson")
	@ResponseBody
	public Map<String, Object> sysUCoinListJson(Model model,PagerParam pageParam,String columnFiled,String keyWord) {
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		
		Page<SysUserVCoin> pageData = sysDataService.findSysUCoinList(searchParams, pageParam);
        		 
		return HttpResponseUtil.successJson(pageData, pageParam);
	}
	
	@RequestMapping("/sysLogList")
	public String sysLogList(Model model) {
		
		return ModuleView("/sysLogList");
	}
	
	@RequestMapping("/sysLogListJson")
	@ResponseBody
	public Map<String, Object> sysLogListJson(Model model,PagerParam pageParam,String columnFiled,String keyWord) {
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		
		Page<SysLogEntity> pageData = this.webSysService.findLogList(searchParams, pageParam);
        		 
		return HttpResponseUtil.successJson(pageData, pageParam);
	}
	
	@RequestMapping("/sysUserExtEdit")
	public String sysUserExtEdit(Model model, long userId) {
		SysUserEntity userEntity = sysDataService.getUserInfoById(userId);
		model.addAttribute("user", userEntity);
		
		SysUserExt userExt = sysDataService.getSysUserExt(userId);
		if(userExt==null){
			userExt = new SysUserExt();
			userExt.setUserId(userId);
		}
		logger.debug("网络获取:"+userExt.getBankCard());
		model.addAttribute("entity", userExt);
		
		return ModuleView("/sysUserExtForm");
	}
	

	@RequestMapping(value = "/sysUserExtSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sysUserExtSave(Model model, SysUserExt sysUserExt) {

		long userId = sysUserExt.getUserId();
		
		if (userId <= 0) {			
			return HttpResponseUtil.failureJson("参数非法");			
		}
		
		if(StringUtils.isBlank(sysUserExt.getNickName())){
			return HttpResponseUtil.failureJson("用户昵称不能为空");
		}
		logger.debug("BankCard1:"+sysUserExt.getBankCard());
		if(!StringUtils.isBlank(sysUserExt.getBankCard())) {
		logger.debug("BankCard2:"+sysUserExt.getBankCard());
			if(isIcbcCard(sysUserExt.getBankCard())){
				logger.debug("BankCard4:"+sysUserExt.getBankCard());
				return HttpResponseUtil.failureJson("请输入正确的工行卡号");
			}
		}

		
		try{
			this.sysDataService.updateUserExt(sysUserExt);
			
			webContext.sysUserLog(LogType.UserOpr, "修改用户资料：" + userId);
		}catch(Exception e){
			logger.error("sysUserExtSave", e);
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}
	public boolean isIcbcCard(String icbcCard) {
		try {
			String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo="
					+ icbcCard + "&cardBinCheck=true";
			String str = HttpUtil.simpleGet(url);
			JSONObject json = new JSONObject(str);
			logger.debug("BankCard3:"+json.optBoolean("validated")+"=="+json.optString("bank").equals("ICBC"));
			if(json.optBoolean("validated")&&json.optString("bank").equals("ICBC")) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	
	public List<SysUserEntity> readXls(String file) throws IOException, WebShowException {
		
		InputStream is = new FileInputStream(file);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		SysUserEntity user = null;
		List<SysUserEntity> list = new ArrayList<SysUserEntity>();
		// 循环工作表Sheet, 从第一行开始， 0行为表头
		for (int numSheet = 1; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					user = new SysUserEntity();
					HSSFCell username = hssfRow.getCell(0);
					HSSFCell password = hssfRow.getCell(1);
					if(username == null){
						throw new WebShowException("第"+rowNum+"行用户名为空");
					}
					if(password == null){
						throw new WebShowException("第"+rowNum+"行密码为空");
					}
					user.setUserName(getValue(username));
					user.setPassWord(getValue(password));
					list.add(user);
				}
			}
		}
		return list;
	}
	   
public List<SysUserEntity> readXlls(String filePath) throws IOException, WebShowException {
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String serverRootPath = servletContext.getRealPath("/");
		File file = new File(serverRootPath.concat(filePath));
		InputStream is = new FileInputStream(file);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		SysUserEntity user = null;
		List<SysUserEntity> list = new ArrayList<SysUserEntity>();
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// 循环行Row  从第一行开始， 0行为表头
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					user = new SysUserEntity();
					XSSFCell username = xssfRow.getCell(0);
					XSSFCell password = xssfRow.getCell(1);
					if(username == null && password == null){
						continue;
					}
					if(username == null){
						throw new WebShowException("第"+rowNum+"行用户名为空");
					}
					if(password == null){
						throw new WebShowException("第"+rowNum+"行密码为空");
					}
					user.setUserName(getXsffValue(username));
					user.setPassWord(getXsffValue(password));
					list.add(user);
				}
			}
		}
		return list;
	}
	
	
	@SuppressWarnings("static-access")
	private String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			DecimalFormat  df = new DecimalFormat("0");   
			return df.format(hssfCell.getNumericCellValue());
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	 }
	
	@SuppressWarnings("static-access")
	private String getXsffValue(XSSFCell xssfCell) {
		if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			DecimalFormat  df = new DecimalFormat("0");   
			return df.format(xssfCell.getNumericCellValue());
		} else {
			// 返回字符串类型的值
			return String.valueOf(xssfCell.getStringCellValue());
		}
	 }
	
	/**
	 * @description 获取excel模板
	 * @param fileName
	 * @return
	 */
	private InputStream getJxslTemplate(String fileName){
	    ClassLoader classLoader = getClass().getClassLoader();  
	    String path = "jxsl/template/";
	    InputStream isInputStream = classLoader.getResourceAsStream(path.concat(fileName));
	    return isInputStream;  
	}
	
}
