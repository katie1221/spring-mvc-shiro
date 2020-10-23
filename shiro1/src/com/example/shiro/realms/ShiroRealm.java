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
 * AuthorizingRealm��̳���AuthenticatingRealm��
 * ��û��ʵ��AuthenticatingRealm�е�doGetAuthenticationInfo������
 * ������֤����Ȩֻ��Ҫ�̳�AuthorizingRealm�Ϳ����ˣ�ͬʱ��Ҫʵ�������������󷽷���
 * @author Administrator
 *
 */
public class ShiroRealm extends AuthorizingRealm{

	/**
	 * ��֤ 
	 * ��½��ʱ�򣬻�������
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("[FirstRealm] doGetAuthenticationInfo:"+token);
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
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";//�������main������Ӧ��ͬ����ֵ�õ�
		}else if(username.equals("user")){
			credentials = "098d2c478e9c11555ce2823231e02ec1";
		}
		//3).realmName����ǰrealm�����name�����ø����getName()��������
		String realmName=getName();
		//4).��ֵ
		ByteSource credentialsSalt = ByteSource.Util.bytes(username);//��ֵ ҪΨһ
		
		//SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(principal, credentials, realmName);
		SimpleAuthenticationInfo info= new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
		return info;
	}

    /**
     * ��Ȩ
     * ��Ȩ�ᱻshiro�ص��ķ���
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("doGetAuthorizationInfo---");
		//1. �� PrincipalCollection ������ȡ��¼�û�����Ϣ
		Object principal = principals.getPrimaryPrincipal();
		//2. ���õ�¼�û�����Ϣ����ȡ��ǰ�û��Ľ�ɫ��Ȩ�ޣ�������Ҫ��ѯ���ݿ⣩
		Set<String> roles= new HashSet<String>();
		roles.add("user");//ÿ���û�����user�����ɫ
		if("admin".equals(principal)){
			roles.add("admin");//admin�û�����admin��ɫ
		}
		//3. ���� SimpleAuthorizationInfo���������� roles ����
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo(roles);
		//4. ���� SimpleAuthorizationInfo ����
		return simpleAuthorizationInfo;
	}
	
	
	public static void main(String[] args) {
		String hashAlgorithmName="MD5";//�����㷨���������ļ��е�һ�£�
		String credentials="123456";//����
		ByteSource salt=ByteSource.Util.bytes("user");//��ֵ
		int hashIterations=1024;//���ܴ������������ļ��е�һ�£�
		System.out.println(new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations));
	}

}
