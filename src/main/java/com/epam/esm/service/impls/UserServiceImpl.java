package com.epam.esm.service.impls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.daos.OrderDAO;
import com.epam.esm.daos.UserDAO;
import com.epam.esm.dtos.GiftCertificateDTO;
import com.epam.esm.dtos.MostUsedTagDTO;
import com.epam.esm.dtos.OrderDTO;
import com.epam.esm.dtos.TagDTO;
import com.epam.esm.dtos.UserAnalysisDTO;
import com.epam.esm.dtos.UserDTO;
import com.epam.esm.entities.Order;
import com.epam.esm.entities.User;
import com.epam.esm.exceptions.NotFoundException;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.TagService;
import com.epam.esm.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;

	@Autowired
	private OrderDAO orderDao;

	@Autowired
	private GiftCertificateService giftCertificateService;

	@Autowired
	private TagService tagService;

	@Override
	public List<UserDTO> getAllUser() {
		List<User> users = userDao.getAllUsers();
		return populateListUserDTO(users);
	}

	@Override
	public UserDTO getUserById(int id) {
		if (!userDao.isExistByUserId(id))
			throw new NotFoundException("User with id: " + id + " is not found to get.");

		User user = userDao.getUserById(id);
		UserDTO userDto = populateUserDTO(user);
		return userDto;
	}

	private List<UserDTO> populateListUserDTO(List<User> users) {
		List<UserDTO> result = new ArrayList<>();
		for (User user : users) {
			UserDTO userDto = populateUserDTO(user);
			result.add(userDto);
		}
		return result;
	}

	private UserDTO populateUserDTO(User user) {
		UserDTO userDto = new UserDTO();
		userDto.setUserId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		return userDto;
	}

	@Override
	public List<UserDTO> getAllUsersWithOrders() {

		List<UserDTO> users = getAllUser();
		for (UserDTO user : users) {
			user.setOrders(populateOrderDtos(user.getUserId()));
		}
		return users;
	}

	@Override
	public UserDTO getUserWithOrdersById(int userId) {
		UserDTO userDto = getUserById(userId);
		userDto.setOrders(populateOrderDtos(userId));
		return userDto;
	}

	private List<OrderDTO> populateOrderDtos(int userId) {
		List<OrderDTO> orderDtos = new ArrayList<>();
		List<Order> allOrdersByUserId = orderDao.getAllOrdersByUserId(userId);
		for (Order order : allOrdersByUserId) {
			OrderDTO orderDto = new OrderDTO();
			orderDto.setOrderId(order.getOrderId());
			GiftCertificateDTO giftCertificateDto = new GiftCertificateDTO();
			giftCertificateDto.setId(order.getGiftCertificateId());
			giftCertificateDto.setName(giftCertificateService.getGiftCertificateNameById(order.getGiftCertificateId()));
			giftCertificateDto.setPrice(order.getPrice());
			giftCertificateDto.setTags(tagService.getAllTagDTOsByGiftCertificateId(order.getGiftCertificateId()));
			orderDto.setGiftCertificate(giftCertificateDto);
			orderDto.setTimestamp(order.getTimestamp());
			orderDtos.add(orderDto);
		}
		return orderDtos;
	}

	@Override
	public List<UserAnalysisDTO> getAllUsersWithAnalysis() {
		List<UserAnalysisDTO> result = new ArrayList<>();
		List<User> users = userDao.getAllUsers();
		for (User user : users) {
			UserAnalysisDTO userAnalysisDTO = populateUserAnalysisDTO(user.getId());
			result.add(userAnalysisDTO);
		}
		return result;
	}

	@Override
	public UserAnalysisDTO getUserWithAnalysis(int userId) {
		return populateUserAnalysisDTO(userId);
	}

	private UserAnalysisDTO populateUserAnalysisDTO(int userId) {
		if (!userDao.isExistByUserId(userId))
			throw new NotFoundException("User with id: " + userId + " is not found to analyze.");
		UserAnalysisDTO result = new UserAnalysisDTO();
		User user = userDao.getUserById(userId);
		result.setUserId(userId);
		result.setFirstName(user.getFirstName());

		boolean isExist = orderDao.isExistByUserId(userId);
		if (!isExist) {
			result.setMostUsedTag(null);
			result.setMaxPrice(0.0);
			return result;
		}

		MostUsedTagDTO mostUsedTagDto = userDao.getMostWidelyUsedTagId(userId);
		int tagId = mostUsedTagDto.getTagId();
		TagDTO tagDto = tagService.getTagById(tagId);
		tagDto.setCount(mostUsedTagDto.getCount());
		result.setMostUsedTag(tagDto);
		Double maxPrice = userDao.getMaxPrice(userId);
		result.setMaxPrice(maxPrice);
		return result;
	}

}
