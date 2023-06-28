package it.polito.tdp.nyc.model;

import java.util.Objects;

import com.javadocmd.simplelatlng.LatLng;

public class Location implements Comparable<Location> {
	
	private String name;
	private LatLng posizione;
	private int numerovicini;
	
	public int getNumerovicini() {
		return numerovicini;
	}

	public void setNumerovicini(int numerovicini) {
		this.numerovicini = numerovicini;
	}

	public Location(String name, LatLng posizione) {
		super();
		this.name = name;
		this.posizione = posizione;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LatLng getPosizione() {
		return posizione;
	}

	public void setPosizione(LatLng posizione) {
		this.posizione = posizione;
	}

	@Override
	public String toString() {
		return this.name+"-"+this.posizione;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public int compareTo(Location o) {
		// TODO Auto-generated method stub
		return this.name.compareTo(o.name);
	}

	
	
	
	
	
	
	
	
	

}
