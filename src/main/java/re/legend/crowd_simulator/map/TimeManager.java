package re.legend.crowd_simulator.map;


public class TimeManager {
	float startTime=0;
	float currentTime;
	float endTime;
	float spentTime; //duree
	
    public void start()
    {
    	currentTime = System.currentTimeMillis();
    	startTime = currentTime;
    }
    
    public float getCurrentTime()
    {
    	return currentTime;
    }
    
    public void stop()
    {
    	spentTime = (endTime - startTime);
    }
}
