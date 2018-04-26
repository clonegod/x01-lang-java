package spittr.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 领域对象-用户发送的消息
 *
 */
public class Spittle {

	private Long id;
	private String message;
	private Date time;
	private Double latitude;
	private Double longitude;

	public Spittle() {
		super();
	}

	public Spittle(String message, Date time) {
		this(null, message, time, null, null);
	}

	public Spittle(Long id, String message, Date time, Double longitude, Double latitude) {
		this.id = id;
		this.message = message;
		this.time = time;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Long getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public Date getTime() {
		return time;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	@Override
	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that, "id", "time");
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id", "time");
	}

}
