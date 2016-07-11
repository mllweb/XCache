package com.mllweb.cache;

import android.content.Context;

import java.io.File;


public class XCacheConfig {
    private Builder mBuilder;

    public XCacheConfig(Context context) {
        mBuilder = new Builder(context);
    }

    public XCacheConfig(Builder builder) {
        if (builder == null) {
            throw new NullPointerException("XCacheConfig -> Builder:Constructor arguments cannot be empty");
        }
        mBuilder = builder;
    }

    /**
     * get cache path
     */
    public String getCachePath() {
        FileUtils.createFolder(mBuilder.cachePath);
        return mBuilder.cachePath;
    }

    /**
     * get key encrypt enabled
     */
    public boolean getKeyEncrypt() {
        return mBuilder.keyEncrypt;
    }

    /**
     * get memory cache max size
     *
     */
    public int getMemoryCacheMaxSize() {
        return mBuilder.memoryCacheMaxSize;
    }

    /**
     * get memory cache enabled
     */
    public boolean getMemoryCacheEnabled() {
        return mBuilder.memoryCache;
    }

    public static class Builder {
        private XCacheConfig config;
        private String cachePath;
        private boolean keyEncrypt = true;
        private boolean memoryCache = true;
        private int memoryCacheMaxSize = 1024 * 1024 * 10;

        public Builder(Context context) {
            config = new XCacheConfig(this);
            cachePath = context.getExternalCacheDir().getAbsolutePath() + File.separator + "XCache";
        }

        /**
         * set cache path
         */
        public Builder setCachePath(String cachePath) {
            this.cachePath = cachePath + File.separator + "XCache";
            return this;
        }

        public Builder setMemoryCacheMaxSize(int maxSize) {
            this.memoryCacheMaxSize = maxSize;
            return this;
        }

        /**
         * set memory cache max size
         */
        public Builder setMemoryCacheEnabled(boolean enabled) {
            this.memoryCache = enabled;
            return this;
        }

        /**
         * set key encrypt enabled,default true
         */
        public Builder setKeyEncrypt(boolean keyEncrypt) {
            this.keyEncrypt = keyEncrypt;
            return this;
        }

        /**
         * build config object
         */
        public XCacheConfig build() {
            return config;
        }
    }
}
