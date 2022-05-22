package com.epam.esm.service.impls;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.epam.esm.daos.OrderDAO;
import com.epam.esm.daos.UserDAO;
import com.epam.esm.dtos.GiftCertificateDTO;
import com.epam.esm.dtos.OrderDTO;
import com.epam.esm.dtos.OrderDtoToSave;
import com.epam.esm.dtos.UserDTO;
import com.epam.esm.entities.Order;
import com.epam.esm.exceptions.FieldRequiredException;
import com.epam.esm.exceptions.GenericException;
import com.epam.esm.response.entities.BaseResponse;
import com.epam.esm.response.entities.BaseResponseBody;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDao;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private GiftCertificateService giftCertificateService;

	@Override
	public List<OrderDTO> getAllOrders() {
		List<Order> orders = orderDao.getAllOrders();
		return populateListOrderDto(orders);
	}

	@Override
	public OrderDTO getOrderById(int orderId) {
		Order order = orderDao.getOrderById(orderId);
		OrderDTO orderDto = populateOrderDto(order);
		return orderDto;
	}

	@Override
	public List<OrderDTO> getAllOrdersByUserId(int userId) {
		List<Order> orders = orderDao.getAllOrdersByUserId(userId);
		return populateListOrderDto(orders);
	}

	private List<OrderDTO> populateListOrderDto(List<Order> orders) {
		List<OrderDTO> result = new ArrayList<>();
		for (Order order : orders) {
			OrderDTO orderDto = populateOrderDto(order);
			result.add(orderDto);
		}
		return result;
	}

	private OrderDTO populateOrderDto(Order order) {
		OrderDTO orderDto = new OrderDTO();
		orderDto.setOrderId(order.getOrderId());

		UserDTO userDto = new UserDTO();
		String userName = userDao.getUserNameByUserId(order.getUserId());
		userDto.setUserId(order.getUserId());
		userDto.setFirstName(userName);
		
		orderDto.setUser(userDto);

		GiftCertificateDTO giftCertificateDtoById = giftCertificateService
				.getGiftCertificateById(order.getGiftCertificateId());
		GiftCertificateDTO giftCertificateDto = new GiftCertificateDTO();
		giftCertificateDto.setId(order.getGiftCertificateId());
		giftCertificateDto.setName(giftCertificateDtoById.getName());
		giftCertificateDto.setPrice(order.getPrice());
		giftCertificateDto.setTags(giftCertificateDtoById.getTags());
		orderDto.setGiftCertificate(giftCertificateDto);

		orderDto.setTimestamp(order.getTimestamp());
		return orderDto;
	}

	@Override
	public BaseResponse saveOrder(OrderDtoToSave orderDto) {
		if (orderDto.getUser_id() == null || orderDto.getGift_certificate_id() == null) {
			throw new FieldRequiredException("Both user id and certificate id are required.");
		}
		
		int userId = orderDto.getUser_id();
		int certificateId = orderDto.getGift_certificate_id();

		GiftCertificateDTO giftCertificate = giftCertificateService.getGiftCertificateById(certificateId);

		Order order = new Order();
		order.setUserId(userId);
		order.setGiftCertificateId(certificateId);
		order.setPrice(giftCertificate.getPrice());
		order.setTimestamp(LocalDateTime.now());

		int result = orderDao.save(order);
		if (result == 0) {
			throw new GenericException("An error occured while saving order to database");
		}
		BaseResponseBody body = new BaseResponseBody();
		body.setCode(9999);
		body.setMessage("Order Saved Successfully");
		BaseResponse response = new BaseResponse();
		response.setHttpStatus(HttpStatus.CREATED.value());
		response.setResponseBody(body);
		return response;
	}

}
