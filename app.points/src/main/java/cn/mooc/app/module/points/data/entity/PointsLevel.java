package cn.mooc.app.module.points.data.entity;


import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the t_points_level database table.
 * 
 */
@Entity
@Table(name="t_points_level")
public class PointsLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String imageUrl;

	private String levelName;

	private Double minPoints;

	@Id
	@Column(name = "level_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_points_level", pkColumnValue = "t_points_level", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_points_level")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "image_url", length = 200)
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column(name = "level_name", length = 20)
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	@Column(name = "min_points", precision = 8)
	public Double getMinPoints() {
		return minPoints;
	}

	public void setMinPoints(Double minPoints) {
		this.minPoints = minPoints;
	}
}