package com.epam.esm.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dtos.OrderDTO;
import com.epam.esm.dtos.OrderDtoToSave;
import com.epam.esm.response.entities.BaseResponse;
import com.epam.esm.services.OrderService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping
	public ResponseEntity<List<EntityModel<OrderDTO>>> getAllOrders() {
		List<OrderDTO> orders = orderService.getAllOrders();
		List<EntityModel<OrderDTO>> result = new ArrayList<>();
		for (OrderDTO order : orders) {
			Link selfLink = linkTo(OrderController.class).slash(order.getOrderId()).withSelfRel();
			Link userLink = linkTo(methodOn(UserController.class).getUserById(order.getUser().getUserId()))
					.withRel("user");
			Link certLink = linkTo(methodOn(GiftCertificateController.class)
					.getGiftCertificateById(order.getGiftCertificate().getId())).withRel("certificate");
			EntityModel<OrderDTO> resource = EntityModel.of(order, selfLink, userLink, certLink);
			result.add(resource);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable int orderId) {
		OrderDTO order = orderService.getOrderById(orderId);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<BaseResponse> saveOrder(@RequestBody OrderDtoToSave order) {
		BaseResponse baseResponse = orderService.saveOrder(order);
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
	}

}
