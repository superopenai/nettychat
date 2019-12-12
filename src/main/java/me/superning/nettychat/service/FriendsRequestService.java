package me.superning.nettychat.service;

public interface FriendsRequestService{

    /**
     * 处理结束这个好友请求后，对请求进行删除
     * @param senderId
     *         发送人的id
     * @param myId
     *          接收人自己的id
     *
     */
    void deleteRequest(Long senderId,Long myId);


}
