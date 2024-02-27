package io.black.crud.dao;

import java.util.List;

import io.black.crud.entities.Person;

public interface PersonDAO {
    public void save(Person person);
    public void update(Person person);
    public int updateMany(String updateColumn, String updateValue, String whereColumn, String whereValue);
    public void delete(long id);
    public int deleteMany(String whereColumn, String whereValue);
    public int deleteAll();
    public Person findById(long id);
    public List<Person> findAll();
    public List<Person> findByLastName(String lastName);
}
