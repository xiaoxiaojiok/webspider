package com.coderxiao.webspider.pipeline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coderxiao.webspider.ResultItems;
import com.coderxiao.webspider.Task;
import com.coderxiao.webspider.util.FilePersistentBase;

/**
 * 
 * 图片下载PipeLine
 * 
 * @author XiaoJian
 *
 */
public class ImagePipeline extends FilePersistentBase implements Pipeline {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public ImagePipeline() {
		setPath("/data/webmagic/");
	}

	/**
	 * @param path 保存路径
	 */
	public ImagePipeline(String path) {
		setPath(path);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void process(ResultItems resultItems, Task task) {
		String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
		try {
			
			String imgShortNameNew =resultItems.get("imgShortNameNew");
			CloseableHttpClient httpclient = HttpClients.createDefault();
			List<String> list = (List<String>) resultItems.get("images");
			System.out.println(list);
			for (int i = 1; i < list.size(); i++) {

				StringBuffer sb = new StringBuffer();
				StringBuffer imgFileName = sb.append(path)
						.append(list.get(0)) // 此处提取文件夹名，即之前采集的标题名
						.append(PATH_SEPERATOR);
				// 这里先判断文件夹名是否存在，不存在则建立相应文件夹
				getFile(imgFileName.toString());
				System.out.println(imgFileName.toString());
				String extName = com.google.common.io.Files
						.getFileExtension(list.get(i));
				StringBuffer imgFileNameNew = imgFileName
						.append((list.get(i)).replaceAll(imgShortNameNew, "")
								.replaceAll("[\\pP‘’“”]", "")).append(".")
						.append(extName);

				// 这里通过httpclient下载之前抓取到的图片网址，并放在对应的文件中
				HttpGet httpget = new HttpGet(list.get(i));
				HttpResponse response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				InputStream in = entity.getContent();

				File file = new File(imgFileNameNew.toString());

				try {
					FileOutputStream fout = new FileOutputStream(file);
					int l = -1;
					byte[] tmp = new byte[1024];
					while ((l = in.read(tmp)) != -1) {
						fout.write(tmp, 0, l);
					}
					fout.flush();
					fout.close();
				} finally {

					in.close();
				}

			}
			httpclient.close();
		} catch (Exception e) {
			logger.warn("write file error", e);
		}

	}
}


