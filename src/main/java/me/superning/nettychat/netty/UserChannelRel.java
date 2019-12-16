package me.superning.nettychat.netty;


import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * @author superning
 */
public class UserChannelRel {


        private static HashMap<Long, Channel> manager  = new HashMap<>();

        public static void put(Long senderId, io.netty.channel.Channel channel)
        {
            manager.put(senderId,channel);
        }
        public static Channel get(Long senderId)
        {
            return manager.get(senderId);
        }
}
