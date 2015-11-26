package com.coderxiao.webspider.pipeline;

import com.coderxiao.webspider.util.ConfigUtilInstance;

/***
 * pipeline factory
 * 
 * @author XiaoJian
 *
 */
public class PipelineFactory {
	
    private static final String PIPELINE_PATH = ConfigUtilInstance.getInstance().getPipeLinePath();
    
	public static Pipeline createPipeline(String type){
		if(type == null){
			return new HTMLPipeline(PIPELINE_PATH);
		}
		if(StorageType.Mysql.value().equals(type)){
			return new MySQLPipeline();
		}
		if(StorageType.LocalFile.value().equals(type)){
			return new HTMLPipeline(PIPELINE_PATH);
		}
		return new HTMLPipeline(PIPELINE_PATH);
	}

}
