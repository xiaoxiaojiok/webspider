package com.coderxiao.webspider.pipeline;

import com.coderxiao.webspider.util.ConfigUtil;

/***
 * pipeline factory
 * 
 * @author XiaoJian
 *
 */
public class PipelineFactory {
	
    private static final String PIPELINE_PATH = ConfigUtil.getInstance().getPipeLinePath();
    
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
		if (StorageType.MongoDB.value().equals(type)) {
			return new MongoPipeline();
		}
		return new HTMLPipeline(PIPELINE_PATH);
	}

}
