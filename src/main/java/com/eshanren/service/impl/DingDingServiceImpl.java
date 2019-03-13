package com.eshanren.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.eshanren.dto.RespRet;
import com.eshanren.service.IDingDingService;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WWF
 */

/*
 * 使用HttpClient发送请求、接收响应
 * 1.创建HttpClient对象    HttpClient httpClient = HttpClients.createDefault();
 * 2.创建请求方法的实例并指定请求URL.如果需要发送get请求,创建HttpGet对象;如果需要发送post请求,创建HttpPost对象  Httppost httppost = new Httppost(URL);
 * 3.如果需要发送请求参数,可调用setEntity(HttpEntity entity)方法来设置请求参数.  httppost.addHeader(name,value)   HttpEntity httpEntity = new HttpEntity(参数,charset);
 * 4.调用HttpClient对象的execute(HttpUriRequest request)发送请求,该方法反悔一个HttpResponse.  httppost.setEntity(httpEntity)  HttpResponse httpResponse = httpClient.execute(httppost);
 * 5.调用HttpResponce的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头;调用HttpResponse
 * 的getEntity()方法可获取HttpEntity对象,该对象包装了服务器的响应内容.
 * 6.无论成否,都会释放连接.
 *
 *
 */
public class DingDingServiceImpl implements IDingDingService {


    Prop prop = PropKit.use("data.properties");
    RespRet respRet;
    @Override
    public RespRet getAllRobots() {
        Iterator<String> it = prop.getProperties().stringPropertyNames().iterator();
        List<String> data = new ArrayList<>();
        while(it.hasNext()){
            String key = it.next();
            data.add(prop.get(key));
        }

        if (data.toString().equals("")){
            respRet =  RespRet.fail();
        } else {
           respRet = new RespRet();
           respRet.setData(data);
           respRet.setSuccess(true);
           respRet.setError("null");

        }
        return respRet;
    }

    @Override
    public RespRet getRobotById(String id) {
        respRet = new RespRet();
        Iterator<String> it = prop.getProperties().stringPropertyNames().iterator();
        while(it.hasNext()){
            String key = it.next();
            System.out.println(key);
            if (key.contains(id)) {
                System.out.println(prop.get(key));
                respRet.setMessage(prop.get(key));
            }
        }
        return respRet;
    }

    @Override
    public RespRet searchRobotsByGroupName(String name) {
        Map<String,String> robot = new HashMap<String,String>();
        Iterator<String> it = prop.getProperties().stringPropertyNames().iterator();
        while(it.hasNext()){
            String key = it.next();
            String value = prop.get(key);
            robot.put(key,value);
        }

        Pattern pattern = Pattern.compile(name);
        List results = new ArrayList();

        for(String b : robot.values()){
            Matcher matcher = pattern.matcher(b);
            if (matcher.find()) {
                results.add(b);
            }
        }
        System.out.println(results);

        if (results.toString().equals("")){
            respRet = RespRet.fail();
        } else {
            respRet = new RespRet();
            respRet.setSuccess(true);
            respRet.setData(results);
        }
        return respRet;
    }


    public static void common(String WEBHOOK_TOKEN,String textMsg) throws IOException {
        HttpClient httpClient1 = HttpClients.createDefault();
        HttpPost httppost1 = new HttpPost(WEBHOOK_TOKEN);
        httppost1.addHeader("Content-Type","application/json;charset=utf-8");
        StringEntity se1 = new StringEntity(textMsg,"utf-8");
        httppost1.setEntity(se1);
        HttpResponse response1 = httpClient1.execute(httppost1);
        if (response1.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String result= EntityUtils.toString(response1.getEntity(), "utf-8");
            System.out.println(result);
        }
    }

