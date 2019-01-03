package cn.mooc.app.module.push.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.mooc.app.module.push.data.entity.IosMessagePedding;

/**
 * IosMessagePeddingService
 * Ios正在处理的消息接口
 * 
 * @author zjj
 * @date 2016-03-29
 */
public interface IosMessagePeddingService {

	
	public List<IosMessagePedding> findByCount(int count);
	
	public List<IosMessagePedding> findList();

	public IosMessagePedding get(Integer id);

	public IosMessagePedding save(IosMessagePedding bean);
	
	public IosMessagePedding update(IosMessagePedding bean);

	public void delete(Integer... id);
}
