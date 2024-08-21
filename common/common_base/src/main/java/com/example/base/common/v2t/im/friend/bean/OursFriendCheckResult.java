package com.example.base.common.v2t.im.friend.bean;

public class OursFriendCheckResult {

    public static final int FRIEND_RELATION_TYPE_NONE = 0;
    public static final int FRIEND_RELATION_TYPE_IN_MY_FRIEND_LIST = 1;
    public static final int FRIEND_RELATION_TYPE_IN_OTHER_FRIEND_LIST = 2;
    public static final int FRIEND_RELATION_TYPE_BOTH_WAY = 3;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private int resultCode;

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    private int relationType;

    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }

    public int getRelationType() {
        return relationType;
    }


    private String resultStr;

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public String getResultStr() {
        return resultStr;
    }

    public OursFriendCheckResult() {
    }


}
