package cn.mooc.app.module.cms.service;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.model.LdapPerson;
import cn.mooc.app.core.service.LdapLoginService;

@Service
public class NudtLdapLoginService  {

	@Value("${ldap.properties.url}")
	private String url;
	@Value("${ldap.properties.security_authentication}")
	String authentication;
	@Value("${ldap.properties.dn}")
	String dn;
	@Value("${ldap.properties.object_class}")
	String objectClass;
	@Value("${ldap.properties.usernameKey}")
	String usernameKey;
	@Value("${ldap.properties.displayNameKey}")
	String displayNameKey;
	
	
	
	
	public LdapPerson login(String username, String password) {
		LdapPerson person = new LdapPerson();
		person.setUsername(username);
		person.setPassword(password);

		String principal = dn.replace(usernameKey, username);

		DirContext ctx = null;

		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_AUTHENTICATION, authentication);
		env.put(Context.SECURITY_PRINCIPAL, principal);
		env.put(Context.SECURITY_CREDENTIALS, password);

		try {
			//
			ctx = new InitialDirContext(env);
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			//设置超时时间
			//controls.setTimeLimit(1000*5);
			NamingEnumeration results = ctx.search(principal, objectClass, controls);

			// while (results.hasMore()) {
			while (results.hasMoreElements()) {
				SearchResult searchResult = (SearchResult) results.next();
				Attributes attributes = searchResult.getAttributes();
				Attribute attr = attributes.get(displayNameKey);
				String displayName = attr.get().toString();
				person.setDisplayName(displayName);
			}
			person.setLoginSuccess(true);
		} catch (AuthenticationException e) {
			person.setLoginSuccess(false);
//			e.printStackTrace();
		} catch (Exception e) {
			person.setLoginSuccess(false);
//			e.printStackTrace();
		} finally {
			try {
				if (ctx != null) {
					ctx.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return person;
	}

}
