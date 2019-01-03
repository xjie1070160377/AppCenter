package cn.mooc.app.module.push.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.mooc.app.module.push.data.entity.IosMessageError;

/**
 * IosMessageErrorService
 * Ios处理失败的消息接口
 * 
 * @author zjj
 * @date 2016-03-29
 */
public interface IosMessageErrorService {
	
	public List<IosMessageError> findList();

	public IosMessageError get(Integer id);

	public IosMessageError save(IosMessageError bean);
	
	public List<IosMessageError> save(List<IosMessageError> beans);
	
	public IosMessageError update(IosMessageError bean);

	public void delete(Integer... id);
}
