package com.example.shiro.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.shiro.service.ShiroService;

@RestController
public class ShiroHandlerController {

	
	/**
	 * ����loginҳ��
	 * @return
	 */
	@RequestMapping("/toLogin")
	public ModelAndView login(){
		return new ModelAndView("login");
	}
	
	/**
	 * ����listҳ��
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(){
		return new ModelAndView("list");
	}
	
	/**
	 * ����unauthorizedҳ��
	 * @return
	 */
	@RequestMapping("/unauthorized")
	public ModelAndView unauthorized(){
		return new ModelAndView("unauthorized");
	}
	
	/**
	 * ��¼
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam("username")String username,@RequestParam("password")String password){

		//1.��ȡ��ǰ��Subject
		Subject subject=SecurityUtils.getSubject();
		//2.�жϵ�ǰ���û��Ƿ��Ѿ�����֤�����Ƿ��Ѿ���¼
		if(!subject.isAuthenticated()){
			//3.��û�б���֤�����username��password��װ��UsernamePasswordToken����
			UsernamePasswordToken token=new UsernamePasswordToken(username, password);
			//���ü�ס��
			token.setRememberMe(true);
			//4.ִ�е�¼
			try {
				subject.login(token);
				System.out.println("��¼�ɹ�");
			} 
			//������֤ʱ�쳣�ĸ���
			catch (AuthenticationException e) {
				System.out.println("��¼ʧ�ܣ�"+e.getMessage());
				return new ModelAndView("login");
			}
		}
		return new ModelAndView("list");
	}
	
	/**
	 * ����adminҳ��
	 * @return
	 */
	@RequestMapping("/toAdmin")
	public ModelAndView admin(){
		return new ModelAndView("admin");
	}
	
	/**
	 * ����userҳ��
	 * @return
	 */
	@RequestMapping("/toUser")
	public ModelAndView user(){
		return new ModelAndView("user");
	}
	
	@Autowired
	private ShiroService shiroService;
	
	/**
	 * ���� Ȩ��ע��
	 * @return
	 */
	@RequestMapping("/testShiroAnnotation")
	public ModelAndView testShiroAnnotation(HttpSession session){
		session.setAttribute("key", "123456");
		shiroService.testMethod();
		return new ModelAndView("list");
	}
	
//	/**
//	 * ���� Ȩ��ע��  ����Ч��
//	 * @return
//	 */
//	@RequiresRoles({"admin"})
//	@RequestMapping("/testShiroAnnotation2")
//	public ModelAndView testShiroAnnotation2(){
//		System.out.println("Controller --- testShiroAnnotation2");
//		return new ModelAndView("list");
//	}
}
