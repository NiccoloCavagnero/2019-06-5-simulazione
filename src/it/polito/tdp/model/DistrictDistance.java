package it.polito.tdp.model;

public class DistrictDistance implements Comparable<DistrictDistance>{
	
	 private District d;
	 private double dist;
	
	 public DistrictDistance(District d, double dist) {
		super();
		this.d = d;
		this.dist = dist;
	}

	public District getD() {
		return d;
	}

	public void setD(District d) {
		this.d = d;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}

	@Override
	public int compareTo(DistrictDistance o) {
		return (int) (this.dist-o.getDist());
	}

	@Override
	public String toString() {
		return "ID: "+d.getId() + ", dist: "+dist;
	}
	 
	 
	 
	 

}
