package cn.mooc.app.module.cms.data.entity;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.AbstractTrigger;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import cn.mooc.app.module.cms.support.Siteable;
import cn.mooc.app.module.cms.util.JsonMapper;

@Entity
@Table(name = "t_cms_schedule_job")
public class ScheduleJob implements Siteable, java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public static final int ENABLED = 0;
	public static final int DISABLED = 1;

	public static final int CYCLE_CRON = 1;
	public static final int CYCLE_SIMPLE = 2;
	/**
	 * 毫秒
	 */
	public static final int UNIT_MSEC = 1;
	/**
	 * 秒
	 */
	public static final int UNIT_SEC = 2;
	/**
	 * 分
	 */
	public static final int UNIT_MINUTE = 3;
	/**
	 * 时
	 */
	public static final int UNIT_HOUR = 4;
	/**
	 * 日
	 */
	public static final int UNIT_DAY = 5;
	/**
	 * 周
	 */
	public static final int UNIT_WEEK = 6;
	/**
	 * 月
	 */
	public static final int UNIT_MONTH = 7;
	/**
	 * 年
	 */
	public static final int UNIT_YEAR = 8;

	@Transient
	public boolean isEnabled() {
		return getStatus() == ENABLED;
	}

	@Transient
	public TriggerKey getTriggerKey() {
		return new TriggerKey("Trigger-" + getId(), getGroup());
	}

	@Transient
	public Trigger getTrigger() throws ParseException {
		AbstractTrigger<?> trigger;
		if (getCycle() == CYCLE_CRON) {
			CronTriggerImpl t = new CronTriggerImpl();
			t.setCronExpression(getCronExpression());
			trigger = t;
		} else {
			SimpleTriggerImpl t = new SimpleTriggerImpl();
			t.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
			long interval = getRepeatInterval();
			switch (getUnit()) {
			case UNIT_SEC:
				interval *= 1000;
				break;
			case UNIT_MINUTE:
				interval *= 1000 * 60;
				break;
			case UNIT_HOUR:
				interval *= 1000 * 60 * 60;
				break;
			case UNIT_DAY:
				interval *= 1000 * 60 * 60 * 24;
				break;
			case UNIT_WEEK:
				interval *= 1000 * 60 * 60 * 24 * 7;
				break;
			case UNIT_MONTH:
				interval *= 1000 * 60 * 60 * 24 * 30;
				break;
			case UNIT_YEAR:
				interval *= 1000 * 60 * 60 * 24 * 365;
				break;
			default:
				break;
			}
			t.setRepeatInterval(interval);
			trigger = t;
		}
		trigger.setKey(getTriggerKey());
		if (getStartTime() != null) {
			trigger.setStartTime(getStartTime());
		} else if (getStartDelay() != null) {
			// 单位为分
			trigger.setStartTime(new Date(System.currentTimeMillis()
					+ getStartDelay() * 1000 * 60));
		}
		if (getEndTime() != null) {
			trigger.setEndTime(getEndTime());
		}
		return trigger;
	}

	@Transient
	@SuppressWarnings("unchecked")
	public JobDetail getJobDetail() throws ClassNotFoundException {
		JobBuilder jb = JobBuilder.newJob((Class<? extends Job>) Class
				.forName(getCode()));
		jb.withIdentity(getId().toString());
		if (StringUtils.isNotBlank(getDescription())) {
			jb.withDescription(getDescription());
		}
		String data = getData();
		JsonMapper mapper = new JsonMapper();
		Map<String, String> map = mapper.fromJson(data, Map.class);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			jb.usingJobData(entry.getKey(), entry.getValue());
		}
		JobDetail detail = jb.build();
		return detail;
	}

	@Transient
	public Map<String, String> getDataMap() {
		Map<String, String> dataMap = new HashMap<String, String>();
		String data = getData();
		if (StringUtils.isNotBlank(data)) {
			JsonMapper mapper = new JsonMapper();
			mapper.update(data, dataMap);
		}
		return dataMap;
	}

	@Transient
	public void setDataMap(Map<String, String> dataMap) {
		String data = null;
		if (dataMap != null) {
			JsonMapper mapper = new JsonMapper();
			data = mapper.toJson(dataMap);
		}
		setData(data);
	}

	@Transient
	public void applyDefaultValue() {
		if (getStatus() == null) {
			setStatus(ENABLED);
		}
	}

	private Integer id;
	private Site site;
	private Integer userId;

	private String name;
	private String group;
	private String code;
	private String data;
	private String description;
	private String cronExpression;
	private Date startTime;
	private Date endTime;
	private Long startDelay;
	private Long repeatInterval;
	private Integer unit;
	private Integer cycle;
	private Integer status;

	private Date nextFireTime;

	@Id
	@Column(name = "schedulejob_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_schedule_job", pkColumnValue = "t_cms_schedule_job", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_schedule_job")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "group", length = 100)
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Column(name = "code", nullable = false, length = 100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "data")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", length = 19)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", length = 19)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "cron_expression", length = 100)
	public String getCronExpression() {
		return this.cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	@Column(name = "start_delay")
	public Long getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(Long startDelay) {
		this.startDelay = startDelay;
	}

	@Column(name = "repeat_interval")
	public Long getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(Long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	@Column(name = "unit")
	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	@Column(name = "cycle", nullable = false)
	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Transient
	public Date getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

}
