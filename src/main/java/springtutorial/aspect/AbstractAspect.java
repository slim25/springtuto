package springtutorial.aspect;

public abstract class AbstractAspect {
	protected Integer checkForFirstTimeCall(Integer eventCount){
		if(eventCount == null || eventCount == 0){
			eventCount = new Integer(1); 
		}else{
			eventCount++;
		}
		return eventCount;
	}
	
}
