package cn.mooc.app.module.push.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.module.push.data.entity.IosMessageError;
import cn.mooc.app.module.push.data.rds.IosMessageErrorDao;
import cn.mooc.app.module.push.service.IosMessageErrorService;

/**
 * IosMessageErrorServiceImpl
 * Ios处理失败的消息实现类
 * 
 * @author zjj
 * @date 2016-03-29
 */
@Service
@Transactional(readOnly = true)
public class IosMessageErrorServiceImpl implements IosMessageErrorService {

	@Resource
	private IosMessageErrorDao dao;


	public List<IosMessageError> findList() {
		return dao.findAll();
	}

	public IosMessageError get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public IosMessageError save(IosMessageError bean) {
		return dao.save(bean);
	}
	
	@Transactional
	public List<IosMessageError> save(List<IosMessageError> beans){
		return dao.save(beans);
	}

	@Transactional
	public IosMessageError update(IosMessageError bean) {
		return dao.save(bean);
	}

	@Transactional
	public void delete(Integer... ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.delete(id);
			}
		}
	}
}