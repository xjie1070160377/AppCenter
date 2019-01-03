package cn.mooc.app.core.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.mooc.app.core.event.selector.EventSelector;

public class SysEvent {
	
	private Set<EventSelector> eventSelectors = new HashSet<EventSelector>();
	private TriggerType triggerType;
	private Object obj;
	private Map<String, Object> paramMap = new HashMap<String, Object>();
	
	public SysEvent(){}
	
	public SysEvent(TriggerType triggerType, Object obj){
		this.triggerType = triggerType;
		this.obj = obj;
	}
	
	public void addSelector(EventSelector selector){
		eventSelectors.add(selector);
	}

	public TriggerType getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(TriggerType triggerType) {
		this.triggerType = triggerType;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Set<EventSelector> getEventSelectors() {
		return eventSelectors;
	}

	public void setEventSelectors(Set<EventSelector> eventSelectors) {
		this.eventSelectors = eventSelectors;
	}
	
	public <T> T addParam(String paramName, T paramVal){
		return (T) this.paramMap.put(paramName, paramVal);
	}
	
	public <T> T getParam(String paramName){
		return (T) this.paramMap.get(paramName);
	}
	
}
