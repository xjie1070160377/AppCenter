package ${packageName}.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${packageName}.data.entity.${baseName};
import ${packageName}.data.rds.${baseName}Repository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * ${baseName}Service
 * ${manager}服务
 * 
 * @author ${author}
 * @date ${newDate?string("yyyy-MM-dd")}
 */
@Service
public class ${baseName}Service {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ${baseName}Repository ${entity}Repository;
	
	public ${baseName} get${baseName}ById(${pkType} id) {
		${baseName} entity = ${entity}Repository.findOne(id);
		return entity;
	}
	
	public List<${baseName}> getAll${baseName}s(){
		return this.${entity}Repository.findAll();
	}
	
	public Page<${baseName}> find${baseName}Page(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<${baseName}> spec = SpecDynamic.buildSpec(filters.values());

		return ${entity}Repository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public ${baseName} save${baseName}(${baseName} entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.${entity}Repository.save(entity);
	}

	@Transactional
	public ${baseName} update${baseName}(${baseName} entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.${entity}Repository.save(entity);
	}
	
	@Transactional
	public boolean del${baseName}(${pkType} id){
		try{
			this.${entity}Repository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int del${baseName}s(${pkType}[] ids){
		try{
			List<${baseName}> entities = this.${entity}Repository.findAll(Arrays.asList(ids));
			this.${entity}Repository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
