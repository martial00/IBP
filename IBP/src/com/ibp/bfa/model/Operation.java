package com.ibp.bfa.model;

public class Operation {
	
	private String date; 
	private String type; 
	private String libelle;
	private double montant;
	private String categorie;
	
	public Operation(String date, String type, String libelle, String categorie, double montant) {
		this.date = date; 
		this.type = type;
		this.libelle = libelle;
		this.montant = montant;
		this.categorie = categorie;

	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	

}
