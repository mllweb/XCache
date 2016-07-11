package com.mllweb.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class XCache {
    private static XCache mCache;
    private XCacheConfig mConfig;
    private LruCache<String, byte[]> mLruCache;

    private XCache(XCacheConfig config) {
        mConfig = config;
        mLruCache = new LruCache<>(mConfig.getMemoryCacheMaxSize());
    }

    public static XCache getInstance(Context context) {
        if (mCache == null) {
            synchronized (XCache.class) {
                if (mCache == null) {
                    XCacheConfig config = new XCacheConfig(context);
                    mCache = new XCache(config);
                }
            }
        }
        return mCache;
    }

    public static XCache getInstance(XCacheConfig manage) {
        if (mCache == null) {
            synchronized (XCache.class) {
                if (mCache == null) {
                    mCache = new XCache(manage);
                }
            }
        }
        return mCache;
    }

    /**
     * write to cache
     */
    public void put(String key, String value) {
        put(key, value.getBytes());
    }

    /**
     * write to cache
     */
    public void put(String key, JSONObject value) {
        put(key, value.toString());
    }

    /**
     * write to cache
     *
     */
    public void put(String key, JSONArray value) {
        put(key, value.toString());
    }

    /**
     * write to cache
     */
    public void put(String key, Bitmap value) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            value.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            byte[] byteArray = bytes.toByteArray();
            bytes.close();
            put(key, byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get cache size
     */
    public long size() {
        return FileUtils.size(mConfig.getCachePath());
    }

    /**
     * get cache format byte array
     */
    public byte[] getBytes(String key) {
        String keyEncryptStr = key;
        if (mConfig.getKeyEncrypt()) {
            keyEncryptStr = MD5.encrypt(key);
        }
        if (mConfig.getMemoryCacheEnabled()) {
            byte[] bytes = mLruCache.get(keyEncryptStr);
            if (bytes != null && bytes.length > 0) {
                return bytes;
            }
        }
        return FileUtils.read(mConfig.getCachePath() + File.separator + keyEncryptStr);
    }

    /**
     * get cache format JSONObject
     */
    public JSONObject getJSONObject(String key) {
        try {
            byte[] bytes = getBytes(key);
            String value = new String(bytes, 0, bytes.length);
            return new JSONObject(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /**
     * get cache format JSONArray
     */
    public JSONArray getJSONArray(String key) {
        try {
            byte[] bytes = getBytes(key);
            String value = new String(bytes, 0, bytes.length);
            return new JSONArray(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    /**
     * get cache format String
     */
    public String getString(String key) {
        byte[] bytes = getBytes(key);
        String value = new String(bytes, 0, bytes.length);
        return value;
    }

    /**
     * get cache format Bitmap
     */
    public Bitmap getBitmap(String key) {
        byte[] bytes = getBytes(key);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * cleat cache
     */
    public void clear() {
        new Thread() {
            @Override
            public void run() {
                FileUtils.delete(mConfig.getCachePath());
            }
        }.start();
    }

    /**
     * write to cache
     */
    public void put(final String key, final byte[] bytes) {
        new Thread() {
            @Override
            public void run() {
                String keyEncryptStr = key;
                if (mConfig.getKeyEncrypt()) {
                    keyEncryptStr = MD5.encrypt(key);
                }
                if (mConfig.getMemoryCacheEnabled()) {
                    mLruCache.put(keyEncryptStr, bytes);
                }
                FileUtils.write(mConfig.getCachePath() + File.separator + keyEncryptStr, bytes);
            }
        }.start();
    }
}
