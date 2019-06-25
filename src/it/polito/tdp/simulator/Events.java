package it.polito.tdp.simulator;

import java.time.LocalDateTime;

import it.polito.tdp.model.District;

public class Events implements Comparable<Events>{
	
	enum EventType {
		OTHERCRIME,
		CRIME,
		FREE,
	}
	
	private LocalDateTime time;
	private EventType type;
	private District d;
	private Agent a;
	
	public Events(LocalDateTime time, District d, EventType t) {
		super();
		this.time = time;
		this.d = d;
		this.type = t;
	}
	
	public Events(LocalDateTime time, Agent a, EventType t) {
		super();
		this.time = time;
		this.a = a;
		this.type = t;
	}
	
	
	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public District getD() {
		return d;
	}
	public void setD(District d) {
		this.d = d;
	}
	
	public Agent getA() {
		return a;
	}

	@Override
	public int compareTo(Events o) {
	   return this.time.compareTo(o.getTime());
	}
	
	
	
	
	
	

}
