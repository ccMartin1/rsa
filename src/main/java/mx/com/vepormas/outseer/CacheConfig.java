package mx.com.vepormas.outseer;

import lombok.extern.slf4j.Slf4j;
import mx.com.vepormas.outseer.util.Constantes;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
/**
 * @author E01264-Martin Cruz Vargas
 * @version 1.0
 * @since 2024-16-08
 * @apiNote  Clase de configuracion de la cache
 */
@Configuration
@EnableCaching
@EnableScheduling
@Slf4j
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache(Constantes.CACHE_NOMBRE_ANALYZE_RERQUEST),
                new ConcurrentMapCache(Constantes.CACHE_NOMBRE_ANALYZE_RESPONSE)
        ));
        return cacheManager;
    }
}