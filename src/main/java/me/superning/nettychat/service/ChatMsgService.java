package me.superning.nettychat.service;

import me.superning.nettychat.netty.ChatMsg;

import javax.validation.constraints.Max;
import java.util.List;

public interface ChatMsgService{


    /**
     *
     * @param chatMsg
     *         通过传输的ChatMsg，提取出我们需要的msg保存到数据库中
     * @return
     *         返回每个msg的id
     */
    Long saveMsg(ChatMsg chatMsg);

    /**
     *  签收消息，把数据库中的符合条件的未签收消息进行签收
     * @param receieveId
     *          接收者id
     * @param senderId
     *          发送者id
     */
    void oneMsgSelectAndUpdate(Long receieveId,Long senderId);

    /**
     * 获得 “我” 所有没签收的消息@
     * @param receieveId
     *
     * @return
     */
    List<me.superning.nettychat.domain.ChatMsg> getUnReadMsg(Long receieveId);


}
