package com.coderxiao.webspider.processor.test;

import java.util.List;

import com.coderxiao.webspider.Page;
import com.coderxiao.webspider.Site;
import com.coderxiao.webspider.Spider;
import com.coderxiao.webspider.pipeline.ImagePipeline;
import com.coderxiao.webspider.processor.PageProcessor;

public class BaiduTieBaPageProcessor implements PageProcessor{
	
	private Site site = Site.me().setRetryTimes(5).setSleepTime(0).setCycleRetryTimes(3).setCharset("UTF-8");
	
	private String imgShortNameNew = "(http://pic.meizitu.com/wp-content/uploads/)|(jpg)";
	private String imgRegex = "http://[.a-zA-Z0-9/]*.gif";
	
	public BaiduTieBaPageProcessor() {
		
	}
	
	@Override
	public void process(Page page) {
//		System.out.println(page);
        String imgHostFileName = page.getHtml().xpath("//title/text()").toString();
        List<String> listProcess = page.getHtml()
        		.regex(imgRegex)
        		.all();
        //此处将标题一并抓取，之后提取出来作为文件名
        listProcess.add(0, imgHostFileName);
        page.putField("images", listProcess);
        page.putField("imgShortNameNew", imgShortNameNew);
	}

	@Override
	public Site getSite() {
		return this.site;
	}
	
	public static void main(String[] args) {
		Spider spider = Spider.create(new BaiduTieBaPageProcessor());
		spider.setDestroyWhenExit(true)
		.addPipeline(new ImagePipeline("F:\\webmagic\\"));
		String urlprefix = "http://tieba.baidu.com/p/165068885?pn=";
		for(int i=1;i<=57;i++){
			spider.addUrl(urlprefix + i);
		}
		spider.thread(15)
		.start();
	}
	
	

}
