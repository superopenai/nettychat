package me.superning.nettychat;

import com.google.zxing.WriterException;
import me.superning.nettychat.Controller.LoginController;
import me.superning.nettychat.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class NettyChatApplicationTests {

    @Autowired
    UserService service;
    @Autowired
    LoginController loginController;


    @Test
    void reg() {
        service.registUser("刘希宁","134556673843");


    }

    @Test
    void image() throws IOException, WriterException {

        Long id = service.registUser("刘希宁", "134556673843").getId();

        service.convertBlob(id);

    }

    @Test
    void request ()
    {
        service.sendFriendRequest(2,"刘希宁");
    }


    @Test
    void addNewFriend()
    {
    Long my = 3L;
    Long friend = 2L;
    Integer op = 1;

     loginController.listFriendList(2L,1L,op);

    }

}
