package it.polito.tdp.crimes.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	public enum EventType{
		NUOVO_CRIMINE,
		CRIMINE_RISOLTO
	}
	
	private LocalDateTime istante;
	private EventType type;
	private Event event;
	private Poliziotto p;
	
	public Evento(LocalDateTime istante, EventType type, Event event) {
		super();
		this.istante = istante;
		this.type = type;
		this.event = event;
		this.p = null;
	}

	public Poliziotto getP() {
		return p;
	}

	public void setP(Poliziotto p) {
		this.p = p;
	}

	public LocalDateTime getIstante() {
		return istante;
	}

	public void setIstante(LocalDateTime istante) {
		this.istante = istante;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	

	@Override
	public String toString() {
		return "Evento [istante=" + istante + ", type=" + type + ", event=" + event + ", p=" + p + "]";
	}

	@Override
	public int compareTo(Evento other) {
		return this.istante.compareTo(other.istante);
	}
	
	
	
	
	

}
