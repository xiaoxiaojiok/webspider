
package com.coderxiao.admin.util;

/***
 * 
 * @author XiaoJian
 *
 */
public enum StorageType {
	LocalFile("LocalFile"),
	MongoDB("MongoDB"),
	Mysql("Mysql");
	
	private final String value;
	
	private StorageType(final String value){
		this.value= value;
	}
	
	public String value(){
		return this.value;
	}
	
}
