package me.superning.nettychat.service.Impl;

import me.superning.nettychat.domain.Friendlist;
import me.superning.nettychat.domain.vo.MyFriendVo;
import me.superning.nettychat.mapper.FriendlistMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import me.superning.nettychat.service.FriendlistService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author superning
 */
@Service
public class FriendlistServiceImpl implements FriendlistService{

    @Resource
    private FriendlistMapper friendlistMapper;

    /**
     *  在数据库朋友表里添加一个我与朋友的关系
     *                      朋友与我双向的关系只需要调转一下参数即可
     * @param myId
     *        我的id
     * @param friendId
     *        朋友的id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewFriend(Long myId, Long friendId) {

        Friendlist friendlist = new Friendlist();
        friendlist.setMyId(myId);
        friendlist.setMyFriendId(friendId);
        friendlistMapper.insert(friendlist);


    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<MyFriendVo> myFriendList(Long myId) {

        return friendlistMapper.selectFriendList(myId);

    }
}
