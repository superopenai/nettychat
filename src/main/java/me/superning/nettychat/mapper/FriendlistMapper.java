package me.superning.nettychat.mapper;

import me.superning.nettychat.domain.Friendlist;
import me.superning.nettychat.domain.vo.MyFriendVo;
import me.superning.nettychat.utils.MyMapper;

import java.util.List;

/**
 * @author superning
 */
public interface FriendlistMapper extends MyMapper<Friendlist> {


    /**
     *
     * @param myId
     *         我的id
     * @return
     *         朋友对象的列表
     * +----------+----------------+----------------+-------------+
     * | friendId | friendUsername | friendNickname | friendImage |
     * +----------+----------------+----------------+-------------+
     * |        3 | xxx     xxxx   | xxxxxxxxxx     |             |
     * |        1 | test           | test           |             |
     * +----------+----------------+----------------+-------------+
     */
    List<MyFriendVo> selectFriendList(Long myId);



}
