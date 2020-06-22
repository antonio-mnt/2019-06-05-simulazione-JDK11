package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.crimes.db.EventsDao;


public class Model {
	

	private EventsDao dao;
	private SimpleWeightedGraph<Integer,DefaultWeightedEdge> grafo;
	private List<Integer> distretti;
	private Map<Integer,LatLng> coordinate;
	private Simulator sim;
	
	
	public Model() {
		dao = new EventsDao();
	}
	
	
	public void creaGrafo(int anno) {
		
		this.distretti = new ArrayList<>(dao.listaDistretti());
		this.grafo = new SimpleWeightedGraph<Integer,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.distretti);
		
		this.coordinate = new HashMap<>();
		
		this.dao.coordinate(this.coordinate, anno);
		
		for(Integer i: this.coordinate.keySet()) {
			for(Integer j: this.coordinate.keySet()) {
				
				if(i!=j && !this.grafo.containsEdge(i,j) && !this.grafo.containsEdge(j,i)) {
					Arco tempC = new Arco(i,j,this.coordinate.get(i),this.coordinate.get(j));
					Graphs.addEdgeWithVertices(this.grafo, tempC.getD1(), tempC.getD2(), tempC.getDistanza());
					//System.out.println(this.grafo);
				}
				
			}
		}
	/*	DefaultWeightedEdge def = this.grafo.getEdge(2, 1);
		System.out.println("\n\n"+def+" "+this.grafo.getEdgeWeight(def));*/
		
	}
	
	public int getNumeroVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumeroArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public String reteCittadina() {
		
		String elenco = "";
		
		
		for(Integer i: this.distretti) {
			List<Integer> vicini = new ArrayList<>(Graphs.neighborListOf(this.grafo, i));
			vicini.remove(i);
			List<Arco> archi = new ArrayList<>();
			for(Integer v: vicini) {
				Arco tempA = new Arco(i,v,null,null);
				tempA.setDistanza(this.grafo.getEdgeWeight(this.grafo.getEdge(i,v)));
				archi.add(tempA);
			}
			Collections.sort(archi);
			elenco+="Vicini del distretto "+i+":\n";
			for(Arco a: archi) {
				elenco+=String.format("%d distanza: %.2f km\n",a.getD2(), a.getDistanza());
			}
			
		}
		
		
		return elenco;	
	}
	
	
	public List<Integer> getListaAnni(){
		return dao.listaAnni();
	}
	
	public List<Integer> getListaMesi(int anno){
		return dao.listaMesi(anno);
	}
	
	public List<Integer> getListaGiorni(int anno, int mese){
		return dao.listaGiorni(anno, mese);
	}
	
	
	public int getEventiMalGestiti(int anno, int mese, int giorno, int N) {
		this.sim = new Simulator();
		this.sim.run(this.dao.distrettoIniziale(anno, mese, giorno), N, this.dao.listAllEvents(anno, mese, giorno), this.grafo);
		return this.sim.getEventiMalGestiti();
	}
	
	
	
	
	
}
