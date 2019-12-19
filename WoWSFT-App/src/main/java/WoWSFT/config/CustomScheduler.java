package WoWSFT.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomScheduler
{

//    @CacheEvict(allEntries = true, cacheNames = {"ship"})
//    @Scheduled(cron = "0 0 * * * *")
    public void cacheEvict()
    {
        log.info("Cache Evicted");
    }
}
