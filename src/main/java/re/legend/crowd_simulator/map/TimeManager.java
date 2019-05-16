package re.legend.crowd_simulator.map;


public class TimeManager {
	long startTime=0;
	long currentTime;
	long endTime;
	long spentTime; //duree
	
    public void start()
    {
    	currentTime = System.currentTimeMillis();
    	startTime = currentTime;
    }
    
    public long getCurrentTime()
    {
    	return currentTime;
    }
    
    public void stop()
    {
    	spentTime = (endTime - startTime);
    }
}
