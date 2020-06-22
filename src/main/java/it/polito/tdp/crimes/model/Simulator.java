package it.polito.tdp.crimes.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.model.Evento.EventType;


public class Simulator {
	
	private PriorityQueue<Evento> queue = new PriorityQueue<>();
	
	
	private final double velocità = 60;
	
	private final Duration corta = Duration.ofMinutes(60);
	private final Duration lunga = Duration.ofMinutes(120);
	private final Duration tempoMinimo = Duration.ofMinutes(15);
	
	private SimpleWeightedGraph<Integer,DefaultWeightedEdge> grafo;
	
	
	private List<Poliziotto> poliziotti;
	private Poliziotto poliziotto;
	
	private int eventiMalGestiti = 0;

	public int getEventiMalGestiti() {
		return eventiMalGestiti;
	}
	
	
	public void run(int dI, int N, List<Event> eventi, SimpleWeightedGraph<Integer,DefaultWeightedEdge> g) {
		this.eventiMalGestiti = 0;
		this.poliziotti = new ArrayList<>();
		this.grafo = g;
		this.poliziotto = null;
		
		for(int i = 0; i<N; i++) {
			Poliziotto p = new Poliziotto(i,dI,true);
			this.poliziotti.add(p);
		}
		
		this.queue.clear();
		
		for(Event ev: eventi) {
			Evento e = new Evento(ev.getReported_date(),EventType.NUOVO_CRIMINE,ev);
			this.queue.add(e);
		}
		
		while(!this.queue.isEmpty()) {
			Evento e = this.queue.poll();
			//System.out.println(e);
			processEvent(e);
		}
		
		
	}


	private void processEvent(Evento e) {
		
		switch(e.getType()) {
		
		case NUOVO_CRIMINE:
			
			double distanza = trovaDistanza(e);
			LocalDateTime t = e.getEvent().getReported_date();
			
			if(distanza==-1) {
				this.eventiMalGestiti++;
			}else {
				int tempo = (int) (distanza/(this.velocità/60));
				Duration tempoImpiegato = Duration.ofMinutes(tempo);
				t = t.plusMinutes(tempo);
				
				if(tempoImpiegato.compareTo(this.tempoMinimo)>0) {
					this.eventiMalGestiti++;
				}else {
					if(e.getEvent().getOffense_category_id().equals("all-other-crimes")) {
						double prob = Math.random();
						if(prob<0.5) {
							t = t.plusMinutes(this.corta.toMinutes());
						}else {
							t = t.plusMinutes(this.lunga.toMinutes());
						}					
					}else {
						t = t.plusMinutes(this.lunga.toMinutes());
					}
				}
				
				Evento ev = new Evento(t,EventType.CRIMINE_RISOLTO,e.getEvent());
				ev.setP(this.poliziotto);
				this.queue.add(ev);
				
			}
			
			
			break;
			
		case CRIMINE_RISOLTO:
			
			for(Poliziotto p: this.poliziotti) {
				if(p.equals(this.poliziotto)) {
					p.setOccupato(true);
				}
			}
			
			
			
			break;

		}
		
	}
	
	
	public double trovaDistanza(Evento e) {
		
		double distanza = -1;
		Poliziotto pol = null;
		
		for(Poliziotto p: this.poliziotti) {
			if(p.isOccupato()==true) {
				if(e.getEvent().getDistrict_id()==p.getDistretto()) {
					p.setOccupato(false);
					p.setDistretto(e.getEvent().getDistrict_id());
					this.poliziotto = p;
					
					return 0;
				}else {
					//System.out.println(p.getDistretto()+"  "+e.getEvent().getDistrict_id()+"\n");
					double d = this.grafo.getEdgeWeight(this.grafo.getEdge(p.getDistretto(), e.getEvent().getDistrict_id()));
					if(distanza == -1) {
						pol = p;
						distanza = d;
					}else {
						if(distanza<d) {
							pol = p;
							distanza = d;
						}
					}
				}
			}
			
		}
		
		if(pol==null) {
			return distanza;
		}else {
			pol.setOccupato(false);
			pol.setDistretto(e.getEvent().getDistrict_id());
			this.poliziotto = pol;
			return distanza;
		}

	}
	
	
	
	
	
	
	

}
