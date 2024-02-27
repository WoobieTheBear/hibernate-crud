package io.black.crud;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import io.black.crud.dao.PersonDAO;
import io.black.crud.entities.Person;

@SpringBootApplication
public class CrudApplication {
	ArrayList<Long> peopleIds;
	ArrayList<Person> people;

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PersonDAO personDAO){
		return runner -> {
			this.people = new ArrayList<>();
			this.peopleIds = new ArrayList<>();
			this.readDataSetup();
			this.createPerson(personDAO);
			this.readPerson(personDAO);
			this.readAllEntries(personDAO);
			this.readByLastName(personDAO);
			this.updateCompany(personDAO);
			this.updateCompanyMany(personDAO);
			this.deletePerson(personDAO);
			this.deleteManyPeople(personDAO);
			this.deleteAllPeople(personDAO);
		};
	}

	private void readDataSetup(){
        try {
            File file = ResourceUtils.getFile("classpath:files/people.json");
            InputStream inputStream = new FileInputStream(file);
            JSONParser parser = new JSONParser(inputStream);
            LinkedHashMap<String, Object> json = parser.object();
            List<LinkedHashMap<String, Object>> staff = (List<LinkedHashMap<String, Object>>) json.get("data");
            for( LinkedHashMap<String, Object> personEntry : staff ){
                Person localEntry = new Person( 
					(String) personEntry.get("firstName"), 
					(String) personEntry.get("lastName"), 
					(String) personEntry.get("email"),
					(String) personEntry.get("company")
				);
                this.people.add(localEntry);
            }
        } catch (Exception e) {
            System.out.println( "afterInitialization() OOOPS: " + e.getMessage());
        }
	}

	private void createPerson(PersonDAO personDAO){
		System.out.println("[INFO]: Starting persistence process");
		for( Person person : this.people ){
			this.savePerson(personDAO, person);
		}
	}

	private void savePerson(PersonDAO personDAO, Person person) {
		System.out.println("[INFO]: Person created " + person);
		personDAO.save(person);
		System.out.println("[INFO]: Person persisted has id " + person.getId() );
		this.peopleIds.add(person.getId());
	}


	private void readPerson(PersonDAO personDAO){
		System.out.println("[INFO]: Starting query process");
		Person temp = personDAO.findById(this.peopleIds.get(0));
		System.out.println("[INFO]: Person read first " + temp);
	}

	private void readAllEntries(PersonDAO personDAO){
		List<Person> allPeople = personDAO.findAll();
		for(Person person : allPeople){
			System.out.println("[INFO]: Person all " + person);
		}
		if (allPeople.size() < 1){
			System.out.println("[INFO]: No results found");
		}
	}

	private void readByLastName(PersonDAO personDAO){
		List<Person> allPeople = personDAO.findByLastName( "Wayne");
		for(Person person : allPeople){
			System.out.println("[INFO]: Person by last name " + person);
		}
	}

	private void updateCompany(PersonDAO personDAO){
		Person person = personDAO.findById(this.peopleIds.get(0));
		person.setCompany("The Joker Inc.");
		personDAO.update(person);
		System.out.println("[INFO]: Updated company " + person);
	}

	private void updateCompanyMany(PersonDAO personDAO){
		String updateColumn = "company", updateValue = "Bat Cave Inc.", whereColumn = "lastName", whereValue = "Wayne";
		int updated = personDAO.updateMany(updateColumn, updateValue, whereColumn, whereValue);
		System.out.println("[INFO]: Updated "  + updated + " rows");
		this.readAllEntries(personDAO);
	}

	private void deletePerson(PersonDAO personDAO){
		long deletedId = peopleIds.get(0);
		personDAO.delete(deletedId);
		peopleIds.remove(deletedId);
		System.out.println("[INFO]: Deleted "  + deletedId);
		this.readAllEntries(personDAO);
	}

	private void deleteManyPeople(PersonDAO personDAO){
		String whereColumn = "lastName", whereValue = "Wayne";
		int updated = personDAO.deleteMany(whereColumn, whereValue);
		System.out.println("[INFO]: Deleted "  + updated + " rows");
		this.readAllEntries(personDAO);
	}

	private void deleteAllPeople(PersonDAO personDAO){
		int updated = personDAO.deleteAll();
		System.out.println("[INFO]: Deleted all "  + updated + " rows");
		this.readAllEntries(personDAO);
	}
}
