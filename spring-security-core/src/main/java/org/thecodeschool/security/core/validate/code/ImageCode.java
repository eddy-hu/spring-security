package org.thecodeschool.security.core.validate.code;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode {

	private BufferedImage image;
	private String code;
	private LocalDateTime expireTime;

	public ImageCode(BufferedImage image, String code, int expireIn) {
		super();
		this.image = image;
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}

	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		super();
		this.image = image;
		this.code = code;
		this.expireTime = expireTime;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}

	public boolean isExpired() {
		return this.expireTime.isBefore(LocalDateTime.now());
	}

	@Override
	public String toString() {
		return "ImageCode [image=" + image + ", code=" + code + ", expireTime=" + expireTime + "]";
	}

}
