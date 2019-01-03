package cn.mooc.app.plugin.upload.local;

public class FileInfo {

	private String serverRootPath;
	private String webPath;
	private String fileExt;
	private String saveToDir;
	private String destFileName;
	private String fileName;
	private long fileSize;
	
	public String getServerRootPath() {
		return serverRootPath;
	}
	public void setServerRootPath(String serverRootPath) {
		this.serverRootPath = serverRootPath;
	}
	public String getWebPath() {
		return webPath;
	}
	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public String getSaveToDir() {
		return saveToDir;
	}
	public void setSaveToDir(String saveToDir) {
		this.saveToDir = saveToDir;
	}
	public String getDestFileName() {
		return destFileName;
	}
	public void setDestFileName(String destFileName) {
		this.destFileName = destFileName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	
}
