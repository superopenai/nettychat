package me.superning.nettychat;

import me.superning.nettychat.service.WsServer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import org.springframework.stereotype.Service;

/**
 * @author superning
 */
@Service
public class NettyBoot implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private WsServer wsServer;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            try {
                wsServer.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
