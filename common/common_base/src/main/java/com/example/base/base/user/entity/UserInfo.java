package com.example.base.base.user.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 用户
 *
 * @Description:用户
 */

@Entity(tableName = "UserInfo_v12")
public class UserInfo implements Parcelable {

    public static int LOGIN_STATE_TRUE = 1;
    public static int LOGIN_STATE_FALSE = 0;
    /**
     * 当前用户id
     */

    @ColumnInfo(name = "uid_")
    private long userId;
    /**
     * 当前角色id
     */
//    @PrimaryKey()
//    @ColumnInfo(name = "rid_")
//    private long rid;

    @ColumnInfo(name = "token_")
    private String token;

    @ColumnInfo(name = "accessToken_")
    private String accessToken;
    /**
     * 昵称
     */
    @ColumnInfo(name = "nickname_")
    private String nickname;
    /**
     * 头像
     */
    @ColumnInfo(name = "headPic_")
    private String headPic;

    /**
     * 省号
     */
    @ColumnInfo(name = "provinceCode_")
    private String provinceCode;

    /**
     * 城市
     */
    @ColumnInfo(name = "cityCode_")
    private String cityCode;

    /**
     * 区号
     */
    @ColumnInfo(name = "districtCode_")
    private String districtCode;

    /**
     * 性别文本
     */
    @ColumnInfo(name = "genderLabel_")
    private String genderLabel;

    /**
     * 状态文本
     */
    @ColumnInfo(name = "statusLabel_")
    private String statusLabel;

    /**
     * 选择状态  1选中 0未选中 -1失效
     */
    @ColumnInfo(name = "loginState")
    private int loginState;

    /**
     * 性别 群组时这个字段是成员数
     */
    @ColumnInfo(name = "gender_")
    private int gender;

    /**
     * 生日
     */
    @ColumnInfo(name = "birthday_")
    private String birthday;

    /**
     * 星座
     */
    @ColumnInfo(name = "constellation_")
    private String constellation;
    /**
     * 年龄
     */
    @ColumnInfo(name = "age_")
    private String age;
    /**
     * 半身像
     */
    @ColumnInfo(name = "looks_")
    private String looks;
    /**
     * 地址
     */
    @ColumnInfo(name = "address_")
    private String address;
    /**
     * 城市
     */
    @ColumnInfo(name = "city_")
    private String city;
    /**
     * 地区
     */
    @ColumnInfo(name = "district_")
    private String district;

    /**
     * 学历
     */
    @ColumnInfo(name = "educationName_")
    private String educationName;
    /**
     * 学历编号
     */
    @ColumnInfo(name = "education_")
    private int education;
    /**
     * 待审核头像
     */
    @ColumnInfo(name = "headPicAudit_")
    private String headPicAudit;
    /**
     * 审核头像状态
     */
    @ColumnInfo(name = "headPicStatus_")
    private int headPicStatus;
    /**
     * 体重
     */
    @ColumnInfo(name = "weight_")
    private int weight;
    /**
     * 身高
     */
    @ColumnInfo(name = "height_")
    private int height;
    /**
     * 是否在读:0否1是
     */
    @ColumnInfo(name = "inSchool_")
    private int inSchool;
    /**
     * 登录时间
     */
    @ColumnInfo(name = "loginTime_")
    private String loginTime;
    /**
     * 籍贯市编码
     */
    @ColumnInfo(name = "nativeCityCode_")
    private String nativeCityCode;
    /**
     * 籍贯市
     */
    @ColumnInfo(name = "nativeCity_")
    private String nativeCity;
    /**
     * 籍贯区号
     */
    @ColumnInfo(name = "nativeDistrictCode_")
    private String nativeDistrictCode;
    /**
     * 籍贯区
     */
    @ColumnInfo(name = "nativeDistrict_")
    private String nativeDistrict;

    /**
     * 籍贯省编码
     */
    @ColumnInfo(name = "nativeProvinceCode_")
    private String nativeProvinceCode;
    /**
     * 籍贯省
     */
    @ColumnInfo(name = "nativeProvince_")
    private String nativeProvince;
    /**
     * 审核中昵称
     */
    @ColumnInfo(name = "nicknameAudit_")
    private String nicknameAudit;
    /**
     * 审核昵称状态
     */
    @ColumnInfo(name = "nicknameStatus_")
    private int nicknameStatus;
    /**
     * 省
     */
    @ColumnInfo(name = "province_")
    private String province;
    /**
     * 学校ID
     */
    @ColumnInfo(name = "schoolId_")
    private int schoolId;
    /**
     * 学校名称
     */
    @ColumnInfo(name = "schoolName_")
    private String schoolName;
    /**
     * 状态
     */
/*    @ColumnInfo(name = "status_")
    private int status;*/
    /**
     * 空间快照
     */
    @ColumnInfo(name = "space_")
    private String space;
    /**
     * 创建时间戳
     */
    @ColumnInfo(name = "createdAt_")
    private String createdAt;

