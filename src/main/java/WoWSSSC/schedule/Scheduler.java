package WoWSSSC.schedule;

import WoWSSSC.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Qualson-Lee on 2016-11-25.
 */
@Component
public class Scheduler
{
    @Autowired
    private APIService apiService;

    @Scheduled(cron = "0 0 * * * *")
    public void cacheEvict()
    {
        apiService.cacheEvictShipHashMap();
    }
}
