# XCache
XCache is a lightweight hard disk cache framework
#gradle
```
compile 'com.mllweb:xcache:1.0.0'
```
#used
```
XCache cache=XCache.getInstance(Context);
cache.put(String key,byte[] bytes);
```
At the same time, you can also configure the cache policy
```
 XCacheConfig config=new XCacheConfig.Builder(Context)
                .setCacheMaxSize()//set the size of the hard disk cache
                .setCachePath()//set the hard disk cache path
                .setKeyEncrypt(true)//Sets whether to enable the memory cache
                .setMemoryCacheEnabled(true)//Sets whether to enable the memory cache
                .setMemoryCacheMaxSize(0)//Sets whether to enable key encryption
                .build();
XCache cache=XCache.getInstance(config);
```
