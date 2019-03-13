import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eshanren.service.IDingDingService;
import com.eshanren.service.impl.DingDingServiceImpl;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/13.
 */

public class test3 {

    IDingDingService dd = new DingDingServiceImpl();
    //被@人的手机号集

    @Test
    public void robotById(){
        //从机器人的id获取机器人信息
        dd.getRobotById("2");

    }
    @Test
    public void robotByGroupName(){
        //根据name模糊查询机器人.
        dd.searchRobotsByGroupName("om");
    }
    @Test
    public void textMsg () throws IOException {
        List<String> a= new ArrayList<>();
        a.add("15726957891");
        a.add("12312321321");
        dd.pushTextMsg("2","hello",a,false);
    }

    @Test
    public void linkMsg() throws IOException{

        dd.pushLinkMsg("2","你好","你好不好啊jfkafklsshad hdkjh ask萨克回答说跨世纪的快乐啊就是肯德基阿萨科大就啊卡沙卡仕达酱打飞机阿娇快递费急啊方可实施单独 jdas kjfaskd jklajsfk ldsajsj skskfkls fsd","http://pic9.nipic.com/20100824/2531170_082435310724_2.jpg","https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI");

    }

    @Test
    public void markdownMsg() throws  IOException{
        List<String> a= new ArrayList<>();
        a.add("15726957891");
        a.add("12312321321");
        dd.pushMarkdownMsg("2","你们好","大家好啊@15726957891",a,false);
    }

    @Test
    public void actionCardMsg() throws IOException{
        dd.pushActionCardMsg("2","标题","![screenshot](http://pic9.nipic.com/20100824/2531170_082435310724_2.jpg) \n" +
                " ### 乔布斯 20 年前想打造的苹果咖啡厅 \n" ,"哈哈","https://www.dingtalk.com/","0","0");
    }

    @Test
    public void actionCardMsg2() throws IOException{
        JSONObject jb;
        JSONArray ja = new JSONArray();
        jb = new JSONObject();
        jb.put("title","内容不错");
        jb.put("actionURL","https://www.dingtalk.com");
        ja.add(jb);

        jb = new JSONObject();
        jb.put("title","乐色");
        jb.put("actionURL","https://www.baidu.com");
        ja.add(jb);

        // ActionCard独立跳转发送消息
        dd.pushActionCardMsg("2","标题","哈哈哈哈",ja,"1","1");
    }

    @Test
    public void feedCardMsg() throws IOException{
        JSONArray j = new JSONArray();
        JSONObject jb ;
        jb = new JSONObject();
        jb.put("title","标题");
        jb.put("messageURL","https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI");
        jb.put("picURL","http://pic1.nipic.com/2008-12-30/200812308231244_2.jpg");
        j.add(jb);

        jb = new JSONObject();
        jb.put("title","标题2222");
        jb.put("messageURL","https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI");
        jb.put("picURL","图片2");
        j.add(jb);
        System.out.println(j);
        //FeedCard方式发送消息
        dd.pushFeedCardMsg("2",j);
    }
}
