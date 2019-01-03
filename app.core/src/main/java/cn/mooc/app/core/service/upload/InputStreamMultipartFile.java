package cn.mooc.app.core.service.upload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

public class InputStreamMultipartFile implements MultipartFile, Serializable {

	private static final long serialVersionUID = -3503140269900190322L;
	
	private String name;
	private String originalFilename;
	private String contentType;
	private long size;
	private InputStream inputStream;
	
	public InputStreamMultipartFile(String name, String originalFilename, String contentType, long size, InputStream inputStream){
		this.name = name;
		this.originalFilename = originalFilename;
		this.contentType = contentType;
		this.size = size;
		this.inputStream = inputStream;
		
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getOriginalFilename() {
		// TODO Auto-generated method stub
		return originalFilename;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return contentType;
	}

	@Override
	public boolean isEmpty() {
		return (this.size == 0);
	}

	@Override
	public long getSize() {
		return this.size ;
	}

	@Override
	public byte[] getBytes() throws IOException {

		byte[] bytes = new byte[(int) getSize()];
        InputStream fis = null;

        try {
            fis = new BufferedInputStream(getInputStream());
            fis.read(bytes);
        } catch (IOException e) {
        	bytes = null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

		return (bytes != null ? bytes : new byte[0]);
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return inputStream;
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		if (dest.exists() && !dest.delete()) {
			throw new IOException(
					"Destination file [" + dest.getAbsolutePath() + "] already exists and could not be deleted");
		}

		//BufferedImage originalImage = ImageIO.read(inputStream);
		//String fileExt = FilenameUtils.getExtension(originalFilename).toLowerCase();
		//ImageIO.write(originalImage, fileExt, dest);
		
		FileOutputStream fos = FileUtils.openOutputStream(dest); 
		IOUtils.copy(inputStream, fos);
		
	}
	
	

}