    @Override
    public RespRet pushTextMsg(String id, String content, List<String> atMobiles, Boolean isAtAll) throws IOException {
        String WEBHOOK_TOKEN = getRobotById(id).getMessage();

//        if (isAtAll) {
//            content = "";
//        }

//        String textMsg = "{\n" +
//                "    \"msgtype\": \"text\", \n" +
//                "    \"text\": {\n" +
//                "        \"content\": \"我就是我, 是不一样的烟火"+content+"\"\n" +
//                "    }, \n" +
//                "    \"at\": {\n" +
//                "        \"atMobiles\": \n" +
//               atMobiles+
//                "        , \n" +
//                "        \"isAtAll\": "+isAtAll+"\n" +
//                "    }\n" +
//                "}";
        Map<String,Object> text1 = new HashMap<>();
        text1.put("msgtype","text");
        Map<String,String> under = new HashMap<>();
        under.put("content",content);
        text1.put("text",under);
        Map<String,Object> under1 = new HashMap<>();
        under1.put("atMobiles",atMobiles);
        under1.put("isAtAll",isAtAll);
        text1.put("at",under1);

        String textMsg = JSON.toJSONString(text1);
        System.out.println(textMsg);

        this.common(WEBHOOK_TOKEN,textMsg);


        respRet = new RespRet();
        respRet.setMessage(textMsg);
        respRet.setSuccess(true);
        return respRet;
    }

    @Override
    public RespRet pushLinkMsg(String id, String title, String text, String messageUrl, String picUrl) throws IOException {
        String WEBHOOK_TOKEN = getRobotById(id).getMessage();

//        String textMsg = "{\n" +
//                "    \"msgtype\": \"link\", \n" +
//                "    \"link\": {\n" +
//                "        \"text\": \"这个即将"+text+"\", \n" +
//                "        \"title\": \"时"+title+"\", \n" +
//                "        \"picUrl\": \""+picUrl+"\", \n" +
//                "        \"messageUrl\": \""+messageUrl+"\"\n" +
//                "    }\n" +
//                "}";
        Map<String,Object> text1 = new HashMap<>();
        text1.put("msgtype","link");
        Map<String,Object> under = new HashMap<>();
        under.put("text",text);
        under.put("title",title);
        under.put("picUrl",picUrl);
        under.put("messageUrl",messageUrl);
        text1.put("link", under);

        String textMsg = JSON.toJSONString(text1);
        System.out.println(textMsg);
        this.common(WEBHOOK_TOKEN,textMsg);

        respRet = new RespRet();
        respRet.setMessage(textMsg);
        respRet.setSuccess(true);
        return respRet;
    }

    @Override
    public RespRet pushMarkdownMsg(String id, String title, String text, List<String> atMobiles, Boolean isAtAll) throws IOException {
            String WEBHOOK_TOKEN = getRobotById(id).getMessage();
            System.out.println(WEBHOOK_TOKEN);

//        if (isAtAll) {
//            text =
//        }
//        String textMsg = "{\n" +
//                "     \"msgtype\": \"markdown\",\n" +
//                "     \"markdown\": {\n" +
//                "         \"title\":\"杭州天气\",\n" +
//                "\"text\":\"####" +title+"  \\n > 9度，@1825718XXXX "+text+"空气良89，相对温度73%\\n\\n > ![screenshot](http://i01.lw.aliimg.com/media/lALPBbCc1ZhJGIvNAkzNBLA_1200_588.png)\\n  > ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \"\n" +
//                "     },\n" +
//                "    \"at\": {\n" +
//                "        \"atMobiles\": \n" +
//                atMobiles+
//                "        , \n" +
//                "        \"isAtAll\": "+isAtAll+"\n" +
//                "    }\n" +
//                " }";

        Map<String,Object> text1 = new HashMap<>();
        text1.put("msgtype","markdown");
        Map<String,Object> under = new HashMap<>();
        under.put("title",title);
        under.put("text",text);
        text1.put("markdown",under);
        Map<String,Object> under1 = new HashMap<>();
        under1.put("atMobiles",atMobiles);
        under1.put("isAtAll",isAtAll);
        text1.put("at",under1);

        String textMsg = JSON.toJSONString(text1);
        System.out.println(textMsg);

        this.common(WEBHOOK_TOKEN,textMsg);


        respRet = new RespRet();
        respRet.setMessage(textMsg);
        respRet.setSuccess(true);
        return respRet;
    }

