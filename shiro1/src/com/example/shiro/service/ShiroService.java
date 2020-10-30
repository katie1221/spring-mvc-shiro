package com.example.shiro.service;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;


/**
 * 权限注解测试service
 * @author Administrator
 *
 */
public class ShiroService{
   
	//只有admin角色才能访问该方法，其他角色访问将会抛出异常
	@RequiresRoles({"admin"})
	public void testMethod() {
		System.out.println("testMethod() --- testShiroAnnotation");
		//获取session信息
		Session session =SecurityUtils.getSubject().getSession();
		Object val=session.getAttribute("key");
		System.out.println("session val --- "+val);
	}

}
