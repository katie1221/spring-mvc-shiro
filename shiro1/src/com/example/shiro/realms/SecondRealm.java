package com.example.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class SecondRealm extends AuthenticatingRealm{

	/**
	 * 认证 
	 * 登陆的时候，会调用这个
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("[SecondRealm] doGetAuthenticationInfo:"+token);
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
			credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06--";//由下面的main方法对应不同的盐值得到
		}else if(username.equals("user")){
			credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718--";
		}
		//3).realmName：当前realm对象的name，调用父类的getName()方法即可
		String realmName=getName();
		//4).盐值
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);//盐值 要唯一
		
		//SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(principal, credentials, realmName);
		SimpleAuthenticationInfo info= new SimpleAuthenticationInfo("SecondRealmName", credentials, credentialsSalt, realmName);
		return info;
	}


	public static void main(String[] args) {
		String hashAlgorithmName="SHA1";//加密算法（与配置文件中的一致）
		String credentials="123456";//密码
		ByteSource salt=ByteSource.Util.bytes("admin");//盐值
		int hashIterations=1024;//加密次数（与配置文件中的一致）
		System.out.println(new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations));
	}

}
