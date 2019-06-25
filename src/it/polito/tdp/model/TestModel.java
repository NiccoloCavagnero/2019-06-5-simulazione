package it.polito.tdp.model;

import java.time.Year;

public class TestModel {

	public static void main(String[] args) {
		Model model = new Model();
		System.out.println(model.getYearsList());
		//System.out.println(model.dao.districtsList(Year.of(2015)));
		model.buildGraph(Year.of(2017));
		System.out.println(model.eventList(Year.of(2017), "01", "01"));
	}

}
