package com.example.testall.ignite;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachedServiceImpl implements CachedService {

    @Cacheable("peerClassLoadingCache")
    public SomeCacheValue getSomeCacheValue(int key) {
        SomeCacheValue someCacheValue = new SomeCacheValue();
        someCacheValue.setValue("from_cachedMethod");
        return someCacheValue;
    }
}
