package com.eshanren.service;

import com.alibaba.fastjson.JSONArray;
import com.eshanren.dto.RespRet;

import java.io.IOException;
import java.util.List;

/**
 * 钉钉业务层接口
 *
 * @author WWF
 */
public interface IDingDingService {


    /**
     * 获取所有钉钉机器人信息
     *
     * @return
     */
    public RespRet getAllRobots();


    /**
     * 通过机器人id 获取到机器人信息
     *
     * @param id 机器人id
     * @return
     */
    public RespRet getRobotById(String id);

    /**
     * 通过 群名进行模糊检索
     *
     * @param name
     * @return
     */
    public RespRet searchRobotsByGroupName(String name);


    /**
     * 推送 text 消息，支持at 动作
     *
     * @param id        机器人id
     * @param content   内容
     * @param atMobiles 被@人的手机号(在content里添加@人的手机号)，可为空
     * @param isAtAll   @所有人时：true，否则为：false
     * @return
     */
    public RespRet pushTextMsg(String id, String content, List<String> atMobiles, Boolean isAtAll) throws IOException;

    /**
     * 推送 link 消息
     *
     * @param id         机器人id
     * @param title      消息标题
     * @param text       消息内容。控制长度，自测 一下可以多长
     * @param messageUrl 点击消息跳转的URL
     * @param picUrl     图片URL，可为空
     * @return
     */
    public RespRet pushLinkMsg(String id, String title, String text, String messageUrl, String picUrl) throws IOException;


    /**
     * 推送 markdown消息
     *
     * @param id        机器人id
     * @param title     首屏会话透出的展示内容
     * @param text      markdown格式的消息
     * @param atMobiles 被@人的手机号(在text内容里要有@手机号),可空
     * @param isAtAll   @所有人时：true，否则为：false
     * @return
     */
    public RespRet pushMarkdownMsg(String id, String title, String text, List<String> atMobiles, Boolean isAtAll) throws IOException;


    /**
     * 推送整体跳转ActionCard 消息
     *
     * @param id             机器人id
     * @param title          首屏会话透出的展示内容
     * @param text           markdown格式的消息
     * @param singleTitle    单个按钮的方案。(设置此项和singleURL后btns无效)
     * @param singleURL      点击singleTitle按钮触发的URL
     * @param btnOrientation 0-按钮竖直排列，1-按钮横向排列
     * @param hideAvatar     0-正常发消息者头像，1-隐藏发消息者头像
     * @return
     */
    public RespRet pushActionCardMsg(String id, String title, String text, String singleTitle, String singleURL, String btnOrientation, String hideAvatar) throws IOException;


    /**
     * 推送 独立跳转ActionCard 消息
     *
     * @param id             机器人id
     * @param title          首屏会话透出的展示内容
     * @param text           markdown格式的消息
     * @param btns           按钮的信息：title-按钮方案，actionURL-点击按钮触发的URL
     * @param btnOrientation 0-按钮竖直排列，1-按钮横向排列
     * @param hideAvatar     0-正常发消息者头像，1-隐藏发消息者头像
     * @return
     */
    public RespRet pushActionCardMsg(String id, String title, String text, JSONArray btns, String btnOrientation, String hideAvatar) throws IOException;


    /**
     * 推送 FeedCard 消息
     *
     * @param id    机器人id
     * @param links title-单条信息文本，messageURL-点击单条信息到跳转链接，picURL-单条信息后面图片的URL
     * @return
     */
    public RespRet pushFeedCardMsg(String id, JSONArray links) throws IOException;


}
