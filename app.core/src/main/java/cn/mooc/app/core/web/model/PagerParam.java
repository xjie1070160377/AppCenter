package cn.mooc.app.core.web.model;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PagerParam {

	private int page = 1;
	private int rows = 5;
	private String[] sidx;
	private String sord = "ASC";
	private Sort sort;

	public PagerParam(){
	}
	public PagerParam(int page, int rows){
		this.page = page;
		this.rows = rows;
	}
	public PagerParam(int page, int rows, Sort sort){
		this.page = page;
		this.rows = rows;
		this.sort = sort;
	}
	public PagerParam(int page, int rows,String sidx,String sord){
		this.page = page;
		this.rows = rows;
		this.sidx = new String[] { sidx };
		this.sord = sord;
	}
	public PagerParam(int page, int rows,String[] sidx,String sord){
		this.page = page;
		this.rows = rows;
		this.sidx = sidx;
		this.sord = sord;
	}
	
	public PageRequest getPageRequest(){
		return new PageRequest(this.getPageStart(), this.getRows(), this.getSort());
	}
	
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	public Sort getSort(){
		if(this.sort == null){
			Sort sort = null;
			if(ArrayUtils.isNotEmpty(this.getSidx())){
				sort = new Sort(Sort.Direction.fromString(this.getSord()), this.getSidx());			
			}
			return sort;
		}else{
			return this.sort;
		}
	}

	public long getTotalPage(long totalCount, int pageSize) {
		long totalPage = (long) Math.ceil(totalCount/Double.parseDouble(String.valueOf(pageSize)));
		return totalPage;
	}
	
	// 得到取行数的起始页码
	public int getPageStart() {
		int tmp = page - 1;
		return tmp <= 0 ? 0 : tmp;
	}
	
	public int getLimitStart() {
		int tmp = page - 1;
		tmp = tmp <= 0 ? 0 : tmp;
		return tmp * rows;
	}
	
	public int getLimitEnd() {
		int tmp = getLimitStart();
		return tmp + rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String[] getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = new String[] { sidx };
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}




}
