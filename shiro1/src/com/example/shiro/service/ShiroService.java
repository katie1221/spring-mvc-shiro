package com.example.shiro.service;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;


/**
 * Ȩ��ע�����service
 * @author Administrator
 *
 */
public class ShiroService{
   
	//ֻ��admin��ɫ���ܷ��ʸ÷�����������ɫ���ʽ����׳��쳣
	@RequiresRoles({"admin"})
	public void testMethod() {
		System.out.println("testMethod() --- testShiroAnnotation");
		//��ȡsession��Ϣ
		Session session =SecurityUtils.getSubject().getSession();
		Object val=session.getAttribute("key");
		System.out.println("session val --- "+val);
	}

}
