package it.polito.tdp.crimes.model;

public class Poliziotto {
	
	private int id;
	private int distretto;
	private boolean occupato;
	
	

	public Poliziotto(int id, int distretto, boolean occupato) {
		super();
		this.id = id;
		this.distretto = distretto;
		this.occupato = occupato;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDistretto() {
		return distretto;
	}

	public void setDistretto(int distretto) {
		this.distretto = distretto;
	}
	

	public boolean isOccupato() {
		return occupato;
	}

	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}

	@Override
	public String toString() {
		return "Poliziotto [id=" + id + ", distretto=" + distretto + ", occupato=" + occupato + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poliziotto other = (Poliziotto) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	

}
