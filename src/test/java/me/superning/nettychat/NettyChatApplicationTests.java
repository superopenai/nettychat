package me.superning.nettychat;

import com.alibaba.fastjson.JSON;
import com.google.zxing.WriterException;
import io.netty.handler.timeout.IdleStateEvent;
import me.superning.nettychat.Controller.LoginController;
import me.superning.nettychat.domain.ChatMsg;
import me.superning.nettychat.service.ChatMsgService;
import me.superning.nettychat.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class NettyChatApplicationTests {

    @Autowired
    UserService service;
    @Autowired
    ChatMsgService chatMsgService;

    @Autowired
    LoginController loginController;


    @Test
    void reg() {
        service.registUser("茜茜", "宝贝");


    }

    @Test
    void image() throws IOException, WriterException {

        Long id = service.registUser("刘希宁", "134556673843").getId();

        service.convertBlob(id);

    }

    @Test
    void request() {
        service.sendFriendRequest(2, "刘希宁");
    }


    @Test
    void addNewFriend() {
        Long my = 3L;
        Long friend = 2L;
        Integer op = 1;

        loginController.listFriendList(2L, 1L, op);

    }

    @Test
    void myFriendList() {

        System.out.println(loginController.myFriendsList(2L).getData());


    }

    @Test
    void msgSelectAndUpdate() {
        chatMsgService.oneMsgSelectAndUpdate(3L, 1L);
    }

    @Test
    void getUnReadMsg() {

        Object ob = new Object();
        if (ob instanceof IdleStateEvent)
        {
            System.out.println("fuck yeh");
        }
    }
}
