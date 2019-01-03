package cn.mooc.jooq.demo;

import org.jooq.util.GenerationTool;

public class JooQGenerationApp {

    public static void main( String[] args )
    {
    	try {
    		//
			//GenerationTool.main(new String[] { "library_mall.xml" });
			GenerationTool.main(new String[] { "library_sys.xml" });
			//GenerationTool.generate(xml);
			//GenerationTool.main(configuration);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
        System.out.println( "Generation完成" );
    }
    
    
}
