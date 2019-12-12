package me.superning.nettychat.service.Impl;

import me.superning.nettychat.mapper.ChatMsgMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import me.superning.nettychat.service.ChatMsgService;
@Service
public class ChatMsgServiceImpl implements ChatMsgService{

    @Resource
    private ChatMsgMapper chatMsgMapper;

}
