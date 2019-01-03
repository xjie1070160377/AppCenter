package cn.mooc.app.module.api;

import org.junit.runner.RunWith;

import cn.mooc.app.module.api.utils.MobileRSA;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */

//@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
//@ContextConfiguration(locations={"classpath:applicationContext.xml"}) 
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp(){
    		MobileRSA rsaUtil = new MobileRSA();
    		String username ="qGqUPcj7ePULrWtkxYWtDJ%2BxTGBvPHdkZzaV593VBReVMns%2BZ3Lv6YFcvOWjJTeVNczvL3c9NSEPOPylJliSKDzbU3rWYTW0HphhMNi%2Fdvd3dxB9q46yu3i5E5PrB6ehnsqGCxg6Sw89uTurS8CiZC9vxjDLc9YnxIS4Y9eKUoQ%3D";
    	    String password = "W2IVwyh629TXSB8kfTRm8jPZEyMce0OREl4niV179WOThAzfiUQS2g5%2BCyLPjUW7S2txcrYroPRrRWI5VaNnr%2FPKz19ZNpXocWVkKpBMXLmffDEaC7CEkt30Xxe0n66BFyRym6aNjvczGOFiZloDz4JMASEKI1JCfyDU1cbmxzM%3D";
//    	    String username ="xiangjie";
//    	    String password = "1qazxsw2";

    	    String ausername = "", apassword = "";
		try{
			ausername = rsaUtil.decrypt("qGqUPcj7ePULrWtkxYWtDJ%2BxTGBvPHdkZzaV593VBReVMns%2BZ3Lv6YFcvOWjJTeVNczvL3c9NSEPOPylJliSKDzbU3rWYTW0HphhMNi%2Fdvd3dxB9q46yu3i5E5PrB6ehnsqGCxg6Sw89uTurS8CiZC9vxjDLc9YnxIS4Y9eKUoQ%3D");
			
			apassword = rsaUtil.decrypt("W2IVwyh629TXSB8kfTRm8jPZEyMce0OREl4niV179WOThAzfiUQS2g5%2BCyLPjUW7S2txcrYroPRrRWI5VaNnr%2FPKz19ZNpXocWVkKpBMXLmffDEaC7CEkt30Xxe0n66BFyRym6aNjvczGOFiZloDz4JMASEKI1JCfyDU1cbmxzM%3D");
			
		}catch(Exception e){
			try{
				ausername = rsaUtil.olddecrypt(username);
				
				apassword = rsaUtil.olddecrypt(password);
			}catch(Exception ex){
//				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("安全校验失败！！")));
//				return;
				ausername = username;
				apassword = password;
			}
		}
		System.out.println(ausername);
		System.out.println(apassword);
    }
}
