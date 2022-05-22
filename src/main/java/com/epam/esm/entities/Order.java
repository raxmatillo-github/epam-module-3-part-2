package com.epam.esm.entities;

import java.time.LocalDateTime;

public class Order {

	private int orderId;
	private int userId;
	private int giftCertificateId;
	private double price;

	private LocalDateTime timestamp;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getGiftCertificateId() {
		return giftCertificateId;
	}

	public void setGiftCertificateId(int giftCertificateId) {
		this.giftCertificateId = giftCertificateId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
