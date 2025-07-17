package mx.com.vepormas.outseer;

import mx.com.vepormas.outseer.util.Constantes;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.util.Arrays;

public class SpringCacheCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

    @Override
    public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(Arrays.asList(
                Constantes.CACHE_NOMBRE_ANALYZE_RERQUEST,
                Constantes.CACHE_NOMBRE_ANALYZE_RESPONSE));
    }
}