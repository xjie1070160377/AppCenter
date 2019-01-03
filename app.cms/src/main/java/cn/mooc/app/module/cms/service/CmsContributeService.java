package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.CmsContribute;
import cn.mooc.app.module.cms.data.entity.CmsContributeFile;
import cn.mooc.app.module.cms.data.entity.CmsContributeRec;
import cn.mooc.app.module.cms.data.rds.CmsContributeRepository;
import cn.mooc.app.module.cms.support.Uploader;

/**
 * AppContributeServiceImpl
 * 用户投稿信息实现类
 * 
 * @author zjj
 * @date 2016-05-16
 */
@Service
public class CmsContributeService {

	@Resource
	private CmsContributeRepository dao;
	@Autowired
	private CmsContributeFileService fileService;
	@Autowired
	private CmsContributeRecService recService;

	public Page<CmsContribute> findAll(Map<String, Object> searchParams, PagerParam pageParam) {
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CmsContribute> spec = SpecDynamic.buildSpec(filters.values());
		
		return dao.findAll(spec, pageParam.getPageRequest());
	}

	public List<CmsContribute> findList() {
		return dao.findAll();
	}

	public CmsContribute get(Integer id) {
		return dao.findOne(id);
	}
	
	public CmsContribute findByUser(Long userId){
		return dao.findByUser(userId);
	}

	@Transactional
	public CmsContribute save(CmsContribute bean) {
		return dao.save(bean);
	}
	
	@Transactional
	public CmsContribute save(CmsContribute bean, CmsContributeRec rec, String[] files, String[] images){
		dao.save(bean);
		
		rec.setContribute(bean);
		rec.applyDefaultValue();
		recService.save(rec);
		
		List<CmsContributeFile> filelist = new ArrayList<CmsContributeFile>();
		if(files != null && files.length > 0){
			for(String file : files){
				String[] args = file.split(":");
				CmsContributeFile f = new CmsContributeFile();
				f.setFileUrl(args[0]);
				f.setFileSize(StringUtil.strnull2Int(args[1]));
				f.setFileName(args[2]);
				f.setFileType(Uploader.FILE);
				f.setContributeRec(rec);
				filelist.add(f);
			}
		}
		if(images != null && images.length > 0){
			for(String file : images){
				String[] args = file.split(":");
				CmsContributeFile f = new CmsContributeFile();
				f.setFileUrl(args[0]);
				f.setFileSize(StringUtil.strnull2Int(args[1]));
				f.setFileName(args[2]);
				f.setFileType(Uploader.IMAGE);
				f.setContributeRec(rec);
				filelist.add(f);
			}
		}
		if(filelist.size() > 0){
			fileService.save(filelist);
		}
		return bean;
	}

	@Transactional
	public CmsContribute update(CmsContribute bean) {
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