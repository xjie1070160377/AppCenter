package cn.mooc.app.core.event.listener;

import java.io.File;
import java.nio.file.WatchKey;

public interface FileSysEventListener {

	
	public void processEvent(File eventDir, WatchKey watchKey);
	
}
