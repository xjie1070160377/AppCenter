package cn.mooc.app.core.security;

import org.apache.shiro.authc.credential.CredentialsMatcher;

public interface CredentialsDigestMatcher extends CredentialsMatcher {
	
	public CredentialsDigest getDigest();
	
}
