package it.polito.tdp.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;

public class Model {
	
	EventsDao dao;
	Map<Integer,District> dMap;
	Graph<District,DefaultWeightedEdge> graph;
	
	public Model() {
		dao  = new EventsDao();
		dMap = new HashMap<>();
	}
	
	public List<Year> getYearsList(){
		return dao.yearsList();
	}
	
	public List<Event> eventList(Year year, String month, String day){
		for ( District d: dMap.values() )
			  d.newList();
		return dao.listAllEvents(year, month, day, dMap);
	}
	
	public District getSafer(Year year) {
		return dao.safer(year, dMap);
	}
	
	public void buildGraph(Year year){
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(graph, dao.districtsList(year,dMap));
		
		for ( District d1 : graph.vertexSet() ) {
			for ( District d2 : graph.vertexSet() ) {
				if ( !d1.equals(d2) && !graph.containsEdge(d1, d2) ) {
					double distance = new Double(LatLngTool.distance(d1.getCoord(), d2.getCoord(), LengthUnit.KILOMETER));
				    Graphs.addEdge(graph, d1, d2, distance);
				}
			}
		}
	}
	
	public List<DistrictDistance> getNeighbors(District d) {
		List<DistrictDistance> list = new ArrayList<>();
		List<District> neighbors = new ArrayList<>(Graphs.neighborListOf(graph, d));
		
		for ( District d1 : neighbors ) {
			double distance = graph.getEdgeWeight(graph.getEdge(d, d1));
			list.add(new DistrictDistance(d1,distance));
		}
		Collections.sort(list);
		
		return list;
	}

	public Graph<District, DefaultWeightedEdge> getGraph() {
		return graph;
	}

	public Map<Integer, District> getdMap() {
		return dMap;
	}
	
	
	
}
