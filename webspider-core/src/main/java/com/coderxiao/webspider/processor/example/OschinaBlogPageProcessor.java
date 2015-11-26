package com.coderxiao.webspider.processor.example;

import java.util.List;

import com.coderxiao.webspider.Page;
import com.coderxiao.webspider.Site;
import com.coderxiao.webspider.processor.PageProcessor;

/**
 * @author code4crafter@gmail.com <br>
 */
public class OschinaBlogPageProcessor implements PageProcessor {

    private Site site = Site.me().setDomain("my.oschina.net").setRetryTimes(5).setSleepTime(1).setCycleRetryTimes(3);

    @Override
    public void process(Page page) {
        List<String> links = page.getHtml().links().regex("http://my\\.oschina\\.net/flashsword/blog/\\d+").all();
        page.addTargetRequests(links);
        page.putField("title", page.getHtml().xpath("//div[@class='BlogEntity']/div[@class='BlogTitle']/h1/text()").toString());
        if (page.getResultItems().get("title") == null) {
            //skip this page
//            page.setSkip(true);
        }
        page.putField("content", page.getHtml().smartContent().toString());
        page.putField("tags", page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all());
    }

    @Override
    public Site getSite() {
        return site;

    }

    public static void main(String[] args) {
       /* Spider.create(new OschinaBlogPageProcessor())
        .addUrl("http://my.oschina.net/flashsword/blog")
        .thread(5)
        .run();*/
    }
}
