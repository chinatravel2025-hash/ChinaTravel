package com.aws.bean.api;

import java.io.Serializable;

public class FileData implements Serializable {

    private String code;

    private String url;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