    /**
     * 环信token
     */
    @ColumnInfo(name = "hxAccessToken_")
    private String hxAccessToken;

    /**
     * 心情
     */
    @ColumnInfo(name = "moodData_")
    private String moodData;

    /**
     * 被收藏数
     */
    @ColumnInfo(name = "collecteds_")
    private long collecteds;

    /**
     * 被点赞数
     */
    @ColumnInfo(name = "thumbsUpeds_")
    private long thumbsUpeds;


    /**
     * 日志数
     */
    @ColumnInfo(name = "diarys_")
    private long diarys;

    /**
     * 粉丝数
     */
    @ColumnInfo(name = "fans_")
    private int fans;
    /**
     * 关注数
     */
    @ColumnInfo(name = "followings_")
    private int followings;

    /**
     * 欧点/欧值
     */
    @ColumnInfo(name = "points_")
    private int points;
    /**
     * 欧气/欧币
     */
    @ColumnInfo(name = "eggs_")
    private int eggs;
    @ColumnInfo(name = "headType_")
    private int headType;
    /**
     * oursId
     */
    @ColumnInfo(name = "oursId_")
    private String oursId;

    /**
     * inviteCode
     */
    @ColumnInfo(name = "inviteCode_")
    private String inviteCode;
    /**
     * U3D性别  1 男，2女
     */
    @ColumnInfo(name = "oursGender_")
    private int oursGender;

    /**
     * 心情评论开关
     */
    @ColumnInfo(name = "eSCommentSwitch_")
    private boolean eSCommentSwitch;
    /**
     * ours秀
     */
    @ColumnInfo(name = "showBg")
    private String showBg;

    @ColumnInfo(name = "looksHead_")
    private String looksHead;

    @ColumnInfo(name = "isGM_")
    private int isGM;

    @ColumnInfo(name = "spaceData")
    private String spaceData;

