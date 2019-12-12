package me.superning.nettychat.mapper;

import me.superning.nettychat.domain.User;
import me.superning.nettychat.domain.vo.FriendRequestsVo;
import me.superning.nettychat.utils.MyMapper;

import java.util.List;

public interface UserMapper extends MyMapper<User> {
    /**
     *  你好友看到的东西
     *  +------------+--------------+---------------+--------------+
     * | sendUserId | sendUsername | sendFaceImage | sendNickname |
     * +------------+--------------+---------------+--------------+
     * |          1 | test         |               | test         |
     * +------------+--------------+---------------+--------------+
     *
     * @param acceptUserId
     *          对方的id
     * @return
     *      一个多表查询的对象
     */
    public List<FriendRequestsVo> listAllToMeRequest(Long acceptUserId);

}