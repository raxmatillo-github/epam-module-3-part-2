package com.epam.esm.daos;

import java.util.List;

import com.epam.esm.entities.Order;

public interface OrderDAO {
	
	public List<Order> getAllOrders();
	
	public Order getOrderById(int orderId);

	public int save(Order order);
	
	public List<Order> getAllOrdersByUserId(int userId);
	
	public boolean isExistByOrderId(int orderId);
	
	public boolean isExistByUserId(int userId);
}
