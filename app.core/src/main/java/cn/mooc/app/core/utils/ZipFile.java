package cn.mooc.app.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipFile {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    private File targetDir;

    private File zipFile;
    
    public ZipFile(File zipFile, File targetDir) {
        this.zipFile = zipFile;
        this.targetDir = targetDir;
    }
    
    public void extract() throws IOException {
    	logger.debug("Extract content of '{}' to '{}'", zipFile, targetDir);

        // delete destination file if exists
        removeDirectory(targetDir);

    	ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile), Charset.forName("GBK"));
	    ZipEntry zipEntry = null;

    	while ((zipEntry = zipInputStream.getNextEntry()) != null) {
	        try {
		        File file = new File(targetDir, zipEntry.getName());

        		// create intermediary directories - sometimes zip don't add them
        		File dir = new File(file.getParent());
        		dir.mkdirs();

        		if (zipEntry.isDirectory()) {
		            file.mkdirs();
        		} else {
		            byte[] buffer = new byte[1024];
        		    int length = 0;
		            FileOutputStream fos = new FileOutputStream(file);

        		    while ((length = zipInputStream.read(buffer)) >= 0) {
		            	fos.write(buffer, 0, length);
        		    }

		            fos.close();
                }
    	    } catch (FileNotFoundException e) {
    	    	logger.error("File '{}' not found", zipEntry.getName());
    	    }
	    }

    	zipInputStream.close();
    }
    
    public void extractAndClearSource() throws IOException {
    	logger.debug("Extract content of '{}' to '{}'", zipFile, targetDir);

        // delete destination file if exists
        removeDirectory(targetDir);

    	ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile), Charset.forName("GBK"));
	    ZipEntry zipEntry = null;

    	while ((zipEntry = zipInputStream.getNextEntry()) != null) {
	        try {
		        File file = new File(targetDir, zipEntry.getName());

        		// create intermediary directories - sometimes zip don't add them
        		File dir = new File(file.getParent());
        		dir.mkdirs();

        		if (zipEntry.isDirectory()) {
		            file.mkdirs();
        		} else {
		            byte[] buffer = new byte[1024];
        		    int length = 0;
		            FileOutputStream fos = new FileOutputStream(file);

        		    while ((length = zipInputStream.read(buffer)) >= 0) {
		            	fos.write(buffer, 0, length);
        		    }

		            fos.close();
                }
    	    } catch (FileNotFoundException e) {
    	    	logger.error("File '{}' not found", zipEntry.getName());
    	    }
	    }

    	zipInputStream.close();
    	zipFile.delete();
    }

    private boolean removeDirectory(File directory) {
        if (!directory.exists()) {
            return true;
        }

        if (!directory.isDirectory()) {
            return false;
        }

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                removeDirectory(file);
            } else {
                file.delete();
            }
        }

        return directory.delete();
    }
    
    public static void main(String[] args) throws IOException {
    	ZipFile zipFile = new ZipFile(new File("I:\\java\\aa.zip"), new File("I:\\unzip"));
    	zipFile.extractAndClearSource();
	}
    
}
