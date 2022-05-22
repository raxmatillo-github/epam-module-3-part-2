package com.epam.esm.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.daos.GiftCertificateDAO;
import com.epam.esm.entities.GiftCertificate;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	GiftCertificateDAO foo;

	@GetMapping
	public List<GiftCertificate> ids() {
		System.out.println("Inside tags");
		return foo.getAllGiftCertificatesBySorting("name", "desc");
	}
	
//	private GiftCertificateDTO addLinks(GiftCertificateDTO giftCertificateDto) {
//		Link selfLink = linkTo(methodOn(GiftCertificateController.class).getAllGiftCertificates())
//				.withRel("allGiftCertificates");
//		Link idLink = linkTo(
//				methodOn(GiftCertificateController.class).getGiftCertificateById(giftCertificateDto.getId()))
//						.withSelfRel();
//		giftCertificateDto.add(selfLink);
//		giftCertificateDto.add(idLink);
//		return giftCertificateDto;
//	}
	

//	private UserDTO addLink(UserDTO userDto) {
//		Link selfLink = linkTo(methodOn(UserController.class).getUserById(userDto.getUserId())).withSelfRel();
//		Link allUsersLink = linkTo(methodOn(UserController.class).getAllUsers()).withRel("allUsers");
//		Link ordersLink = linkTo(methodOn(UserController.class).getUserWithOrdersById(userDto.getUserId()))
//				.withRel("orders");
//		userDto.add(selfLink);
//		userDto.add(allUsersLink);
//		userDto.add(ordersLink);
//		return userDto;
//	}
}
