package com.example.shiro.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {

	/**
	 * 配置FilterChainDefinitionMap
	 * @return
	 */
	public LinkedHashMap<String, String> builderFilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		//下面数据可以保存在数据库，从数据库获取得到
		map.put("/toLogin", "anon");//去登录
		map.put("/login", "anon");//登录
		map.put("/logout", "logout");//登出
		map.put("/toAdmin", "authc,roles[admin]");//进入admin页面(经过认证之后才可访问)
		map.put("/toUser", "authc,roles[user]");//进入user页面(经过认证之后才可访问)
		
		map.put("/list", "user");//user拦截器(经过 记住我登录 或 认证之后 都可以访问)
		
		map.put("/**", "authc");
		return map;
	}
}
