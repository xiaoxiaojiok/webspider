package com.coderxiao.webspider.util;

/***
 * 获取全局配置信息</br>
 * 
 * @author XiaoJian
 *
 */
public class ConfigUtilInstance {

	private static class ConfigUtilInstanceGet{
		private static final ConfigUtil instance = new ConfigUtil();
	}
	
	private ConfigUtilInstance(){}
	
	public static ConfigUtil getInstance(){
		return ConfigUtilInstanceGet.instance;
	}

}
