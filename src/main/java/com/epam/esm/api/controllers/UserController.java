package com.epam.esm.api.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dtos.UserAnalysisDTO;
import com.epam.esm.dtos.UserDTO;
import com.epam.esm.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<EntityModel<UserDTO>>> getAllUsers() {
		List<UserDTO> users = userService.getAllUser();
		List<EntityModel<UserDTO>> result = new ArrayList<>();
		for (UserDTO user : users) {
			Link selfLink = linkTo(UserController.class).slash(user.getUserId()).withSelfRel();
			Link orders = linkTo(methodOn(UserController.class).getUserWithOrdersById(user.getUserId()))
					.withRel("orders");
			EntityModel<UserDTO> resource = EntityModel.of(user, selfLink, orders);
			result.add(resource);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable int userId) {
		UserDTO user = userService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/orders")
	public ResponseEntity<List<UserDTO>> getAllUsersWithOrders() {
		List<UserDTO> allUserWithOrders = userService.getAllUsersWithOrders();
		return new ResponseEntity<>(allUserWithOrders, HttpStatus.OK);
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<UserDTO> getUserWithOrdersById(@PathVariable int userId) {
		UserDTO user = userService.getUserWithOrdersById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/analysis")
	public ResponseEntity<List<UserAnalysisDTO>> getAllUsersWithAnalysis() {
		List<UserAnalysisDTO> allUserWithOrders = userService.getAllUsersWithAnalysis();
		return new ResponseEntity<>(allUserWithOrders, HttpStatus.OK);
	}

	@GetMapping("/{userId}/analysis")
	public ResponseEntity<UserAnalysisDTO> getUserWithAnalysis(@PathVariable int userId) {
		UserAnalysisDTO allUserWithOrders = userService.getUserWithAnalysis(userId);
		return new ResponseEntity<>(allUserWithOrders, HttpStatus.OK);
	}

}
