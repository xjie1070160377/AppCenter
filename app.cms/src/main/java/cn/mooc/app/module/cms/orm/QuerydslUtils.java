package cn.mooc.app.module.cms.orm;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.querydsl.core.SimpleQuery;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;

/**
 * Querydsl工具类
 * 
 * @author csmooc
 * 
 */
public abstract class QuerydslUtils {

	public static <T, Q extends SimpleQuery<Q>> void applySorting(
			SimpleQuery<Q> query, EntityPath<T> entityPath, Sort sort) {
		if (sort == null) {
			return;
		}
		PathBuilder<T> builder = new PathBuilder<T>(entityPath.getType(),
				entityPath.getMetadata());
		for (Order order : sort) {
			query.orderBy(toOrder(builder, order));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static OrderSpecifier<?> toOrder(PathBuilder builder, Order order) {
		Expression<Object> property = builder.get(order.getProperty());
		return new OrderSpecifier(
				order.isAscending() ? com.querydsl.core.types.Order.ASC
						: com.querydsl.core.types.Order.DESC, property);
	}
	
}
