package com.coderxiao.webspider.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coderxiao.webspider.Page;
import com.coderxiao.webspider.Request;
import com.coderxiao.webspider.ResultItems;
import com.coderxiao.webspider.Site;
import com.coderxiao.webspider.util.ConfigUtilInstance;

/***
 * 
 * 通用页面处理类 </br>
 * 
 * @author XiaoJian
 * @since 0.1.0
 *
 */
public class ModelPageProcessor implements PageProcessor {
	
	private static int retryTimes = ConfigUtilInstance.getInstance().getRetryTimes();//重试次数
	private static int sleepTime = ConfigUtilInstance.getInstance().getSleepTime();//睡眠时间
	private static int cycleRetryTimes = ConfigUtilInstance.getInstance().getCycleRetryTimes();//循环重试次数
	private static String regex = ConfigUtilInstance.getInstance().getCharsetRegex();//提取页面编码正则表达式
	
	private Site site = Site.me().setRetryTimes(retryTimes).setSleepTime(sleepTime)
			.setCycleRetryTimes(cycleRetryTimes);

	@Override
	public void process(Page page) {
		page.putField(ResultItems.HTML, page.getHtml().toString());
		Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(page.getRawText());
        if(matcher.find()){
            page.putField(ResultItems.CHARSET, matcher.group(1));
        }
        List<String> list = new ArrayList<String>();
        String[] allowRule = this.site.getAllowRules();
        if(allowRule != null){
        	for(String rule:allowRule){
        		list.addAll(page.getHtml()
        				.links()
        				.regex(rule)
        				.all());
        	}
        }
		for (String url : list) {
			int depth = (Integer) page.getRequest().getExtra(Request.DEPTH);
			depth++;
			page.addTargetRequest(new Request(url).putExtra(Request.DEPTH,
					depth));
		}
	}

	@Override
	public Site getSite() {
		return this.site;
	}
	
}
