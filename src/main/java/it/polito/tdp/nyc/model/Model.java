package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private NYCDao dao;
	private Graph<Location, DefaultWeightedEdge> grafo;
	//RICORSIONE
	private Location target;
	private List<Location> percorso;
	private int dimensionePercorso;
	private String stringa;
	
	public Model() {
		this.dao=new NYCDao();
	}

	public List<String> getProviders() {
		
		return this.dao.getAllProviders();
	}

	public List<Location> creaGrafo(String provider, double distance) {
		this.grafo=new SimpleWeightedGraph<Location,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		
		List<Location> vertici=this.dao.getLocations(provider);
		Graphs.addAllVertices(this.grafo, vertici);
		for(Location l: this.grafo.vertexSet()) {
			for (Location l1:this.grafo.vertexSet()) {
				if(l.compareTo(l1)>0 && LatLngTool.distance(l1.getPosizione(), l.getPosizione(), LengthUnit.KILOMETER)<=distance) {
					Graphs.addEdgeWithVertices(this.grafo, l1, l, distance);	
				}
			}
		}
		
		return vertici;
		
	}

	public int getVertici() {
		// TODO Auto-generated method stub
		return this.grafo.vertexSet().size();
	}
	public int getArchi() {
		// TODO Auto-generated method stub
		return this.grafo.edgeSet().size();
	}

	public List<Location> getMigliori() {
		List<Location> migliori=new ArrayList<>();
		int numeroVicini=0;
		for(Location l: this.grafo.vertexSet()) {
			if(migliori.size()==0) {
				numeroVicini=Graphs.neighborListOf(this.grafo, l).size();
				l.setNumerovicini(numeroVicini);
				migliori.add(l);
			}
			else if(Graphs.neighborListOf(this.grafo, l).size()>numeroVicini) {
				numeroVicini=Graphs.neighborListOf(this.grafo, l).size();
				l.setNumerovicini(numeroVicini);
				migliori=new ArrayList<>();
				migliori.add(l);	
			}else if( Graphs.neighborListOf(this.grafo, l).size()==numeroVicini) {
				l.setNumerovicini(numeroVicini);
				migliori.add(l);	
			}
		}
		return migliori;
	}

	public List<Location> calcolaPercorso(Location l, String stringa) {
		this.target=l;
		this.dimensionePercorso=0;
		this.stringa=stringa;
		
		List<Location> parziale=new ArrayList<>();
		
		Location partenza=this.getMigliori().get((int)(this.getMigliori().size()*Math.random()));
		
		System.out.println("partenza trovata: "+ partenza);
		ConnectivityInspector<Location, DefaultWeightedEdge> inspector=new ConnectivityInspector<>(this.grafo);
		if(!inspector.connectedSetOf(partenza).contains(target)) {
			System.out.println("SICURAMENTE IMPOSSIBILE RAGGIUNGERE NODO");
		}
		parziale.add(partenza);
		cerca(parziale);
		
		return this.percorso;
		
		
	}

	private void cerca(List<Location> parziale) {
		Location last=parziale.get(parziale.size()-1);
		System.out.println("-----------------");
		System.out.println("inserito last: "+last);
		if(last==this.target) {
			if(parziale.size()>this.dimensionePercorso) {
				this.dimensionePercorso=parziale.size();
				this.percorso=new ArrayList<>(parziale);
				System.out.println("solzuione migliorata");
			}
			return;
		}
		
		List<Location> vicini=Graphs.neighborListOf(this.grafo, last);
		vicini.removeAll(parziale);
		System.out.println("Last ha come vicini: "+vicini);
		
		for(Location l: vicini) {
			if(!l.getName().contains(this.stringa) ||l.equals(this.target)) {
				parziale.add(l);
				cerca(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}
	
	
	
	
	
}
