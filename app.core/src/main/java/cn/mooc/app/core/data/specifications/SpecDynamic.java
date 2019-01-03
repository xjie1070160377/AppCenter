package cn.mooc.app.core.data.specifications;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

/**
 * 
 * 其它使用，可参考 Specifications、QueryDslJpaRepository、JpaSort 
 * 
 * 另外也可以参考 Criteria 和 Querydsl
 * @author Taven
 *
 */
public class SpecDynamic {

	public static <T> Specifications<T> specAnd(Specification<T> first,Specification<T> second){
		return Specifications.where(first).and(second);
	}
	
	public static <T> Specifications<T> specOr(Specification<T> first,Specification<T> second){
		return Specifications.where(first).or(second);
	}
	public static <T> Specification<T> buildSpec(final Map<String, Object> searchParams) {
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		return buildSpec(filters.values(), true);
	}
	
	public static <T> Specification<T> buildSpec(final Collection<SpecExpression> filters) {
		return buildSpec(filters, true);
	}	
	public static <T> Specification<T> buildSpec(final Collection<SpecExpression> filters, final boolean useAnd) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				if (filters!=null && filters.size()>0) {

					List<Predicate> predicates = new ArrayList<Predicate>();
					for (SpecExpression filter : filters) {
						// nested path translate, 如Role的名为"user.name"的filedName, 转换为Role.user.name属性
						String[] names = StringUtils.split(filter.getFieldName(), ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}

						// logic operator
						switch (filter.getOperator()) {
						case EQ:
							predicates.add(cb.equal(expression, filter.getFieldVal()));
							break;
						case GT:
							predicates.add(cb.greaterThan(expression, (Comparable) filter.getFieldVal()));
							break;
						case LT:
							predicates.add(cb.lessThan(expression, (Comparable) filter.getFieldVal()));
							break;
						case LIKE:
//							predicates.add(cb.like(expression, "%" + filter.getFieldVal() + "%"));
//							break;
						case CN:
							predicates.add(cb.like(expression, "%" + filter.getFieldVal() + "%"));
							break;
						case NC:
							predicates.add(cb.notLike(expression, "%" + filter.getFieldVal() + "%"));
							break;
						case NE:
							predicates.add(cb.notEqual(expression, filter.getFieldVal()));
							break;
						case LTE:
//							predicates.add(cb.lessThanOrEqualTo(expression, (Comparable) filter.getFieldVal()));
//							break;
						case LE:
							predicates.add(cb.lessThanOrEqualTo(expression, (Comparable) filter.getFieldVal()));
							break;
						case GTE:
//							predicates.add(cb.greaterThanOrEqualTo(expression, (Comparable) filter.getFieldVal()));
//							break;
						case GE:
							predicates.add(cb.greaterThanOrEqualTo(expression, (Comparable) filter.getFieldVal()));
							break;
						case BW:
							predicates.add(cb.like(expression, filter.getFieldVal() + "%"));
							break;
						case BN:
							predicates.add(cb.notLike(expression, filter.getFieldVal() + "%"));
							break;
						case EW:
							predicates.add(cb.like(expression, "%" + filter.getFieldVal()));
							break;
						case EN:
							predicates.add(cb.notLike(expression, "%" + filter.getFieldVal()));
							break;
						case IN:
							predicates.add(cb.in(expression).value(filter.getFieldVal()));
							break;
						case NI:
							predicates.add(cb.not(cb.in(expression).value(filter.getFieldVal())));
							break;
						case ISNULL:
							predicates.add(cb.isNull(expression));
							break;
						case ISNOTNULL:
							predicates.add(cb.isNotNull(expression));
							break;
						case ISNULLOREQ:
							predicates.add(cb.or(cb.isNull(expression), cb.equal(expression, filter.getFieldVal())));
							break;
						}
					}
					
					

					// 将所有条件用 and 联合起来
					if (predicates.size() > 0) {
						if(useAnd){
							return cb.and(predicates.toArray(new Predicate[predicates.size()]));
						}else{
							return cb.or(predicates.toArray(new Predicate[predicates.size()]));
						}
						
					}
				}

				return cb.conjunction();
				
			}
			
		};
	}
	
	
}
