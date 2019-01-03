package cn.mooc.app.core.security;

import cn.mooc.app.core.utils.Digests;

public class SHA1CredentialsDigest extends HashCredentialsDigest {
	@Override
	protected byte[] digest(byte[] input, byte[] salt) {
		return Digests.sha1(input, salt, HASH_INTERATIONS);
	}

	@Override
	public byte[] getSaltBytes(String salt) {
		byte[] saltBytes = SaltPwdUtils.getSaltBytes(salt);
		return saltBytes;
	}
	
	
}
