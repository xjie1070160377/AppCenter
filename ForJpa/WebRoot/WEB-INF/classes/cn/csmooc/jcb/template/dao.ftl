package ${packageName}.data.rds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ${packageName}.data.entity.${baseName};

/**
 * ${baseName}Repository
 * ${manager}数据访问
 * 
 * @author ${author}
 * @date ${newDate?string("yyyy-MM-dd")}
 */
public interface ${baseName}Repository extends JpaRepository<${baseName}, ${pkType}>,JpaSpecificationExecutor<${baseName}>{
	
}
