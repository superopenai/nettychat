package me.superning.nettychat.service.Impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.superning.nettychat.service.WsServer;
import me.superning.nettychat.Initialzer.WsServerInitialzer;
import org.springframework.stereotype.Service;

/**
 * @author superning
 */
@Service
public class WsServerImpl implements WsServer {
    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap serverBootstrap;

    private static class SingletionWsServer {
        static final WsServerImpl isntance = new WsServerImpl();

    }

    public static WsServerImpl getInstance() {
        return SingletionWsServer.isntance;

    }

    public WsServerImpl() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(mainGroup, subGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.localAddress("172.16.176.168",33333);
        serverBootstrap.childHandler(new WsServerInitialzer());




    }

    @Override
    public void start() throws InterruptedException {

        ChannelFuture sync = serverBootstrap.bind().sync();
        System.err.println("Server start listen at ");
//            sync.channel().closeFuture().sync();



    }


}
