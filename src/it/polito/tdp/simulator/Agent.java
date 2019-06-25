package it.polito.tdp.simulator;

import it.polito.tdp.model.District;

public class Agent {
	
	private int id;
	private District d;
	private boolean status;
	
	public Agent(int id, District d) {
		super();
		this.id = id;
		this.d = d;
		status = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public District getD() {
		return d;
	}

	public void setD(District d) {
		this.d = d;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
