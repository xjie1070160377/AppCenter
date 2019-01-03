package cn.mooc.jooq.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Java8Linq {

	public static void main(String[] args) {
		
		List<String> stringCollection = new ArrayList<String>();
		stringCollection.add("aaa");
		stringCollection.add("bbaa");
		stringCollection.add("caca");
		stringCollection.add("da11");
		
		Object[] result = stringCollection.stream().filter(s -> s.startsWith("a")).map(s -> s.toString()).toArray();
		System.out.println(result);
		
		
		List<PersonInfo> personInfos = new ArrayList<PersonInfo>();
		personInfos.add(new PersonInfo(0, 0, "age0"));
		personInfos.add(new PersonInfo(1, 19, "小明"));
		personInfos.add(new PersonInfo(2, 30, "哈哈！"));
		personInfos.add(new PersonInfo(3, 65, "有戏！"));
		personInfos.add(new PersonInfo(3, 65, "哈哈！"));
		personInfos.add(new PersonInfo(3, 65, "有戏！"));
		personInfos.add(new PersonInfo(3, 65, "哈哈！"));
		personInfos.add(new PersonInfo(3, 65, "有戏！"));
		personInfos.add(new PersonInfo(3, 65, "有戏！"));
		personInfos.add(new PersonInfo(4, 70, "小妹妹"));
		
		
		personInfos.stream().filter(p -> {return p.getId() > 1; }).forEach(System.out::println);
		
		personInfos.stream().filter(p->p.getName().contains("！")).skip(1).limit(3).forEach(p->{System.out.println(p.getName());});
		
		Map<Integer, List<PersonInfo>> nameofAge1 = personInfos.stream().collect(Collectors.groupingBy(PersonInfo::getAge));
		
		System.out.println(nameofAge1);
		
		Map<Integer, List<String>> nameofAge2 = personInfos.stream().collect(Collectors.groupingBy(PersonInfo::getAge,Collectors.mapping(PersonInfo::getName,Collectors.toList())));
		
		System.out.println(nameofAge2);
		
				
		Collectors.groupingBy(p->p);
		
		personInfos.parallelStream().filter(p->p.getAge()>=20).collect(Collectors.groupingBy(p->p.getAge(), Collectors.summingInt(p->1)));
		
		personInfos.stream().collect(Collectors.groupingBy(p->p.getAge()));
		
		
		
		
	}
	
	
}
