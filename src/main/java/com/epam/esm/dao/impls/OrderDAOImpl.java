package com.epam.esm.dao.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.esm.daos.OrderDAO;
import com.epam.esm.entities.Order;
import com.epam.esm.rowmappers.OrderRowMapper;

@Repository
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Order> getAllOrders() {
		String sql = "select * from epam_order";
		List<Order> orders = jdbcTemplate.query(sql, new OrderRowMapper());
		return orders;
	}

	@Override
	public Order getOrderById(int orderId) {
		String sql = "select * from epam_order where order_id=?";
		Order order = jdbcTemplate.queryForObject(sql, new OrderRowMapper(), orderId);
		return order;
	}

	@Override
	public int save(Order order) {
		String sql = "insert into epam_order(user_id, gift_certificate_id, price, order_date) values(?,?,?,?)";
		int result = jdbcTemplate.update(sql, new Object[] { order.getUserId(), order.getGiftCertificateId(),
				order.getPrice(), order.getTimestamp() });
		return result;
	}

	@Override
	public List<Order> getAllOrdersByUserId(int userId) {
		String sql = "select * from epam_order where user_id=?";
		List<Order> orders = jdbcTemplate.query(sql, new OrderRowMapper(), userId);
		return orders;
	}

	@Override
	public boolean isExistByOrderId(int orderId) {
		String sql = "SELECT EXISTS(SELECT FROM epam_order WHERE order_id = ?)";
		return jdbcTemplate.queryForObject(sql, Boolean.class, orderId);
	}

	@Override
	public boolean isExistByUserId(int userId) {
		String sql = "SELECT EXISTS(SELECT FROM epam_order WHERE user_id = ?)";
		return jdbcTemplate.queryForObject(sql, Boolean.class, userId);
	}

}
