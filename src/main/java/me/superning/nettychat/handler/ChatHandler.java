package me.superning.nettychat.handler;

import io.netty.channel.Channel;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author superning
 * @Description :处理消息的handler
 * <p>
 * TextWebSocketFrame： 是用于为websocket处理文本的对象
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {



    //一个存放channel的容器
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();

        //获取前端（客户端）传来的消息
        String contenttext = msg.text();
        DataContent dataContent = JsonUtils.jsonToPojo(contenttext, DataContent.class);

        //检查枚举类型
        Integer action = dataContent.getAction();

        if (action.equals(MsgActionEnum.CONNECT.type))
        {

            //用Hashmap把channel和userId 关联起来
            Long senderId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.put(senderId,channel);


        }
        else if (action.equals(MsgActionEnum.CHAT.type))
        {
            ChatMsg chatMsg = dataContent.getChatMsg();



        }
        else if (action.equals(MsgActionEnum.SIGNED.type))
        {

        }
        else if (action.equals(MsgActionEnum.KEEPALIVE.type))
        {

        }
        else if (action.equals(MsgActionEnum.PULL_FRIEND.type))
        {

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
        users.remove(ctx.channel());




    }
}
