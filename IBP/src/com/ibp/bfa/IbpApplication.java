package com.ibp.bfa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.ibp.bfa.model.Operation;

public class IbpApplication {
	
	static double soldeFinal = 1487.8;
	static double soldeCPTOctobre =1487.8;
	static double soldePELOctobre =4400;
	static double soldePELFinal = 4400;
	static double cumul =0;
	static int i=0;
	
	public static void main(String[] args) {
		final String FILE_NAME = "C:\\Users\\ndiay\\eclipse-workspace\\IBP\\resources\\csv\\EcritureComptableCPT34319312576.csv";
		final String FILE_NAME_PEL = "C:\\Users\\\\ndiay\\\\eclipse-workspace\\\\IBP\\\\resources\\\\csv\\\\EcritureComptablePEL94461497433.csv";
		List<Operation> operationsCPT = readOperationsFromCSV(FILE_NAME);
		List<Operation> operationPEL =readOperationsFromCSV(FILE_NAME_PEL);
		
		analyseCompteCPT(operationsCPT);
		analyseComptePEL(operationPEL);
		calculDesFrais();
		analyseDepenses(operationsCPT);
	}
	
	private static  List<Operation> readOperationsFromCSV(String fileName) { 
		List<Operation> operations = new ArrayList<>(); 

		try (BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(fileName)))) { 
			br.readLine(); 
			String line = br.readLine(); 
			// loop until all lines are read 
			while (line != null) { 
				String[] ligne = line.split(";"); 
				Operation operation = createOperation(ligne); 
				operations.add(operation); 
				line = br.readLine(); 
			} 
		} catch (IOException ioe) { ioe.printStackTrace(); }
		
		return operations; 
	}
			
	
	
	private static Operation createOperation(String[] ligne) { 
		String date = ligne[0]; 
		String type = ligne[1];
		double montant = Double.parseDouble(ligne[2]); 
		String libelle = ligne[3];  
		String categorie = "Inconnu";
		if(ligne.length>4) {
			categorie = ligne[4];
		}
		return new Operation(date,type,libelle,categorie,montant);
	}
	
	private static void analyseCompteCPT(List<Operation> operations ) {
		System.out.println("Compte de Mr CPT34319312576");
		System.out.println("Solde en début de mois = "+soldePELOctobre+ " euros");
		List<Operation> sortedList = operations.stream()
				.sorted(Comparator.comparingDouble(Operation::getMontant))
				.sorted(Comparator.comparing(Operation::getType))
				.collect(Collectors.toList());
		sortedList.forEach(op -> {
		    System.out.println(op.getDate() +","+op.getType()+", "+op.getMontant()+" , "+op.getLibelle()+","+op.getCategorie());
		    if(op.getType().equalsIgnoreCase("C")) {
		    	soldeFinal = soldeFinal + op.getMontant();
		    }
		    if(op.getType().equalsIgnoreCase("D")) {
		    	soldeFinal = soldeFinal - op.getMontant();
		    }
		});
		System.out.println("\nSolde en début de mois = "+soldeFinal+ " euros");
		System.out.println("_________________________________________________");
		
	}
	
	private static void analyseComptePEL(List<Operation> operations ) {
		System.out.println("PEL  PEL94461497433");
		System.out.println("Solde en début de mois = "+soldePELOctobre+ " euros");
		List<Operation> sortedList = operations.stream()
				.sorted(Comparator.comparingDouble(Operation::getMontant))
				.sorted(Comparator.comparing(Operation::getType))
				.collect(Collectors.toList());
		sortedList.forEach(op -> {
		    System.out.println(op.getDate() +","+op.getType()+", "+op.getMontant()+" , "+op.getLibelle()+","+op.getCategorie());
		    if(op.getType().equalsIgnoreCase("C")) {
		    	soldePELFinal = soldePELFinal + op.getMontant();
		    }
		    if(op.getType().equalsIgnoreCase("D")) {
		    	soldePELFinal = soldePELFinal - op.getMontant();
		    }
		});
		System.out.println("Vos droits à crédit s'élèvent à : "+ (soldePELFinal - soldePELOctobre)*0.002);
		System.out.println("\nSolde en début de mois = "+soldePELFinal+ " euros");
		System.out.println("_________________________________________________");
	}
	
	private static  void analyseDepenses(List<Operation> operations ) {
		String[] listeCategorie = {"factures", "autres","Loisir","Abonnement","shopping","Nourriture"};
		System.out.println("Analyse Depense compte courant");
		for (i=0; i< listeCategorie.length ; i++) {
			cumul=0;
			List<Operation> sortedList = operations.stream()   
            .filter(line -> line.getCategorie().equalsIgnoreCase(listeCategorie[i]))
            .collect(Collectors.toList());
			sortedList.forEach(e -> cumul = cumul+ e.getMontant());
			System.out.println(listeCategorie[i]+" , nombres d'opérations :"+ sortedList.size() +", montant total : "+cumul);
		}

	}
	
	
	
	private static void calculDesFrais() {
		System.out.println("Frais bancaire du mois prochain");
		System.out.println("les frais pour le compte CPT34319312576 seront de : "+(soldeFinal*0.005)+"euros");
		System.out.println("les frais pour le compte CPT34319312576 seront de : 0.0euros");
		System.out.println("_________________________________________________");
	}
	
}
