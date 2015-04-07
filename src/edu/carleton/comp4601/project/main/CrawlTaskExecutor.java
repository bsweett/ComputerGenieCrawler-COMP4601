package edu.carleton.comp4601.project.main;

//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlTaskExecutor {

	private static final Logger logger = LoggerFactory.getLogger(CrawlTaskExecutor.class);
	
	ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    CrawlTask myTask;
    volatile boolean isStopIssued;

    public CrawlTaskExecutor(CrawlTask myTask$) {
        myTask = myTask$;
    }

    /**
     * Starts an execution task at the given target time
     * 
     * @param targetHour
     * @param targetMin
     * @param targetSec
     */
    /*
    public void startExecutionAt(int targetHour, int targetMin, int targetSec) {
        Runnable taskWrapper = new Runnable(){

            @Override
            public void run()  {
                myTask.execute();
                startExecutionAt(targetHour, targetMin, targetSec);
            }

        };
        
        long delay = computNextDelay(targetHour, targetMin, targetSec);
        executorService.schedule(taskWrapper, delay, TimeUnit.SECONDS);
    }

    /**
     * Computes the delay until it calls the task again
     * 
     * @param targetHour
     * @param targetMin
     * @param targetSec
     * @return
     */
    /*
    private long computNextDelay(int targetHour, int targetMin, int targetSec) {
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.systemDefault();
        
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNextTarget = zonedNow.withHour(targetHour).withMinute(targetMin).withSecond(targetSec);
        
        if(zonedNow.compareTo(zonedNextTarget) > 0) {
            zonedNextTarget = zonedNextTarget.plusDays(1);
        }
        
        Duration duration = Duration.between(zonedNow, zonedNextTarget);
        
        return duration.getSeconds();
    }
*/
    /**
     *  Tries to stop the service 
     */
    public void stop() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException ex) {
        	logger.error("Interupt", ex);
        }
    }
	
}
