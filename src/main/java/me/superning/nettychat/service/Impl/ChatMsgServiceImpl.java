package me.superning.nettychat.service.Impl;

import lombok.SneakyThrows;
import me.superning.nettychat.mapper.ChatMsgMapper;
import me.superning.nettychat.netty.ChatMsg;
import me.superning.nettychat.utils.SHAUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import me.superning.nettychat.service.ChatMsgService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ChatMsgServiceImpl implements ChatMsgService{

    @Resource
    private ChatMsgMapper chatMsgMapper;

    /**
     *
     * @param chatMsg
     *          一个中间对象
     *  将信息存入数据库里。
     * @return
     */
    @SneakyThrows
    @Override
    public Long saveMsg(ChatMsg chatMsg) {

        me.superning.nettychat.domain.ChatMsg chatMsginDB = new me.superning.nettychat.domain.ChatMsg();
        chatMsginDB.setSendUserId(chatMsg.getSenderId());
        chatMsginDB.setAcceptUserId(chatMsg.getReceieverId());
        chatMsginDB.setMessage(SHAUtils.getSHA512(chatMsg.getMsg()));
        chatMsgMapper.insert(chatMsginDB);
        return chatMsginDB.getId();

    }

    @Override
    public void oneMsgSelectAndUpdate(Long receiveId,Long senderId) {
        Example msgExample = new Example(me.superning.nettychat.domain.ChatMsg.class);
        msgExample.createCriteria().andEqualTo("acceptUserId",receiveId).andEqualTo("sendUserId",senderId).andEqualTo("signFlag",0);

        List<me.superning.nettychat.domain.ChatMsg> msgList = chatMsgMapper.selectByExample(msgExample);
        msgList.forEach(chatMsg -> {
            chatMsg.setSignFlag(true);
            chatMsgMapper.updateByPrimaryKey(chatMsg);

        });
    }


}
