
package com.coderxiao.webspider.pipeline;

/***
 * 
 * @author XiaoJian
 *
 */
public enum StorageType {
	LocalFile("LocalFile"),
	Mysql("Mysql");
	
	private final String value;
	
	private StorageType(final String value){
		this.value= value;
	}
	
	public String value(){
		return this.value;
	}
	
}
