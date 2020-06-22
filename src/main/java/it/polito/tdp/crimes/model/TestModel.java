package it.polito.tdp.crimes.model;

public class TestModel {

	public static void main(String[] args) {
		
		
		Model model = new Model();
		
		model.creaGrafo(2014);
		
		System.out.println("Vertici: "+model.getNumeroVertici()+" Archi: "+model.getNumeroArchi());
		
	}

}
