package cn.mooc.app.module.cms.orm;

import org.springframework.data.domain.Sort;

/**
 * 数据库取数及排序接口
 */
public interface Limitable {
	public Integer getFirstResult();

	public Integer getMaxResults();

	public int getLastResult();

	public Sort getSort();

	public void setSort(Sort sort);
}
