package com.example.linebot.model;

import java.io.Serializable;
import java.util.List;

public class EventWrapper implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Event> events;
    private String destination;
    
    public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public List<Event> getEvents() {
        return events;
    }
    public void setEvents(List<Event> events) {
        this.events = events;
    }    
}