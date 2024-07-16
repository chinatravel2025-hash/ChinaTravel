package com.aws.bean.api;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class APIListData<T> {
    public String total;
    public List<T> pagelist;
    private String isAuditModel;

    public List<T> classifyItems;
    /**
     * 最后一次更新时间，用来标志是否过期
     */
    public String updateTime;//"1618556253548"
    public String userExp;
    private String isTnm;
    public String desc;


    public String cdnpre;

    public String jumpUrl;
    public String jumpName;
    public String rumorNextTime;

    public String getIsGCOver() {
        return isGCOver;
    }

    public void setIsGCOver(String isGCOver) {
        this.isGCOver = isGCOver;
    }

    private String isGCOver;

    private String isAllGCOver;

    public String getIsTnm() {
        return isTnm;
    }

    public void setIsTnm(String isTnm) {
        this.isTnm = isTnm;
    }

    public String getCdnpre() {
        return cdnpre;
    }

    public void setCdnpre(String cdnpre) {
        this.cdnpre = cdnpre;
    }


    public APIListData() {
        total = "0";
        pagelist = new ArrayList<T>();
    }

    public APIListData(String total, List<T> list) {
        this.total = total;
        this.pagelist = list;
    }

    public String getIsAllGCOver() {
        return isAllGCOver;
    }

    public void setIsAllGCOver(String isAllGCOver) {
        this.isAllGCOver = isAllGCOver;
    }

    public List<T> getClassifyItems() {
        return classifyItems;
    }

    public void setClassifyItems(List<T> classifyItems) {
        this.classifyItems = classifyItems;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsAuditModel() {
        return TextUtils.isEmpty(isAuditModel) ? "" : isAuditModel;
    }

    public boolean getRealAuditModel() {
        return TextUtils.isEmpty(isAuditModel) ? false : isAuditModel.equals("1");
    }

    public void setIsAuditModel(String isAuditModel) {
        this.isAuditModel = isAuditModel;
    }

}
