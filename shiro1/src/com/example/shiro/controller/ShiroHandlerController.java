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
	 * 进入login页面
	 * @return
	 */
	@RequestMapping("/toLogin")
	public ModelAndView login(){
		return new ModelAndView("login");
	}
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(){
		return new ModelAndView("list");
	}
	
	/**
	 * 进入unauthorized页面
	 * @return
	 */
	@RequestMapping("/unauthorized")
	public ModelAndView unauthorized(){
		return new ModelAndView("unauthorized");
	}
	
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam("username")String username,@RequestParam("password")String password){

		//1.获取当前的Subject
		Subject subject=SecurityUtils.getSubject();
		//2.判断当前的用户是否已经被认证，即是否已经登录
		if(!subject.isAuthenticated()){
			//3.若没有被认证，则把username和password封装称UsernamePasswordToken对像
			UsernamePasswordToken token=new UsernamePasswordToken(username, password);
			//设置记住我
			token.setRememberMe(true);
			//4.执行登录
			try {
				subject.login(token);
				System.out.println("登录成功");
			} 
			//所有认证时异常的父类
			catch (AuthenticationException e) {
				System.out.println("登录失败："+e.getMessage());
				return new ModelAndView("login");
			}
		}
		return new ModelAndView("list");
	}
	
	/**
	 * 进入admin页面
	 * @return
	 */
	@RequestMapping("/toAdmin")
	public ModelAndView admin(){
		return new ModelAndView("admin");
	}
	
	/**
	 * 进入user页面
	 * @return
	 */
	@RequestMapping("/toUser")
	public ModelAndView user(){
		return new ModelAndView("user");
	}
	
	@Autowired
	private ShiroService shiroService;
	
	/**
	 * 测试 权限注解
	 * @return
	 */
	@RequestMapping("/testShiroAnnotation")
	public ModelAndView testShiroAnnotation(HttpSession session){
		session.setAttribute("key", "123456");
		shiroService.testMethod();
		return new ModelAndView("list");
	}
	
//	/**
//	 * 测试 权限注解  不起效果
//	 * @return
//	 */
//	@RequiresRoles({"admin"})
//	@RequestMapping("/testShiroAnnotation2")
//	public ModelAndView testShiroAnnotation2(){
//		System.out.println("Controller --- testShiroAnnotation2");
//		return new ModelAndView("list");
//	}
}
