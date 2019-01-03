package cn.mooc.app.core.data.specifications;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.loader.custom.Return;
import org.springframework.data.mongodb.core.query.Criteria;

import ch.qos.logback.access.sift.AccessEventDiscriminator.FieldName;

public class CriteriaExpression {

	public static Criteria parse(Map<String, Object> searchParams) {
		Criteria criteria = new Criteria();
		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value==null || StringUtils.isBlank(value.toString())) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			
			String filedName = names[1];
			SpecOperator operator = SpecOperator.valueOf(names[0]);
			criteria = getCriteria(criteria, filedName, operator, value);
			/*if(_criteria!=null){
				//return _criteria;
				criteria.andOperator(_criteria);
			}*/
			
		}
		
		return criteria;
		
	}
	
	public static Criteria getCriteria(Criteria criteria, String filedName, SpecOperator operator, Object filedValue){
		switch (operator) {
			case EQ:
				//return Criteria.where(filedName).is(filedValue);
				return criteria.and(filedName).is(filedValue);
			case LIKE:
				return criteria.and(filedName).regex(".*" + filedValue + ".*");
				//return criteria.and(filedName).regex(".*?\\" + filedValue + ".*");
			case GT:
				return criteria.and(filedName).gt(filedValue);
			case LT:
				return criteria.and(filedName).lt(filedValue);
			case GE:
			case GTE:
				return criteria.and(filedName).gte(filedValue);
			case LE:
			case LTE:
				return criteria.and(filedName).lte(filedValue);
			case NE:
				return criteria.and(filedName).ne(filedValue);
			case IN:
				return criteria.and(filedName).in((Object[])filedValue);
			case NI:
				return criteria.and(filedName).nin((Object[])filedValue);
			case ISNOTNULL:
				return criteria.and(filedName).exists((boolean)filedValue);
			default:
				break;
		}
		
		return criteria;
	}
	
}
