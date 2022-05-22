package com.epam.esm.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.esm.entities.Order;

public class OrderRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Order order = new Order();
		order.setOrderId(rs.getInt("order_id"));
		order.setUserId(rs.getInt("user_id"));
		order.setGiftCertificateId(rs.getInt("gift_certificate_id"));
		order.setPrice(rs.getDouble("price"));
		order.setTimestamp(rs.getTimestamp("order_date").toLocalDateTime());
		return order;
	}

}
