package cn.mooc.app.core.data.specifications;


/**
 * LessThan, GreaterThan, Like, In, OrderBy, Not,And,Or
 * @author Taven
 *
 */
public enum SpecOperator {
	
	/**
	 * 等于
	 */
	EQ, 
	/**
	 * 模糊匹配
	 */
	LIKE, 
	/**
	 * 大于
	 */
	GT, 
	/**
	 * 小于
	 */
	LT, 
	/**
	 * 大于等于
	 */
	GTE, 
	/**
	 * 小于等于
	 */
	LTE,
	//JqGrid 查询
	/**
	 * contains 包含
	 */
	CN, 
	/**
	 * does not contain 不包含,
	 */
	NC,
	/**
	 * not equal 不等于
	 */
	NE, 
	/**
	 * less or equal 小于等于
	 */
	LE, 
	/**
	 * greater or equal 大于等于
	 */
	GE,
	/**
	 * begins with 以...开始
	 */
	BW,
	/**
	 * does not begin with 不以...开始
	 */
	BN,
	/**
	 * is in, 请传List
	 */
	IN,
	/**
	 * is not in
	 */
	NI,
	/**
	 * ends with 以...结尾
	 */
	EW,
	/**
	 * does not end with 不以...结尾
	 */
	EN,
	/**
	 * is null
	 */
	ISNULL,
	/**
	 * is not null
	 */
	ISNOTNULL,
	/**
	 * is null or equal
	 */
	ISNULLOREQ
}
//JqGrid 可用值：['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
//意 思为['equal','not equal', 'less', 'less or equal','greater','greater or equal', 'begins with','does not begin with','is in','is not in','ends with','does not end with','contains','does not contain']