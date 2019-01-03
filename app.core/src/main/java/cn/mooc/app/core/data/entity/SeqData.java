package cn.mooc.app.core.data.entity;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_seq_data")
public class SeqData {

	/**
	 * 集合名，可一个集合一个自增字段
	 */
	@Id
	private String _id;

	
	private long seq;


	public String get_id() {
		return _id;
	}


	public void set_id(String _id) {
		this._id = _id;
	}


	public long getSeq() {
		return seq;
	}


	public void setSeq(long seq) {
		this.seq = seq;
	}
	
	
	
}
