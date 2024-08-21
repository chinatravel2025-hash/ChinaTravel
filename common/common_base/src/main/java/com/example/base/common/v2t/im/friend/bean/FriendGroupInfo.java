package com.example.base.common.v2t.im.friend.bean;

import java.util.List;

public class FriendGroupInfo {

    private String groupName;

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public long getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(long friendCount) {
        this.friendCount = friendCount;
    }

    private long friendCount;

    List<String> friendIds;

    public void setFriendIds(List<String> friendIds) {
        this.friendIds = friendIds;
    }

    public List<String> getFriendIds() {
        return friendIds;
    }
}
