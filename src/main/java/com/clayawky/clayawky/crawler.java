package com.clayawky.clayawky;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class crawler implements PageProcessor {


    private  static final String[] userAgent={
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1","Mozilla/5.0 (X11; CrOS i686 2268.111.0) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.57 Safari/536.11",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 Safari/536.6",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1090.0 Safari/536.6",
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/19.77.34.5 Safari/537.1",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.9 Safari/536.5",
            "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.0 Safari/536.3",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24",
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24"
};
    List<List<String>> ye=new ArrayList();
    private static String nani="";
    public static String ua(){
     int a= (int) ((Math.random() * 10 )+1);
        return userAgent[a];
    }
    private Site site = Site.me().setUserAgent(ua());
    @Override
    public void process(Page page) {

        System.out.println("????????"+page);
        page.putField("author", page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[1]/div[2]/h1/span/span/span/span/span").all());
        page.putField("video", page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div[4]/div[1]/div[2]/ul").all());
         for (int a=1;a<=12;a++){
             ye.add(page.getHtml().xpath("//*[@id=\"root\"]/div/div[2]/div/div[4]/div[1]/div[2]/ul/li["+a+"]/a").all());
         }
        if (page.getResultItems().get("video").toString().equals("[]")) {
            page.setDownloadSuccess(false);
            System.out.println("--------------------------------------------------fuck-------------------------------------------------------");
      return;
        }

        Iterator  i = ye.listIterator();
        while (i.hasNext()){
           String test=  i.next().toString();
            nani+=test.substring( test.indexOf("href=")+1, test.indexOf("class"));
        }
    }
        @Override
    public Site getSite() {
        return site;
    }
    public static String   GithubRepoPageProcessor1(String url) {
        System.out.println("目标地址-----"+url);
        nani="";
        System.out.println(userAgent[(int)(Math.random() * 10 )+1]);
        try {
            Spider.create(new crawler()).addUrl(url).thread(5).run();
        }catch (Exception e){
            e.printStackTrace();
        }
            return "";


    }

    public static String getData(){
        if(nani.equals("")){
            return "请求失败，请重试!";
        }else{
            return  nani;
        }
    }
    //懒汉设计模式
//    private GithubRepoPageProcessor(){}
//    private  static GithubRepoPageProczessor instance =null;
//    public static  GithubRepoPageProcessor newInstance(String url){
//        if(instance==null){
//            instance=new  GithubRepoPageProcessor(url);
//            return instance;
//        }
//        return instance;
//    }
}


