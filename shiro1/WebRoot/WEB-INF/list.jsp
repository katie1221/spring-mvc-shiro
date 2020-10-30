<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>list</title>
</head>
<body>
  
  <h3>list Page</h3>
  <div>欢迎:<shiro:principal></shiro:principal>登录</div>
  
  <shiro:hasRole name="admin">
     <a href="toAdmin">Admin page</a><br>
  </shiro:hasRole>
  
  <shiro:hasRole name="user">
    <a href="toUser">User page</a><br>
  </shiro:hasRole>
   <a href="testShiroAnnotation">测试 shiro 权限注解</a><br>
   <!-- <a href="testShiroAnnotation2">测试 shiro 权限注解(controller)</a><br> -->
  <a href="logout">logout</a>
</body>
</html>