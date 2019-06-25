package it.polito.tdp.simulator;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.model.District;
import it.polito.tdp.model.Event;
import it.polito.tdp.model.Model;
import it.polito.tdp.simulator.Events.EventType;

public class Simulator {
	
	    Model model;
	    private List<Agent> agentsList;
	    private District source;

	    // Coda degli eventi
		private PriorityQueue<Events> queue;

		// Modello del Mondo
	    

		// Parametri di simulazione
		private int n;
		
		// Statistiche
		private int not;
		
		public Simulator(Year year, String month, String day, int n, Model model) {
			this.not = 0;
			this.n = n;
			this.model = model;
			this.source = model.getSafer(year);
			queue = new PriorityQueue<>();
			agentsList = new ArrayList<>();
			
			for(int i=1; i<=n; i++) {
				agentsList.add(new Agent(i,source));
			}
			for ( Event e : model.eventList(year,month,day) )
				if ( e.getOffense_category_id().compareTo("all_other_crimes") == 0 )
			       queue.add(new Events(e.getReported_date(),model.getdMap().get(e.getDistrict_id()),EventType.OTHERCRIME));
				else
					queue.add(new Events(e.getReported_date(),model.getdMap().get(e.getDistrict_id()),EventType.CRIME));
		}
		
		public void run() {
		
		    Random rand = new Random();
		    
			while (!queue.isEmpty()) {
				Events ev = queue.poll();
				double min = Double.MAX_VALUE;
				Agent temp = null;
				int flag = 1;
				
				switch ( ev.getType() ) {
				
				case OTHERCRIME:
					for ( Agent a : agentsList ) {
						if ( a.isStatus() == true) {
						  if ( !a.getD().equals(ev.getD()) && flag == 1 )
							if ( model.getGraph().getEdgeWeight(model.getGraph().getEdge(a.getD(), ev.getD())) < min ) {
								min = model.getGraph().getEdgeWeight(model.getGraph().getEdge(a.getD(), ev.getD()));
								temp = a;
							}
							else {
								temp = a;
								min = 0;
								flag = -1;
							}
						}
					}
					
					if ( temp != null ) {
						
						temp.setD(ev.getD());
						temp.setStatus(false);
						
						if ( min > 15 )
							not++;
						if ( rand.nextDouble() > 0.5 ) {
							queue.add(new Events(ev.getTime().plusHours(2),temp,EventType.FREE));
						}
						else {
							queue.add(new Events(ev.getTime().plusHours(1),temp,EventType.FREE));
						}
					}
					else
						not++;
					
					break;
					
				case CRIME: 	
					for ( Agent a : agentsList ) {
						if ( a.isStatus() == true ) {
					      if ( !a.getD().equals(ev.getD()) && flag == 1 )
							if ( model.getGraph().getEdgeWeight(model.getGraph().getEdge(a.getD(), ev.getD())) < min ) {
								min = model.getGraph().getEdgeWeight(model.getGraph().getEdge(a.getD(), ev.getD()));
								temp = a;
							}
							else {
								temp = a;
								min = 0;
								flag = -1;
							}
						}
					}
					
					if ( temp != null ) {
						temp.setD(ev.getD());
						temp.setStatus(false);
						
						if ( min > 15 )
							not++;
						queue.add(new Events(ev.getTime().plusHours(2),temp,EventType.FREE));
						}
					else 
						not++;
					
					break;
					
				case FREE:
					
					ev.getA().setStatus(true);
					
					break;
				}
			}
			
		}
		
		public int getNot() {
			return not;
		}
		
}
		
