package cn.mooc.app.module.push.service;

import java.util.List;

import cn.mooc.app.module.push.data.entity.IosToken;

/**
 * IosTokenService
 * IOS设备token接口
 * 
 * @author zjj
 * @date 2016-03-16
 */
public interface IosTokenService {

	
	public List<IosToken> findList();
	
	public List<IosToken> findIosList();
	
	public IosToken get(Long id);

	public IosToken save(IosToken bean);
	
	public List<IosToken> findByToken(String token);
	
	public IosToken update(IosToken bean);

	public void delete(Long... id);
	
	public IosToken getTokenByUserCode(String userCode);
}
