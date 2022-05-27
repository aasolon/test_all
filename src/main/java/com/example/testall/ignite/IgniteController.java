//package com.example.testall.ignite;
//
//import org.apache.ignite.Ignite;
//import org.apache.ignite.IgniteCache;
//import org.apache.ignite.internal.IgnitionEx;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class IgniteController {
//
////    @Autowired
//    private Ignite ignite = null;
//
//    @Autowired
//    private CachedService cachedService;
//
//    @GetMapping("/get-or-create-ignite-cache-with-peer-class-loading")
//    public String getOrCreateIgniteCacheWithPeerClassLoading() {
//        String cacheName = "peerClassLoadingCache";
//        IgniteCache<Integer, SomeCacheValue> cache = ignite.getOrCreateCache(cacheName);
//        SomeCacheValue someCacheValue = new SomeCacheValue();
//        someCacheValue.setValue("PPP_999");
//
//        String result = "Ignite cache size before put: " + cache.size() + "\n";
//
//        SomeCacheValue value = cache.get(1);
//        SomeCacheValue value2 = cache.get(2);
//        SomeCacheValue value3 = cache.get(3);
//        result += "Ignite cache value by key 1 before put: " + value + "\n";
//
//        cache.put(1, someCacheValue);
//
//        value = cache.get(1);
//        result += "Ignite cache value by key 1 after put: " + value;
//
//        SomeCacheValue someCacheValue1 = cachedService.getSomeCacheValue(3);
//
//        return result;
//    }
//
//    @GetMapping("/get-or-create-ignite-cache")
//    public String getOrCreateIgniteCache() {
//        String cacheName = "accounts";
//        IgniteCache<Integer, String> cache = ignite.getOrCreateCache(cacheName);
//
//        String result = "Ignite cache size before put: " + cache.size() + "\n";
//
//        String value = cache.get(1);
//        result += "Ignite cache value by key 1 before put: " + value + "\n";
//
//        cache.put(1, "Hello");
//        cache.put(2, "World!");
//
//        value = cache.get(1);
//        result += "Ignite cache value by key 1 after put: " + value;
//        return result;
//    }
//
//    @GetMapping("/get-ignite-cache-statistics")
//    public String getIgniteCacheStatistics() {
//        String cacheName = "accounts";
//        IgniteCache<Object, Object> cache = ignite.cache(cacheName);
//
//        String result;
//        if (cache != null) {
//            result = "Statistics for cache \"" + cache.getName() + "\"\n" +
//                    "    IsStatisticsEnabled: " + cache.metrics().isStatisticsEnabled() + "\n" +
//                    "    Size: " + cache.size() + "\n" +
//                    "    CacheGets: " + cache.metrics().getCacheGets() + "\n" +
//                    "    CachePuts: " + cache.metrics().getCachePuts() + "\n" +
//                    "    KeyType: " + cache.metrics().getKeyType() + "\n" +
//                    "    ValueType: " + cache.metrics().getValueType() + "\n";
//        } else {
//            result = "Cache \"" + cacheName + "\" is null";
//        }
//        return result;
//    }
//
//    @GetMapping("/destroy-ignite-cache")
//    public String destroyIgniteCache() {
//        String cacheName = "accounts";
//        IgniteCache<Object, Object> cache = ignite.cache(cacheName);
//        cache.destroy();
//        return "Cache \"" + cacheName + "\" is deleted";
//    }
//}
