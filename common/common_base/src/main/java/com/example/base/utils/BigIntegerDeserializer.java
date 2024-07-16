package com.example.base.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import java.math.BigInteger;

public class BigIntegerDeserializer implements JsonDeserializer<BigInteger> {
    @Override
    public BigInteger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        return new BigInteger(json.getAsString());
    }
}
