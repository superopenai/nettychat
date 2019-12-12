package me.superning.nettychat.service.Impl;

import me.superning.nettychat.domain.Friendlist;
import me.superning.nettychat.mapper.FriendlistMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import me.superning.nettychat.service.FriendlistService;
@Service
public class FriendlistServiceImpl implements FriendlistService{

    @Resource
    private FriendlistMapper friendlistMapper;

    /**
     *
     * @param myId
     *        我的id
     * @param friendId
     *        朋友的id
     */
    @Override
    public void addNewFriend(Long myId, Long friendId) {

        Friendlist friendlist = new Friendlist();
        friendlist.setMyId(myId);
        friendlist.setMyFriendId(friendId);
        friendlistMapper.insert(friendlist);


    }
}
