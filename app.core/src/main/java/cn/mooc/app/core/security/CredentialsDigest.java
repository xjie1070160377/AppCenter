package cn.mooc.app.core.security;

public interface CredentialsDigest {
	
	public byte[] getSaltBytes(String salt);
	
	public String digest(String plainCredentials, byte[] salt);

	public boolean matches(String credentials, String plainCredentials, byte[] salt);
	
	
}
