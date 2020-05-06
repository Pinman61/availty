package com.availty.exercises;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CSVProcessor {
	
	public static void main(String[] args) {
		//read all persons from input file
		List<Person> allPersons = processInputFile(args[0]);
		
		//sort csv contents by company
		List<Person> personsSortedByCompany = allPersons.stream()
			      .sorted((e1, e2) -> e1.getCompany().compareTo(e2.getCompany()))
			      .collect(Collectors.toList());
		
		// seperate list by company into map<company, List<Person>> using Collectors.groupingBy 
		Map<String, List<Person>> groupByCompany = personsSortedByCompany.stream()
				.collect(Collectors.groupingBy(e -> new String(e.getCompany())));

		// sorts each company's persons by userid,version,lastname,firstname ascending
		for (Map.Entry<String, List<Person>> me : groupByCompany.entrySet()) { 
			String key = me.getKey();
			List<Person> personList = me.getValue();
			personList.sort(Comparator.comparing(Person::getUsrId)
					.thenComparing(Person::getVersion)
					.thenComparing(Person::getLastName)
                    .thenComparing(Person::getFirstName));
			
		}
		
		/* 
		  strips out duplicates within company personlists by usrid,version keeping only the person with highest version number
		  and replaces the map.value with de-duped list 
		*/
		for (Map.Entry<String, List<Person>> me : groupByCompany.entrySet()) { 
			String key = me.getKey();
			List<Person> personList = me.getValue();
			List<Person> personNoDuplicates = new ArrayList<Person>();
			Person lastPerson = null;
			for (Person person : personList) {
				if (lastPerson != null) {
					if (person.getUsrId() != lastPerson.getUsrId()) {
						personNoDuplicates.add(lastPerson);
					}	
				}
				lastPerson = person;	
			}
			if (lastPerson != null) {
				personNoDuplicates.add(lastPerson);
			}
			me.setValue(personNoDuplicates);
		}

		/*
		 print contents of file
		*/
		for (Map.Entry<String, List<Person>> me : groupByCompany.entrySet()) { 
			String key = me.getKey();
			List<Person> personList = me.getValue();
			System.out.println(key); //print companyname
			for (Person person : personList) {
				System.out.println(person.toString()); //print each persons data
			}
		}
	}

	public static List<Person> processInputFile(String inputFilePath) {
	    
		List<Person> inputList = new ArrayList<Person>();
			    
	    try{
	      File inputF = new File(inputFilePath);
	      InputStream inputFS = new FileInputStream(inputF);
	      BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
	      // skip the csv header row
	      inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
	      br.close();
	    } catch (IOException e) {
	      
	    }
	    return inputList ;
	}

	private static Function<String, Person> mapToItem = (line) -> {
		  String[] p = line.split(",");
		  
		  Person person = new Person();
		  
		  person.setUsrId(removeQualifiers(p[0]));
		  String name = removeQualifiers(p[1]);
		  person.setName(name);//retain whole name
		  if (name.length() > 0) {
			  String[] q = name.split(" ");
			  person.setFirstName(q[0]);
			  person.setLastName(q[1]);
		  } else {
			  person.setFirstName("");
			  person.setLastName("");
		  }
		  person.setVersion(Integer.parseInt(removeQualifiers(p[2])));
		  person.setCompany(removeQualifiers(p[3]));
	  
		  return person;
	};
	
	private static String removeQualifiers(String input) {
		return input.replaceAll("^\"|\"$", "");
	}
	
	public static void sortPersonsByCompany(List<Person> personList) {
	    for (int i = 0; i < personList.size(); i++) {
	        for (int j = 0; j < personList.size(); j++) {
	            Collections.sort(personList, new Comparator<Person>() {
	                public int compare(Person p1, Person p2) {
	                    return p1.getCompany().compareToIgnoreCase(p2.getCompany());
	                }
	            });
	        }
	    }
	}

}