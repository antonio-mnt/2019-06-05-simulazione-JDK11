package it.polito.tdp.crimes.model;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Arco implements Comparable<Arco>{
	
	private Integer d1;
	private Integer d2;
	private double distanza;
	
	public Arco(Integer d1, Integer d2, LatLng c1, LatLng c2) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		if(c1!=null && c2!=null) {
			this.distanza = LatLngTool.distance(c1, c2, LengthUnit.KILOMETER);
		}else {
			this.distanza = 0;
		}
		
	}

	public Integer getD1() {
		return d1;
	}

	public void setD1(Integer d1) {
		this.d1 = d1;
	}

	public Integer getD2() {
		return d2;
	}

	public void setD2(Integer d2) {
		this.d2 = d2;
	}

	public double getDistanza() {
		return distanza;
	}

	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((d1 == null) ? 0 : d1.hashCode());
		result = prime * result + ((d2 == null) ? 0 : d2.hashCode());
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
		Arco other = (Arco) obj;
		if (d1 == null) {
			if (other.d1 != null)
				return false;
		} else if (!d1.equals(other.d1))
			return false;
		if (d2 == null) {
			if (other.d2 != null)
				return false;
		} else if (!d2.equals(other.d2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Arco [d1=" + d1 + ", d2=" + d2 + ", distanza=" + distanza + "]";
	}

	@Override
	public int compareTo(Arco o) {
		// TODO Auto-generated method stub
		return (int) (this.distanza-o.distanza);
	}
	
	
	

}
