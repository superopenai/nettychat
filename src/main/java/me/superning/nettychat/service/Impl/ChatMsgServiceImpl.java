package me.superning.nettychat.service.Impl;

import lombok.SneakyThrows;
import me.superning.nettychat.mapper.ChatMsgMapper;
import me.superning.nettychat.netty.ChatMsg;
import me.superning.nettychat.utils.SHAUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import me.superning.nettychat.service.ChatMsgService;
@Service
public class ChatMsgServiceImpl implements ChatMsgService{

    @Resource
    private ChatMsgMapper chatMsgMapper;

    /**
     *
     * @param chatMsg
     *          一个中间对象
     *  将信息存入数据库里。
     */
    @SneakyThrows
    @Override
    public void saveMsg(ChatMsg chatMsg) {

        me.superning.nettychat.domain.ChatMsg chatMsginDB = new me.superning.nettychat.domain.ChatMsg();
        chatMsginDB.setSendUserId(chatMsg.getSenderId());
        chatMsginDB.setAcceptUserId(chatMsg.getReceieverId());
        chatMsginDB.setMessage(SHAUtils.getSHA512(chatMsg.getMsg()));
        chatMsgMapper.insert(chatMsginDB);



    }
}
