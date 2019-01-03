package cn.mooc.app.core.security;

import org.apache.commons.lang3.StringUtils;

import cn.mooc.app.core.utils.SecureCryptoUtil;

public class MD5CredentialsDigest implements CredentialsDigest {

	@Override
	public String digest(String plainCredentials, byte[] salt) {
		//
		return SecureCryptoUtil.md5Password(plainCredentials, new String(salt));
	}

	@Override
	public boolean matches(String credentials, String plainCredentials, byte[] salt) {
		if (StringUtils.isBlank(credentials) && StringUtils.isBlank(plainCredentials)) {
			return true;
		}
		return StringUtils.equals(credentials, digest(plainCredentials, salt));
	}

	@Override
	public byte[] getSaltBytes(String salt) {
		byte[] saltBytes = SaltPwdUtils.getSaltBytes(salt);
		return saltBytes;
	}

}
