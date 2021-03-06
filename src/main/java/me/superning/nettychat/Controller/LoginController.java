package me.superning.nettychat.Controller;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import me.superning.nettychat.domain.ChatMsg;
import me.superning.nettychat.domain.FriendsRequest;
import me.superning.nettychat.domain.User;
import me.superning.nettychat.domain.UserImage;
import me.superning.nettychat.domain.vo.FriendRequestsVo;
import me.superning.nettychat.domain.vo.MyFriendVo;
import me.superning.nettychat.enums.MsgActionEnum;
import me.superning.nettychat.enums.OperatorFriendRequestTypeEnum;
import me.superning.nettychat.enums.SeachFriendStatusEnums;
import me.superning.nettychat.netty.DataContent;
import me.superning.nettychat.netty.UserChannelRel;
import me.superning.nettychat.service.ChatMsgService;
import me.superning.nettychat.service.FriendlistService;
import me.superning.nettychat.service.FriendsRequestService;
import me.superning.nettychat.service.UserService;
import me.superning.nettychat.utils.JsonUtils;
import me.superning.nettychat.utils.ResponResult;
import me.superning.nettychat.utils.qrCodeGener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.LongSummaryStatistics;

/**
 * @author superning
 */
@RestController
@RequestMapping(value = "u")
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    FriendsRequestService friendsRequestService;
    @Autowired
    FriendlistService friendlistService;
    @Autowired
    ChatMsgService chatMsgService;



    @PostMapping(value = "/login")
    //登录+注册
    public ResponResult LoginorRegist(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponResult.errorMsg("用户名和密码不得为空");
        }

        boolean userExist = userService.findone(user.getUsername());
        User loginUser = null;

        if (userExist) {
            //login
            loginUser = userService.getLoginUser(user.getPassword(), user.getPassword());
            if (loginUser == null) {
                return ResponResult.errorMsg("密码错误");
            }
        } else {
            //regist
            loginUser = userService.registUser(user.getPassword(), user.getPassword());
        }

        return ResponResult.ok(loginUser);
    }

    //上传图片
    @PostMapping(value = "/uploadimage")
    public ResponResult uploadImageInBase64(@RequestBody UserImage userImage) throws Exception {

        userService.uploadImage(userImage);

        return ResponResult.ok();

    }


    @PostMapping(value = "/search")
    public ResponResult searchFriend(Long myID, String friendname) {
        if (friendname == null) {
            return ResponResult.errorMsg("用户名不能为空");

        }
        Integer status = userService.findFriendByUsername(myID, friendname);
        if (status.equals(SeachFriendStatusEnums.SUCCESS.status)) {
            User userByname = userService.findUserByname(friendname);
            return ResponResult.ok(userByname);

        } else {
            return ResponResult.errorMsg(SeachFriendStatusEnums.getMsgByKey(status));

        }


    }

    @PostMapping(value = "/addFriendRequest")
    public ResponResult addFriendRequest(Long myid, String friendname) {
        if (friendname == null) {
            return ResponResult.errorMsg("用户名不能为空");

        }
        Integer status = userService.findFriendByUsername(myid, friendname);
        if (status.equals(SeachFriendStatusEnums.SUCCESS.status)) {

        } else {
            return ResponResult.errorMsg(SeachFriendStatusEnums.getMsgByKey(status));

        }

        return ResponResult.ok();
    }


    @PostMapping(value = "/operFriendList")
    public ResponResult listFriendList(Long myId, Long sendrId, Integer opertype) {


        // 管理自己收到的好友请求
        if (myId == null || sendrId == null) {
            ResponResult.errorMsg("输入错误，请检查输入准确性");

        }

        if (OperatorFriendRequestTypeEnum.getMsgByType(opertype) == null) {
            ResponResult.errorMsg("没有任何操作");

        }
        if (opertype.equals(OperatorFriendRequestTypeEnum.IGNORE)) {
            friendsRequestService.deleteRequest(sendrId, myId);
        } else {
            //双向添加好友，然后删除请求
            friendlistService.addNewFriend(myId, sendrId);
            friendlistService.addNewFriend(sendrId, myId);
            friendsRequestService.deleteRequest(sendrId, myId);


            //我这边同意之后，对方的好友状态也要刷新
            Channel senderChannel = UserChannelRel.get(sendrId);
            if (senderChannel != null) {
                DataContent dataContent = new DataContent();
                dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);
                senderChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContent)));


            }


        }
        //我这边重新刷新好友列表
        List<FriendRequestsVo> friendRequestsVos = userService.listAllToMeRequest(myId);
        return ResponResult.ok(friendRequestsVos);

    }

    @GetMapping(value = "/friendlist")
    public ResponResult myFriendsList(Long myId) {
        if (myId == null) {
            return ResponResult.errorMsg("请确认输入");

        } else {
            List<MyFriendVo> myFriendVos = friendlistService.myFriendList(myId);


            return ResponResult.ok(myFriendVos);

        }


    }

    @GetMapping(value = "/getUnReadMsg")
    public ResponResult getUnReadMsg(Long myId) {
        if (myId == null) {
          return   ResponResult.errorMsg("不能为空");

        } else {
            List<ChatMsg> unReadMsg = chatMsgService.getUnReadMsg(myId);

            return   ResponResult.ok(unReadMsg);

        }

    }

}
