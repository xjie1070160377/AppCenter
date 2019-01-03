package cn.mooc.app.core.filefilter;

import java.io.File;
import java.io.FileFilter;

public class ExtensionFileFilter implements FileFilter {

    private String extension;

    public ExtensionFileFilter(String extension) {
        this.extension = extension;
    }

    @Override
	public boolean accept(File pathname) {
		//
		return pathname.getName().toUpperCase().endsWith(this.extension.toUpperCase());
	}

}
