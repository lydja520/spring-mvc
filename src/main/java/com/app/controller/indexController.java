package com.app.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.app.aop.LoginCheck;
import com.app.aop.SystemControllerLog;
import com.app.exception.SystemException;
import com.app.model.TUser;
import com.app.redis.SerializeUtil;
import com.app.service.log.LogInte;
import com.app.util.BaseController;
import com.app.util.CookieUtils;

@Controller
@RequestMapping(value="indexcon")
public class indexController extends BaseController{
	private Logger logger = Logger.getLogger(indexController.class);

	@Autowired 
	LogInte loginteimpl;
	@RequestMapping(value = "index")
	@SystemControllerLog(description = "进入登录页面")
	public ModelAndView index(Model model,HttpServletRequest request) throws SystemException {
		String name = "";
		String pass = ""; 
		HashMap<String,Object> map=null;
		try {
			map = new HashMap<String, Object>();
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("liangyiuserName")) {
						name = cookies[i].getValue();
						map.put("username", CookieUtils.decodeBase64(name));
					}
					if (cookies[i].getName().equals("liangyipassword")) {
						pass = cookies[i].getValue();
						map.put("passw", CookieUtils.decodeBase64(pass));
					}
				}
			} 
		} catch (Exception e) {
		    throw new SystemException("系统异常");
		}
	return new ModelAndView("index",map);
	}
	@LoginCheck(description=true)
	@RequestMapping(value = "main")
	@SystemControllerLog(description = "进入main页面")
	public String main(Model model,TUser tus,HttpServletRequest request) {
		//TUser redisuser = (TUser) SerializeUtil.unserialize(this.get(this.findcookie(request).getBytes()));
		TUser redisuser = (TUser) getWebUserAttribute("user");
		model.addAttribute("user", redisuser);
		
		return "main";
	}
	@LoginCheck(description=true)
	@RequestMapping(value = "pvnvjs")
	@SystemControllerLog(description = "浏览量统计")
	public void pvjisuan(Model model,TUser tus,HttpServletRequest request,HttpServletResponse response) {
		
		try {			
			//String json="{\"pv\":"+this.get("pv")+",\"nv\":"+this.get("uv")+"}";
			String json="{\"pv\":"+1+",\"nv\":"+2+"}";
			response_write(json, response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
