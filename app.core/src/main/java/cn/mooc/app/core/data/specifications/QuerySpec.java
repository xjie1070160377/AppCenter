package cn.mooc.app.core.data.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


public class QuerySpec<T> {
	
	private Specification<T> baseSpecification;
	
	public QuerySpec(Specification<T> spec) {
		
		baseSpecification = emptySpec();
		
	}
	
	private Specification<T> emptySpec() {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				return cb.conjunction();
			}
		};
	}
	
}
