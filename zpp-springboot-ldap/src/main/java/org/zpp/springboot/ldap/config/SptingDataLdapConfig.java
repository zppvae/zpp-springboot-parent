package org.zpp.springboot.ldap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@EnableLdapRepositories
public class SptingDataLdapConfig {

	@Bean
	ContextSource contextSource() {

		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setBase("dc=my-domain,dc=com");
		ldapContextSource.setUrl("ldap://192.168.75.130:389");
		ldapContextSource.setUserDn("cn=Manager,dc=my-domain,dc=com");
		ldapContextSource.setPassword("123456");

		return ldapContextSource;
	}

	@Bean
	LdapTemplate ldapTemplate(ContextSource contextSource) {
		return new LdapTemplate(contextSource);
	}

}