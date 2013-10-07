package com.easycms.service.impl;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UrlPathHelper;

import com.easycms.base.AbstractBaseDao;
import com.easycms.common.RequestUtils;
import com.easycms.entity.CmsLog;
import com.easycms.entity.CmsUser;
import com.easycms.service.CmsLogService;

@Service
public class CmsLogServiceImpl extends AbstractBaseDao<CmsLog, Integer> implements CmsLogService{
	private static final Logger logger = Logger.getLogger(CmsLogServiceImpl.class);
	@Override
	public void operating(HttpServletRequest request, String title, String content) {
		//String ip = request.getRemoteAddr();
		//反向代理获取ip
		String ip = RequestUtils.getIpAddr(request);
		CmsUser user = (CmsUser) request.getSession().getAttribute("user");
		String username = user.getUsername();
		UrlPathHelper helper = new UrlPathHelper();
		String url =helper.getOriginatingQueryString(request);
		Date date = new Date();
		
		CmsLog log = new CmsLog();
		log.setCategory(CmsLog.ALL_LOG);
		log.setIp(ip);
		log.setUsername(username);
		log.setUrl(url);
		log.setTime(date);
		log.setTitle(title);
		log.setContent(content);
		
		save(log);
	}

	@Override
	public void loginSucssessLog(HttpServletRequest request, String title) {
		//反向代理获取ip
		String ip = RequestUtils.getIpAddr(request);
		CmsUser user = (CmsUser) request.getSession().getAttribute("user");
		String username = user.getUsername();
		Date date = new Date();
		CmsLog log = new CmsLog();
		log.setCategory(CmsLog.LOGIN_SUCCESS_LOG);
		log.setIp(ip);
		log.setUsername(username);
		log.setTime(date);
		log.setTitle(title);
		
		save(log);
	}

	@Override
	public void loginFailureLog(HttpServletRequest request, String title,
			String content) {
		//反向代理获取ip
		String ip = RequestUtils.getIpAddr(request);
		UrlPathHelper helper = new UrlPathHelper();
		String url =helper.getOriginatingQueryString(request);
		Date date = new Date();
		
		CmsLog log = new CmsLog();
		log.setCategory(CmsLog.LOGIN_FAILURE_LOG);
		log.setIp(ip);
		log.setUrl(url);
		log.setTime(date);
		log.setTitle(title);
		log.setContent(content);
		
		save(log);
	}

}
