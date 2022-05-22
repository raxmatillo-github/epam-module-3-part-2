package com.epam.esm.services;

import java.util.List;

import com.epam.esm.dtos.OrderDTO;
import com.epam.esm.dtos.OrderDtoToSave;
import com.epam.esm.response.entities.BaseResponse;

public interface OrderService {

	public List<OrderDTO> getAllOrders();

	public OrderDTO getOrderById(int orderId);

	public BaseResponse saveOrder(OrderDtoToSave order);
	
	public List<OrderDTO> getAllOrdersByUserId(int userId);

}
