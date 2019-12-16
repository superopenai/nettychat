package me.superning.nettychat.service;

import me.superning.nettychat.domain.vo.MyFriendVo;

import java.util.List;

/**
 * @author superning
 */
public interface FriendlistService{

    /**
     * 加好友
     * @param myId
     *        我的id
     * @param friendid
     *        通过请求的朋友的id
     */
    void addNewFriend(Long myId,Long friendid);
    List<MyFriendVo> myFriendList(Long myId);


}
