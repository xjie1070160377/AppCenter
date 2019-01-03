package cn.mooc.app.module.api.model;

public class MobileListData {

	public String msg_code;
	public String reason;
	public int page_no;
	public int page_size;
	public int total_size;
	public Object result;

	public String getMsg_code() {
		return msg_code;
	}

	public void setMsg_code(String msg_code) {
		this.msg_code = msg_code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getPage_no() {
		return page_no;
	}

	public void setPage_no(int page_no) {
		this.page_no = page_no;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public int getTotal_size() {
		return total_size;
	}

	public void setTotal_size(int total_size) {
		this.total_size = total_size;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public MobileListData(String msg_code, String reason, Object result, int page_no, int page_size, int total_size) {
		this.msg_code = msg_code;
		this.reason = reason;
		this.result = result;
		this.page_no = page_no;
		this.page_size = page_size;
		this.total_size = total_size;
	}

	public static MobileListData createError(String reason) {
		return new MobileListData("1", reason, "", 0, 0, 0);
	}

	public static MobileListData createSuccess(Object obj, int page_no, int page_size, int total_size) {
		return new MobileListData("0", "", obj, page_no, page_size, total_size);
	}

}
