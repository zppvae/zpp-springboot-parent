package org.zpp.springboot.ldap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.zpp.springboot.ldap.dao.PersonRepository;
import org.zpp.springboot.ldap.model.Person;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LdapApplicationTests {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LdapTemplate ldapTemplate;

    @Test
    public void add() {
        Person person = new Person();
        person.setUid("uid:13");
        person.setSuerName("AAA");
        person.setCommonName("aaa");
        person.setUserPassword("123456");
        ldapTemplate.create(person);

    }

    @Test
    public void search() {
        String filter = "(&(objectclass=inetOrgPerson))";
        List<Person> list = ldapTemplate.search("ou=people",filter, new AttributesMapper() {
            @Override
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                Person p = new Person();

                Attribute a = attributes.get("cn");
                if (a != null) p.setCommonName((String) a.get());

                a = attributes.get("uid");
                if (a != null) p.setUid((String) a.get());
                return p;
            }
        });

        list.stream().forEach(p -> {
            System.out.println(p);
        });
    }

    @Test
    public void update(){
        Person person = new Person();
        person.setUid("uid:13");
        person.setSuerName("bbb");
        person.setCommonName("bbb");
        person.setUserPassword("123456");
        ldapTemplate.update(person);
    }
}
