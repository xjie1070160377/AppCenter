package cn.mooc.app.core.security;

import org.apache.commons.lang3.StringUtils;

import cn.mooc.app.core.utils.Cryptos;
import cn.mooc.app.core.utils.Encodes;

public abstract class HashCredentialsDigest implements CredentialsDigest {

	public static final int HASH_INTERATIONS = 1024;

	public String digest(String plainCredentials, byte[] salt) {
		if (StringUtils.isBlank(plainCredentials)) {
			return null;
		}
		byte[] hashPassword = digest(Cryptos.utf8encode(plainCredentials), salt);
		return Encodes.encodeHex(hashPassword);
	}

	public boolean matches(String credentials, String plainCredentials, byte[] salt) {
		if (StringUtils.isBlank(credentials)
				&& StringUtils.isBlank(plainCredentials)) {
			return true;
		}
		return StringUtils.equals(credentials, digest(plainCredentials, salt));
	}

	protected abstract byte[] digest(byte[] input, byte[] salt);
	
}
