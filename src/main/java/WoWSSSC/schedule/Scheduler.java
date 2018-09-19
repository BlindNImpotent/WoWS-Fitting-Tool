package WoWSSSC.schedule;

import WoWSSSC.parser.AsyncHashMap;
import WoWSSSC.service.APIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private AsyncHashMap asyncHashMap;

    @Autowired
    private APIService apiService;

    private final static Logger logger = LoggerFactory.getLogger(Scheduler.class);

//    @Scheduled(fixedRate = 24 * 60 * 60 * 1000, initialDelay = 24 * 60 * 60 * 1000)
//    @Scheduled(cron = "0 0 12 * * ?")
//    public void run() throws Exception
//    {
//        try
//        {
//            logger.info("Getting API data");
//            asyncHashMap.run();
//            logger.info("Finished getting API data");
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

    @Scheduled(cron = "0 0 * * * *")
    public void cacheEvict()
    {
        apiService.cacheEvictShipHashMap();
    }
}
