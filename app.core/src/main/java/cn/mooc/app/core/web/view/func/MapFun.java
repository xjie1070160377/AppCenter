package cn.mooc.app.core.web.view.func;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * <pre>
 * var mapObj = MapFun(); //创建一个新的Map对象
 * MapFun(mapObj,"key") == true //判断Key是否存在
 * MapFun(mapObj,"key","123")  //插入kv
 * MapFun(mapObj,"key","", false) //删除key
 * mapObj["key"]  //取值
 * 
 * 或者直接用@符号调用java方法：
 * @mapObj.put("key","value");
 * @mapObj.containsKey("key");
 * @mapObj.get("key");
 * @mapObj.remove("key");
 * 
 * </pre>
 * 
 * @author Taven
 *
 */
public class MapFun implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		int length = paras.length;
		if(length==0){
			return new LinkedHashMap<Object, Object>();
		}
		
		Map map = (Map) paras[0];
		if(length==2){			
			return map.containsKey(paras[1]);
		}else if(length==3){
			map.put(paras[1], paras[2]);
		}else if(length==4){
			//如果 MapFun(mapObj,"key","",true); 表示覆盖一个key，相当于传3个参数
			//如果 MapFun(mapObj,"key","",false); 表示删除一个key
			if(paras[3].equals(false)){
				map.remove(paras[1]);
			}else{
				map.put(paras[1], paras[2]);
			}
		}
				
		
		return null;
	}

}
