package com.epam.esm.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class OrderDTO {

	private Integer orderId;
	private UserDTO user;
	private GiftCertificateDTO giftCertificate;
	private LocalDateTime timestamp;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public GiftCertificateDTO getGiftCertificate() {
		return giftCertificate;
	}

	public void setGiftCertificate(GiftCertificateDTO giftCertificate) {
		this.giftCertificate = giftCertificate;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
