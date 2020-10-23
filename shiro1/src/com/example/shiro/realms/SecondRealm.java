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
	 * ��֤ 
	 * ��½��ʱ�򣬻�������
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("[SecondRealm] doGetAuthenticationInfo:"+token);
		//1.��AuthenticationToken ת���� UsernamePasswordToken
		UsernamePasswordToken upToken =(UsernamePasswordToken) token;
		//2.��UsernamePasswordToken�л�ȡ�û���
		String username=upToken.getUsername();
		//3.�������ݿⷽ���������ݿ��в�ѯusername��Ӧ���û���¼
		System.out.println("�����ݿ��л�ȡusername��"+username+"��Ӧ���û���Ϣ");
		//4.�ж��û��Ƿ���ڣ��������ڣ����׳�UnknownAccountException�쳣
		if("unknown".equals(username)){
			throw new UnknownAccountException("�û������ڣ�");
		}
		//5.�����û���Ϣ������������Ƿ������׳�������AutheticationException�쳣�����û�������
		if("monster".equals(username)){
			throw new LockedAccountException("�û���������");
		}
		//6.�����û��������������AuthenticationInfo���񲢷��أ�ͨ��ʹ�õ�ʵ������SimpleAuthenticationInfo
		//������Ϣ�Ǵ����ݿ��ȡ��
		//1).principal:��֤��ʵ������Ϣ��������username��Ҳ���������ݱ��Ӧ���û����������
		Object principal=username;
		//2).credentials:���루���ݿ��ȡ���û������룩
		Object credentials=null;//"fc1709d0a95a6be30bc5926fdb7f22f4";
		if(username.equals("admin")){
			credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06--";//�������main������Ӧ��ͬ����ֵ�õ�
		}else if(username.equals("user")){
			credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718--";
		}
		//3).realmName����ǰrealm�����name�����ø����getName()��������
		String realmName=getName();
		//4).��ֵ
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);//��ֵ ҪΨһ
		
		//SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(principal, credentials, realmName);
		SimpleAuthenticationInfo info= new SimpleAuthenticationInfo("SecondRealmName", credentials, credentialsSalt, realmName);
		return info;
	}


	public static void main(String[] args) {
		String hashAlgorithmName="SHA1";//�����㷨���������ļ��е�һ�£�
		String credentials="123456";//����
		ByteSource salt=ByteSource.Util.bytes("admin");//��ֵ
		int hashIterations=1024;//���ܴ������������ļ��е�һ�£�
		System.out.println(new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations));
	}

}
