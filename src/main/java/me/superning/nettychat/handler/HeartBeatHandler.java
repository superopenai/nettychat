package me.superning.nettychat.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import jdk.swing.interop.SwingInterOpUtils;

/**
 * @author superning
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        //evt 是否 为IdleStateEvent（用于触发用户事件，读空闲/写空闲/读写空闲）
        if (evt instanceof IdleStateEvent) {

            IdleStateEvent event = (IdleStateEvent) evt;

            if (event.state().equals(IdleState.READER_IDLE))
            {
                System.out.println("进入读空闲");
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {

                System.out.println("进入写空闲");

            }
            else if (event.state().equals(IdleState.ALL_IDLE)) {
                Channel crashChannel = ctx.channel();
                crashChannel.close();


            }


        }
    }
}