    @ColumnInfo(name = "avatar")
    private String avatar;
    /**
     * 当前角色id
     */
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "ridStr_")
    private String ridStr = "";

    protected UserInfo(Parcel in) {
        userId = in.readLong();
        //rid = in.readLong();
        token = in.readString();
        accessToken = in.readString();
        nickname = in.readString();
        headPic = in.readString();
        headType = in.readInt();
        provinceCode = in.readString();
        cityCode = in.readString();
        districtCode = in.readString();
        genderLabel = in.readString();
        statusLabel = in.readString();
        loginState = in.readInt();
        gender = in.readInt();
        birthday = in.readString();
        constellation = in.readString();
        age = in.readString();
        looks = in.readString();
        address = in.readString();
        city = in.readString();
        district = in.readString();
        educationName = in.readString();
        education = in.readInt();
        headPicAudit = in.readString();
        headPicStatus = in.readInt();
        weight = in.readInt();
        height = in.readInt();
        inSchool = in.readInt();
        loginTime = in.readString();
        nativeCityCode = in.readString();
        nativeCity = in.readString();
        nativeDistrictCode = in.readString();
        nativeDistrict = in.readString();
        nativeProvinceCode = in.readString();
        nativeProvince = in.readString();
        nicknameAudit = in.readString();
        nicknameStatus = in.readInt();
        province = in.readString();
        schoolId = in.readInt();
        schoolName = in.readString();
        space = in.readString();
        createdAt = in.readString();
        hxAccessToken = in.readString();
        moodData = in.readString();
        collecteds = in.readLong();
        thumbsUpeds = in.readLong();
        diarys = in.readLong();
        fans = in.readInt();
        followings = in.readInt();
        points = in.readInt();
        eggs = in.readInt();
        oursId = in.readString();
        inviteCode = in.readString();
        oursGender = in.readInt();
        eSCommentSwitch = in.readByte() != 0;
        showBg = in.readString();
        looksHead = in.readString();
        isGM = in.readInt();
        spaceData = in.readString();
        avatar = in.readString();
        ridStr = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(userId);
        //dest.writeLong(rid);
        dest.writeString(token);
        dest.writeString(accessToken);
        dest.writeString(nickname);
        dest.writeString(headPic);
        dest.writeInt(headType);
        dest.writeString(provinceCode);
        dest.writeString(cityCode);
        dest.writeString(districtCode);
        dest.writeString(genderLabel);
        dest.writeString(statusLabel);
        dest.writeInt(loginState);
        dest.writeInt(gender);
        dest.writeString(birthday);
        dest.writeString(constellation);
        dest.writeString(age);
        dest.writeString(looks);
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(district);
        dest.writeString(educationName);
        dest.writeInt(education);
        dest.writeString(headPicAudit);
        dest.writeInt(headPicStatus);
        dest.writeInt(weight);
        dest.writeInt(height);
        dest.writeInt(inSchool);
        dest.writeString(loginTime);
        dest.writeString(nativeCityCode);
        dest.writeString(nativeCity);
        dest.writeString(nativeDistrictCode);
        dest.writeString(nativeDistrict);
        dest.writeString(nativeProvinceCode);
        dest.writeString(nativeProvince);
        dest.writeString(nicknameAudit);
        dest.writeInt(nicknameStatus);
        dest.writeString(province);
        dest.writeInt(schoolId);
        dest.writeString(schoolName);
        dest.writeString(space);
        dest.writeString(createdAt);
        dest.writeString(hxAccessToken);
        dest.writeString(moodData);
        dest.writeLong(collecteds);
        dest.writeLong(thumbsUpeds);
        dest.writeLong(diarys);
        dest.writeInt(fans);
        dest.writeInt(followings);
        dest.writeInt(points);
        dest.writeInt(eggs);
        dest.writeString(oursId);
        dest.writeString(inviteCode);
        dest.writeInt(oursGender);
        dest.writeByte((byte) (eSCommentSwitch ? 1 : 0));
        dest.writeString(showBg);
        dest.writeString(looksHead);
        dest.writeInt(isGM);
        dest.writeString(spaceData);
        dest.writeString(avatar);
        dest.writeString(ridStr);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };


    public boolean isESCommentSwitch() {
        return eSCommentSwitch;
    }

    public void setESCommentSwitch(boolean eSCommentSwitch) {
        this.eSCommentSwitch = eSCommentSwitch;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    @Ignore
    public UserInfo() {
        this.userId = 0;
        //this.rid = 0;
        this.ridStr = "";
        this.loginState = LOGIN_STATE_TRUE;
    }

    @Ignore
    public UserInfo(String token) {
        this.userId = 0;
        //this.rid = 0;
        this.ridStr = "";
        this.accessToken = token;
        this.loginState = LOGIN_STATE_TRUE;
    }

    public UserInfo(long userId,  String token, String accessToken, String nickname,
                    String headPic, int headType, String provinceCode,
                    String cityCode, String districtCode, String genderLabel,
                    String statusLabel, int loginState,
                    int gender,String birthday,
                    String constellation, String age,
                    String looks, String address, String city, String district,
                    String educationName, int education, String headPicAudit,
                    int headPicStatus, int weight, int height,
                    int inSchool, String loginTime, String nativeCityCode,
                    String nativeCity, String nativeDistrictCode, String nativeDistrict,
                    String nativeProvinceCode, String nativeProvince, String nicknameAudit,
                    int nicknameStatus, String province, int schoolId,
                    String schoolName, String space, String createdAt, String hxAccessToken,
                   String moodData,
                    long collecteds, long thumbsUpeds, long diarys, int fans, int followings, int points,
                    int eggs, String oursId, String inviteCode, int oursGender, boolean eSCommentSwitch, String showBg
            , String looksHead, int isGM,String spaceData,String avatar,String ridStr) {
        this.userId = userId;
        //this.rid = rid;
        this.token = token;
        this.accessToken = accessToken;
        this.nickname = nickname;
        this.headPic = headPic;
        this.headType = headType;
        this.provinceCode = provinceCode;
        this.cityCode = cityCode;
        this.districtCode = districtCode;
        this.genderLabel = genderLabel;
        this.statusLabel = statusLabel;
        this.loginState = loginState;
        this.gender = gender;
        this.birthday = birthday;
        this.constellation = constellation;
        this.age = age;
        this.looks = looks;
        this.address = address;
        this.city = city;
        this.district=district;
        this.educationName = educationName;
        this.education = education;
        this.headPicAudit = headPicAudit;
        this.headPicStatus = headPicStatus;
        this.weight = weight;
        this.height = height;
        this.inSchool = inSchool;
        this.loginTime = loginTime;
        this.nativeCityCode = nativeCityCode;
        this.nativeCity = nativeCity;
        this.nativeDistrictCode = nativeDistrictCode;
        this.nativeDistrict = nativeDistrict;
        this.nativeProvinceCode = nativeProvinceCode;
        this.nativeProvince = nativeProvince;
        this.nicknameAudit = nicknameAudit;
        this.nicknameStatus = nicknameStatus;
        this.province = province;
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        //this.status = status;
        this.space = space;
        this.createdAt = createdAt;
        this.hxAccessToken = hxAccessToken;
        this.moodData = moodData;
        this.collecteds = collecteds;
        this.thumbsUpeds = thumbsUpeds;
        this.diarys = diarys;
        this.fans = fans;
        this.followings = followings;
        this.points = points;
        this.eggs = eggs;
        this.oursId = oursId;
        this.inviteCode = inviteCode;
        this.oursGender = oursGender;
        this.eSCommentSwitch = eSCommentSwitch;
        this.showBg = showBg;
        this.looksHead = looksHead;
        this.isGM = isGM;
        this.spaceData = spaceData;
        this.avatar = avatar;
        this.ridStr = ridStr;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

//    public long getRid() {
//        return rid;
//    }

//    public void setRid(long rid) {
//        this.rid = rid;
//    }

    @NonNull
    public String getToken() {
        return token;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }

    @NonNull
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(@NonNull String accessToken) {
        this.accessToken = accessToken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getGenderLabel() {
        return genderLabel;
    }

    public void setGenderLabel(String genderLabel) {
        this.genderLabel = genderLabel;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public int getLoginState() {
        return loginState;
    }

    public void setLoginState(int loginState) {
        this.loginState = loginState;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLooks() {
        return looks;
    }

    public void setLooks(String looks) {
        this.looks = looks;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public String getHeadPicAudit() {
        return headPicAudit;
    }

    public void setHeadPicAudit(String headPicAudit) {
        this.headPicAudit = headPicAudit;
    }

    public int getHeadPicStatus() {
        return headPicStatus;
    }

    public void setHeadPicStatus(int headPicStatus) {
        this.headPicStatus = headPicStatus;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getInSchool() {
        return inSchool;
    }

    public void setInSchool(int inSchool) {
        this.inSchool = inSchool;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getNativeCityCode() {
        return nativeCityCode;
    }

    public void setNativeCityCode(String nativeCityCode) {
        this.nativeCityCode = nativeCityCode;
    }

    public String getNativeCity() {
        return nativeCity;
    }

    public void setNativeCity(String nativeCity) {
        this.nativeCity = nativeCity;
    }

    public String getNativeDistrictCode() {
        return nativeDistrictCode;
    }

    public void setNativeDistrictCode(String nativeDistrictCode) {
        this.nativeDistrictCode = nativeDistrictCode;
    }

    public String getNativeDistrict() {
        return nativeDistrict;
    }

    public void setNativeDistrict(String nativeDistrict) {
        this.nativeDistrict = nativeDistrict;
    }

    public String getNativeProvinceCode() {
        return nativeProvinceCode;
    }

    public void setNativeProvinceCode(String nativeProvinceCode) {
        this.nativeProvinceCode = nativeProvinceCode;
    }

    public String getNativeProvince() {
        return nativeProvince;
    }

    public void setNativeProvince(String nativeProvince) {
        this.nativeProvince = nativeProvince;
    }

    public String getNicknameAudit() {
        return nicknameAudit;
    }

    public void setNicknameAudit(String nicknameAudit) {
        this.nicknameAudit = nicknameAudit;
    }

    public int getNicknameStatus() {
        return nicknameStatus;
    }

    public void setNicknameStatus(int nicknameStatus) {
        this.nicknameStatus = nicknameStatus;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

/*
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
*/

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getHxAccessToken() {
        return hxAccessToken;
    }

    public void setHxAccessToken(String hxAccessToken) {
        this.hxAccessToken = hxAccessToken;
    }

    public String getMoodData() {
        return moodData;
    }

    public void setMoodData(String moodData) {
        this.moodData = moodData;
    }

    public long getCollecteds() {
        return collecteds;
    }

    public void setCollecteds(long collecteds) {
        this.collecteds = collecteds;
    }

    public long getThumbsUpeds() {
        return thumbsUpeds;
    }

    public void setThumbsUpeds(long thumbsUpeds) {
        this.thumbsUpeds = thumbsUpeds;
    }


    public long getDiarys() {
        return diarys;
    }

    public void setDiarys(long diarys) {
        this.diarys = diarys;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getFollowings() {
        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getEggs() {
        return eggs;
    }

    public void setEggs(int eggs) {
        this.eggs = eggs;
    }

    public String getOursId() {
        return oursId;
    }

    public void setOursId(String oursId) {
        this.oursId = oursId;
    }

    public int getOursGender() {
        return oursGender;
    }

    public void setOursGender(int oursGender) {
        this.oursGender = oursGender;
    }

    public String getShowBg() {
        return showBg;
    }

    public void setShowBg(String showBg) {
        this.showBg = showBg;
    }

    public String getLooksHead() {
        return looksHead;
    }

    public void setLooksHead(String looksHead) {
        this.looksHead = looksHead;
    }

    public int getIsGM() {
        return isGM;
    }

    public void setIsGM(int isGM) {
        this.isGM = isGM;
    }

    public String getSpaceData() {
        return spaceData;
    }

    public void setSpaceData(String spaceData) {
        this.spaceData = spaceData;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRidStr() {
        return ridStr;
    }

    public void setRidStr(String ridStr) {
        this.ridStr = ridStr;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getHeadType() {
        return headType;
    }

    public void setHeadType(int headType) {
        this.headType = headType;
    }
}
