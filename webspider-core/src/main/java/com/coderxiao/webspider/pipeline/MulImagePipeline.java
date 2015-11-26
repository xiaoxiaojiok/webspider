package com.coderxiao.webspider.pipeline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coderxiao.webspider.ResultItems;
import com.coderxiao.webspider.Task;
import com.coderxiao.webspider.thread.CountableThreadPool;
import com.coderxiao.webspider.util.FilePersistentBase;

/**
 * 
 * 图片下载PipeLine
 * 
 * 试用
 * 
 * @author XiaoJian
 *
 */
public class MulImagePipeline extends FilePersistentBase implements Pipeline {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final int DEFAULT_POOL_SIZE = 3;
	
	public int poolSize;
	
	public MulImagePipeline() {
		this.poolSize = DEFAULT_POOL_SIZE;
		setPath("/data/webmagic/");
	}

	/**
	 * @param path 保存路径
	 */
	public MulImagePipeline(String path) {
		this.poolSize = DEFAULT_POOL_SIZE;
		setPath(path);
	}
	
	/**
	 * @param size 线程池大小
	 */
	public MulImagePipeline(int poolSize) {
		this.poolSize = poolSize;
		setPath("/data/webmagic/");
	}
	
	/**
	 * @param path 保存路径
	 * @param size 线程池大小
	 */
	public MulImagePipeline(String path,int poolSize) {
		this.poolSize = poolSize;
		setPath(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void process(ResultItems resultItems, Task task) {
		CountableThreadPool threadPool = new CountableThreadPool(poolSize);
		String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
		String imgShortNameNew =resultItems.get("imgShortNameNew");
		List<String> list = (List<String>) resultItems.get("images");
		if(list.size() < 2 ){
			return;
		}
		CountDownLatch latch=new CountDownLatch(list.size() - 1);
		for (int i = 1; i < list.size(); i++) {
			StringBuffer sb = new StringBuffer();
			StringBuffer imgFileName = sb.append(path)
					.append(list.get(0)) // 此处提取文件夹名，即之前采集的标题名
					.append(PATH_SEPERATOR);
			// 这里先判断文件夹名是否存在，不存在则建立相应文件夹
			getFile(imgFileName.toString());
			String extName = com.google.common.io.Files
					.getFileExtension(list.get(i));
			StringBuffer imgFileNameNew = imgFileName
					.append((list.get(i)).replaceAll(imgShortNameNew, "")
							.replaceAll("[\\pP‘’“”]", "")).append(".")
					.append(extName);
			threadPool.execute(new ImageThread(latch,imgFileNameNew.toString(),list.get(i),logger));
		}
		while(true){
			//轮询
			if(threadPool.getThreadAlive() == 0){
				threadPool.shutdown();
				break;
			}
		}
	}
}

class ImageThread extends Thread{
	
	private CountDownLatch latch;  
	
	private String imgFileNameNew;
	
	private String url;
	
	private Logger logger;
	
	public ImageThread(CountDownLatch latch, String imgFileNameNew,String url, Logger logger){
		this.latch = latch;
		this.imgFileNameNew = imgFileNameNew;
		this.url = url;
		this.logger = logger;
	}
	
	@Override
	public void run(){
		// 这里通过httpclient下载之前抓取到的图片网址，并放在对应的文件中
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();

			File file = new File(imgFileNameNew);
			FileOutputStream fout = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp, 0, l);
			}
			fout.flush();
			fout.close();
			in.close();
			httpclient.close();
		}catch(Exception e){
			logger.warn("write file error", e);
		}finally{
			 latch.countDown();//计数器减一  
		}
	}
}
