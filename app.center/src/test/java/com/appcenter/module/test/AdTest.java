package com.appcenter.module.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"file:E:/红客资料/AppCenter17/app.center/src/main/resources/context-app.xml",
//		"file:E:/红客资料/AppCenter17/app.center/src/main/resources/context-data.xml",
//		"file:E:/红客资料/AppCenter17/app.center/src/main/resources/context-nosql.xml",
//		"file:E:/红客资料/AppCenter17/app.center/src/main/resources/context-jooq.xml",
//		"file:E:/红客资料/AppCenter17/app.center/src/main/resources/context-shiro.xml"})
public class AdTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		System.out.println("test");
	}

}
