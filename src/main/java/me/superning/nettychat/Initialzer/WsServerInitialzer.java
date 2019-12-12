package me.superning.nettychat.Initialzer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import me.superning.nettychat.handler.ChatHandler;

/**
 * @author superning
 */
public class WsServerInitialzer  extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        //websocket基于Http协议
        pipeline.addLast(new HttpServerCodec());
                            // 用于支持大数据流
        pipeline.addLast(new ChunkedWriteHandler());
                            //对httpMessage进行聚合，聚合成FullHttpRequest或者FullResponse
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
                            //自定义handler
        pipeline.addLast(new ChatHandler());




    }
}
