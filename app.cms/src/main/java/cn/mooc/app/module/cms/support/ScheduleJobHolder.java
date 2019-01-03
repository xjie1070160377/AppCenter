package cn.mooc.app.module.cms.support;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ScheduleJobHolder {
	private List<String> codes;
	private Map<String, String> paths;

	public Map<String, String> getPaths() {
		return paths;
	}

	public void setPaths(Map<String, String> paths) {
		this.paths = paths;
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}
}