    @Override
    public RespRet pushActionCardMsg(String id, String title, String text, String singleTitle, String singleURL, String btnOrientation, String hideAvatar) throws IOException {
        String WEBHOOK_TOKEN = getRobotById(id).getMessage();

//        String textMsg = "{\n" +
//                "    \"actionCard\": {\n" +
//                "        \"title\": \"乔布斯 20 年前想打造一间苹果咖啡厅"+title+"\", \n" +
//                "        \"text\": \"![screenshot](http://pic9.nipic.com/20100824/2531170_082435310724_2.jpg) \n" +
//                " ### 乔布斯 20 年前想打造的苹果咖啡厅 \n" +
//                " Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划"+text+"\", \n" +
//                "        \"hideAvatar\": \""+hideAvatar+"\", \n" +
//                "        \"btnOrientation\": \""+btnOrientation+"\", \n" +
//                "        \"singleTitle\" : \"阅读全文"+singleTitle+"\",\n" +
//                "        \"singleURL\" : \"https://"+singleURL+"\"\n" +
//                "    }, \n" +
//                "    \"msgtype\": \"actionCard\"\n" +
//                "}";

        Map<String,Object> text1 = new HashMap<>();
        text1.put("msgtype","actionCard");
        Map<String,Object> under = new HashMap<>();
        under.put("title",title);
        under.put("text",text);
        under.put("hideAvatar",hideAvatar);
        under.put("btnOrientation",btnOrientation);
        under.put("singleTitle",singleTitle);
        under.put("singleURL",singleURL);
        text1.put("actionCard",under);

        String textMsg = JSON.toJSONString(text1);
        System.out.println(textMsg);

        this.common(WEBHOOK_TOKEN,textMsg);

        respRet = new RespRet();
        respRet.setMessage(textMsg);
        respRet.setSuccess(true);
        return respRet;
    }

    @Override
    public RespRet pushActionCardMsg(String id, String title, String text, JSONArray btns, String btnOrientation, String hideAvatar) throws IOException {
        String WEBHOOK_TOKEN = getRobotById("2").getMessage();

//        String textMsg = "{\n" +
//                "    \"actionCard\": {\n" +
//                "        \"title\": \"乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身"+title+"\", \n" +
//                "        \"text\": \"![screenshot](http://pic9.nipic.com/20100824/2531170_082435310724_2.jpg) \n" +
//                " ### 乔布斯 20 年前想打造的苹果咖啡厅 \n" +
//                " Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划"+text+"\", \n" +
//                "        \"hideAvatar\": \""+hideAvatar+"\", \n" +
//                "        \"btnOrientation\": \""+btnOrientation+"\", \n" +
//                "        \"btns\": "+btns+"\n" +
//                "    }, \n" +
//                "    \"msgtype\": \"actionCard\"\n" +
//                "}";

        Map<String,Object> text1 = new HashMap<>();
        text1.put("msgtype","actionCard");
        Map<String,Object> under = new HashMap<>();
        under.put("title",title);
        under.put("text",text);
        under.put("hideAvatar",hideAvatar);
        under.put("btnOrientation",btnOrientation);
        under.put("btns",btns);
        text1.put("actionCard",under);

        String textMsg = JSON.toJSONString(text1);
        System.out.println(textMsg);

        this.common(WEBHOOK_TOKEN,textMsg);


        respRet = new RespRet();
        respRet.setMessage(textMsg);
        respRet.setSuccess(true);
        return respRet;
    }

    @Override
    public RespRet pushFeedCardMsg(String id, JSONArray links) throws IOException {
        String WEBHOOK_TOKEN = getRobotById("2").getMessage();

//        String textMsg = "{\n" +
//                "    \"feedCard\": {\n" +
//                "        \"links\":"+links+"\n"+
//                "    }, \n" +
//                "    \"msgtype\": \"feedCard\"\n" +
//                "}";

        Map<String,Object> text1 = new HashMap<>();
        text1.put("msgtype","feedCard");
        Map<String,Object> under = new HashMap<>();
        under.put("links",links);

        text1.put("feedCard",under);

        String textMsg = JSON.toJSONString(text1);
        System.out.println(textMsg);

        this.common(WEBHOOK_TOKEN,textMsg);

        respRet = new RespRet();
        respRet.setMessage(textMsg);
        respRet.setSuccess(true);
        return respRet;
    }
}
