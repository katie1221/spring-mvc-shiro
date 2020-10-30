package com.example.shiro.factory;

import java.util.LinkedHashMap;

public class FilterChainDefinitionMapBuilder {

	/**
	 * ����FilterChainDefinitionMap
	 * @return
	 */
	public LinkedHashMap<String, String> builderFilterChainDefinitionMap(){
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		//�������ݿ��Ա��������ݿ⣬�����ݿ��ȡ�õ�
		map.put("/toLogin", "anon");//ȥ��¼
		map.put("/login", "anon");//��¼
		map.put("/logout", "logout");//�ǳ�
		map.put("/toAdmin", "authc,roles[admin]");//����adminҳ��(������֤֮��ſɷ���)
		map.put("/toUser", "authc,roles[user]");//����userҳ��(������֤֮��ſɷ���)
		
		map.put("/list", "user");//user������(���� ��ס�ҵ�¼ �� ��֤֮�� �����Է���)
		
		map.put("/**", "authc");
		return map;
	}
}
