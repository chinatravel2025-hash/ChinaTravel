package com.example.base.database.friend.entity;

import androidx.room.TypeConverter;

import com.aws.bean.util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GroupTagConverter {

    @TypeConverter
    public String objectToString(List<String> list) {
        return GsonUtil.getInstance().toJson(list);
    }

    @TypeConverter
    public List<String> stringToObject(String json) {
        Type listType = new TypeToken<List<String>>(){}.getType();
        return GsonUtil.getInstance().fromJson(json, listType);
    }

}
