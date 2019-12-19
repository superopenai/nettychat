package me.superning.nettychat.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import me.superning.nettychat.enums.MsgActionEnum;
import me.superning.nettychat.netty.ChatMsg;
import me.superning.nettychat.netty.DataContent;
import me.superning.nettychat.netty.UserChannelRel;
import me.superning.nettychat.service.ChatMsgService;
import me.superning.nettychat.utils.JsonUtils;
import me.superning.nettychat.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author superning
 * @Description :处理消息的handler
 * <p>
 * TextWebSocketFrame： 是用于为websocket处理文本的对象
 */
@ChannelHandler.Sharable
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    /**
     * 一个存放channel的容器
     */
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();

        //获取前端（客户端）传来的消息
        String contenttext = msg.text();
        DataContent dataContent = JsonUtils.jsonToPojo(contenttext, DataContent.class);

        //检查枚举类型
        Integer action = dataContent.getAction();

        if (action.equals(MsgActionEnum.CONNECT.type)) {

            //用Hashmap把channel和userId 关联起来
            Long senderId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.put(senderId, channel);


        }
        else if (action.equals(MsgActionEnum.CHAT.type)) {
            //消息发送前，先保存
            ChatMsg chatMsg = dataContent.getChatMsg();
            me.superning.nettychat.service.ChatMsgService chatMsgService = (ChatMsgService) SpringUtil.getBean("ChatMsgServiceImpl");
            Long msgId = chatMsgService.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);
            //消息发送
            Channel receieveChannel = UserChannelRel.get(chatMsg.getReceieverId());
            if (receieveChannel == null) {
                //channel为空代表用户离线。。。
            } else {
                //当channel不为null的时候，

                Channel channelExistOrNot = users.find(receieveChannel.id());
                if (channelExistOrNot == null) {
                    //用户离线
                } else {
                    //用户在线
                    receieveChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(chatMsg)));
                }
            }

        }
        else if (action.equals(MsgActionEnum.SIGNED.type)) {
            //消息签收
            me.superning.nettychat.service.ChatMsgService chatMsgService = (ChatMsgService) SpringUtil.getBean("ChatMsgServiceImpl");
            ChatMsg chatMsg = dataContent.getChatMsg();
            if (chatMsg.getSenderId()!=null&&chatMsg.getReceieverId()!=null&&chatMsg.getMsg()!=null)
            {
                chatMsgService.oneMsgSelectAndUpdate(chatMsg.getReceieverId(),chatMsg.getSenderId());
            }


        }
         else if (action.equals(MsgActionEnum.KEEPALIVE.type)) {

            System.out.println("收到"+"["+channel+"]"+"的连接");


        } else if (action.equals(MsgActionEnum.PULL_FRIEND.type)) {

        }


    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {


        users.add(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        users.remove(ctx.channel());


    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //这个语句是多余的，先注释了
        // 源码解释：A closed Channel is automatically removed from the collection
        //  so that you don't need to worry about the life cycle of the added Channel.
        // 当客户端进程结束的时候，才会判断channel结束了，自动remove
        users.remove(ctx.channel());

        //心跳检查


    }
}
