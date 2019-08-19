package org.zpp.springboot.ldap.dao;

import org.springframework.data.repository.CrudRepository;
import org.zpp.springboot.ldap.model.Person;

import javax.naming.Name;

public interface PersonRepository extends CrudRepository<Person, Name> {

}