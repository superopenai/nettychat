package me.superning.nettychat.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @author superning
 * @Description :处理消息的handler
 *
 * TextWebSocketFrame： 是用于为websocket处理文本的对象
 *
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //一个存放channel的容器
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        String contenttext = msg.text();
        System.out.println("server接收到的数据"+ contenttext);
        clients.forEach(channel -> {


            channel.writeAndFlush(new TextWebSocketFrame(
                    "[服务器在]"+ LocalDateTime.now()
                    +"消息为"+contenttext));
        });




    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {


        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
          //这个语句是多余的，先注释了
        // 源码解释：A closed Channel is automatically removed from the collection
        //  so that you don't need to worry about the life cycle of the added Channel.
//        clients.remove(ctx.channel());
        System.out.println("长id："+ctx.channel().id().asLongText());
        System.out.println("短id: "+ctx.channel().id().asShortText());


    }
}
