package cn.mooc.app.core.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.mooc.app.core.event.listener.FileSysEventListener;

/**
 * 监听目录，触发事件后立即处理
 * 
 * @author Taven
 *
 */
public class DirWatcher {
	
	private final static Logger logger = LoggerFactory.getLogger(DirWatcher.class);
	
	private final ReentrantLock lock = new ReentrantLock(true);
	
	private File monitorDir;
	private WatchService watcher;
	
	private List<FileSysEventListener> listeners = new ArrayList<FileSysEventListener>();
	
	public DirWatcher(File monitorDir){
		this.monitorDir = monitorDir;
	}
	
	public void initWatcher() {
		
		Set<File> dirs = new HashSet<>();
		//取目录下的所有子目录
        getChildDirs(monitorDir, dirs);
        
        Set<Path> paths = new HashSet<>();
        for (File each : dirs)
        {
            paths.add(Paths.get(each.getAbsolutePath()));
        }
        try
        {
            watcher = FileSystems.getDefault().newWatchService();
            for (Path each : paths)
            {
                each.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
                
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
		
	}
	
	
    private void getChildDirs(File parentDir, Set<File> dirSets)
    {
        dirSets.add(parentDir);
        for (File each : parentDir.listFiles())
        {
            if (each.isDirectory())
            {
                getChildDirs(each, dirSets);
            }
        }
    }
    
    public void registerListener(FileSysEventListener listener) {

    	lock.lock();
		try {
			this.listeners.add(listener);
		}
		finally {
			lock.unlock();
		}
		
    }
    
    public void unregisterListener(FileSysEventListener listener) {

    	lock.lock();
		try {
	    	
	    	this.listeners.remove(listener);
		}
		finally {
			lock.unlock();
		}
    	
    }
    
    public void startWork() {
    	
    	
    	Thread thread = new Thread(){
    		
    		public void run(){
    			
				while (true) {
					
					try {
						workJob();
					} catch (InterruptedException e) {
						logger.error("workJob异常",e);
					}
															
				}
				
    		}
    	};
    	
    	thread.start();
    	
    }
    
    private void workJob() throws InterruptedException{
    	
        WatchKey watchKey = watcher.take();
        long startTime = System.currentTimeMillis();
        //插件目录有变化
        for (FileSysEventListener fileSysEventListener : listeners) {
        	fileSysEventListener.processEvent(this.monitorDir, watchKey);
		}
        logger.debug("目录有变化,处理事件耗时:{}", System.currentTimeMillis() - startTime);

        watchKey.reset();
        
    }
    
    private void demo(WatchKey watchKey){
    	
        for (WatchEvent<?> event : watchKey.pollEvents())
        {
        	//
            Kind<?> kind = event.kind();
            if (kind == StandardWatchEventKinds.OVERFLOW)
            {// 事件可能lost or discarded
                continue;
            }
            try
            {
            	//插件目录有变化
            	System.out.println("[/"+event.context()+"]文件发生了["+event.kind()+"]事件");
            }
            catch (Exception e)
            {
                logger.error("处理文件系统事件异常",e);
            }
        }
        
    }
	
}
