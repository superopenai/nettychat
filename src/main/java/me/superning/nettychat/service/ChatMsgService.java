package me.superning.nettychat.service;

import me.superning.nettychat.netty.ChatMsg;

import javax.validation.constraints.Max;
import java.util.List;

public interface ChatMsgService{



    Long saveMsg(ChatMsg chatMsg);
    void oneMsgSelectAndUpdate(Long receieveId,Long senderId);

}
