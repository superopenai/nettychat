package me.superning.nettychat.service.Impl;

import me.superning.nettychat.domain.FriendsRequest;
import me.superning.nettychat.mapper.FriendsRequestMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import me.superning.nettychat.service.FriendsRequestService;
import tk.mybatis.mapper.entity.Example;

/**
 * @author superning
 */
@Service
public class FriendsRequestServiceImpl implements FriendsRequestService{

    @Resource
    private FriendsRequestMapper friendsRequestMapper;



    /**
     *
     * @param senderId
     *         发送人的id
     * @param myId
     *          接收人自己的id
     */
    @Override
    public void deleteRequest(Long senderId,Long myId) {

        Example requestExample = new Example(FriendsRequest.class);

        requestExample.createCriteria().andEqualTo("sendUserId",senderId)
                                         .andEqualTo("acceptUserId",myId);
        friendsRequestMapper.deleteByExample(requestExample);
    }
}
