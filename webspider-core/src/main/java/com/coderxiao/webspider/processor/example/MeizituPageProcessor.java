package com.coderxiao.webspider.processor.example;

import java.util.List;

import com.coderxiao.webspider.Page;
import com.coderxiao.webspider.Site;
import com.coderxiao.webspider.Spider;
import com.coderxiao.webspider.pipeline.ImagePipeline;
import com.coderxiao.webspider.processor.PageProcessor;

public class MeizituPageProcessor implements PageProcessor{
	
	private Site site = Site.me().setRetryTimes(5).setSleepTime(0).setCycleRetryTimes(3).setCharset("GBK");
	
	private String imgShortNameNew = "(http://pic.meizitu.com/wp-content/uploads/)|(jpg)";
	private String urlPattern = "http://www.meizitu.com/[a-z]/[0-9]{1,4}.html";
	private String imgRegex = "http://pic.meizitu.com/wp-content/uploads/20[0-9]{2}[a-z]/[0-9]{1,4}/[0-9]{1,4}/[0-9]{1,4}.jpg";
	private String titleReplace = "[|\\pP‘’“”\\s(妹子图)]";
	
	public MeizituPageProcessor() {
		
	}
	
	@Override
	public void process(Page page) {

        List<String> requests = page.getHtml().links().regex(urlPattern).all();
        page.addTargetRequests(requests);
        String imgHostFileName = page.getHtml().xpath("//title/text()").toString().replaceAll(titleReplace, "");
        List<String> listProcess = page.getHtml().$("div#picture").regex(imgRegex).all();
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
		Spider.create(new MeizituPageProcessor())
		.setDestroyWhenExit(true)
		.addPipeline(new ImagePipeline("F:\\webmagic\\"))
		.addUrl("http://www.meizitu.com/a/4887.html")
		.thread(15)
		.start();
	}
	
	

}
