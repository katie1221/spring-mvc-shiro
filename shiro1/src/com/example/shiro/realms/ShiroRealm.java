package com.example.shiro.realms;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * AuthorizingRealm类继承自AuthenticatingRealm，
 * 但没有实现AuthenticatingRealm中的doGetAuthenticationInfo方法，
 * 所以认证和授权只需要继承AuthorizingRealm就可以了，同时需要实现它的两个抽象方法。
 * @author Administrator
 *
 */
public class ShiroRealm extends AuthorizingRealm{

	/**
	 * 认证 
	 * 登陆的时候，会调用这个
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("[FirstRealm] doGetAuthenticationInfo:"+token);
		//1.把AuthenticationToken 转换称 UsernamePasswordToken
		UsernamePasswordToken upToken =(UsernamePasswordToken) token;
		//2.从UsernamePasswordToken中获取用户名
		String username=upToken.getUsername();
		//3.调用数据库方法，从数据库中查询username对应的用户记录
		System.out.println("从数据库中获取username："+username+"对应的用户信息");
		//4.判断用户是否存在，若不存在，则抛出UnknownAccountException异常
		if("unknown".equals(username)){
			throw new UnknownAccountException("用户不存在！");
		}
		//5.根据用户信息的情况，决定是否需求抛出其他的AutheticationException异常。如用户锁定等
		if("monster".equals(username)){
			throw new LockedAccountException("用户被锁定！");
		}
		//6.根据用户的情况，来构建AuthenticationInfo对像并返回，通常使用的实现类是SimpleAuthenticationInfo
		//以下信息是从数据库获取的
		//1).principal:认证的实体类信息。可以是username，也可以是数据表对应的用户的体类对象
		Object principal=username;
		//2).credentials:密码（数据库获取的用户的密码）
		Object credentials=null;//"fc1709d0a95a6be30bc5926fdb7f22f4";
		if(username.equals("admin")){
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";//由下面的main方法对应不同的盐值得到
		}else if(username.equals("user")){
			credentials = "098d2c478e9c11555ce2823231e02ec1";
		}
		//3).realmName：当前realm对象的name，调用父类的getName()方法即可
		String realmName=getName();
		//4).盐值
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);//盐值 要唯一
		
		//SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(principal, credentials, realmName);
		SimpleAuthenticationInfo info= new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;
	}

    /**
     * 授权
     * 授权会被shiro回调的方法
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("doGetAuthorizationInfo---");
		//1. 从 PrincipalCollection 中来获取登录用户的信息
		Object principal = principals.getPrimaryPrincipal();
		//2. 利用登录用户的信息来获取当前用户的角色或权限（可能需要查询数据库）
		Set<String> roles= new HashSet<String>();
		roles.add("user");//每个用户都有user这个角色
		if("admin".equals(principal)){
			roles.add("admin");//admin用户还有admin角色
		}
		//3. 创建 SimpleAuthorizationInfo，并设置其 roles 属性
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roles);
		//4. 返回 SimpleAuthorizationInfo 对象
		return simpleAuthorizationInfo;
	}
	
	
	public static void main(String[] args) {
		String hashAlgorithmName="MD5";//加密算法（与配置文件中的一致）
		String credentials="123456";//密码
		ByteSource salt=ByteSource.Util.bytes("user");//盐值
		int hashIterations=1024;//加密次数（与配置文件中的一致）
		System.out.println(new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations));
	}

}
