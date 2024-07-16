package com.example.base.utils;

import android.os.Bundle;

public class QueueBuffer {
    public enum ImageFormat {
        RGB_888(1), NV21(2);
        int value;
        private ImageFormat(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    byte[] buffer;
    int cacheSize;
    ImageFormat format;
    int width;
    int height;
    long index;
    int rotate;
    boolean mirror;
    Bundle bundle;
    public QueueBuffer(int bufferLength){
        if (bufferLength > 0) {
            this.buffer = new byte[bufferLength];
        }
    }
    public QueueBuffer(byte[] buffer) {
        this.buffer = buffer;
    }
    public void cacheBufer(byte[] data, int rotate, boolean mirror) {
        cacheSize = Math.min(data.length, buffer.length);
        System.arraycopy(data, 0, buffer, 0, cacheSize);
        this.index = System.currentTimeMillis();
        this.rotate = rotate;
        this.mirror = mirror;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    public boolean isMirror() {
        return mirror;
    }
    public void setMirror(boolean mirror) {
        this.mirror = mirror;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public static void swapBuffer(QueueBuffer left, QueueBuffer right) {
        if (left == null || right == null) {
            return;
        }
        long index = left.getIndex();
        boolean mirror = left.isMirror();
        int rotate = left.getRotate();
        byte[] buffer = left.getBuffer();
        int width=  left.getWidth(), height = left.getHeight(), cacheSize = left.cacheSize;
        Bundle des = left.getBundle();
        left.setIndex(right.getIndex());
        left.setMirror(right.isMirror());
        left.setRotate(right.getRotate());
        left.setBuffer(right.getBuffer());
        left.setBundle(right.getBundle());
        left.setWidth(right.getWidth());
        left.setHeight(right.getHeight());
        left.setCacheSize(right.getCacheSize());
        right.setIndex(index);
        right.setMirror(mirror);
        right.setRotate(rotate);
        right.setBuffer(buffer);
        right.setBundle(des);
        right.width = width;
        right.height = height;
        right.cacheSize = cacheSize;
    }

    public ImageFormat getFormat() {
        return format;
    }

    public void setFormat(ImageFormat format) {
        this.format = format;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
