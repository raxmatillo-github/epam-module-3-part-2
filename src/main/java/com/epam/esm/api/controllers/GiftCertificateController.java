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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dtos.GiftCertificateDTO;
import com.epam.esm.response.entities.BaseResponse;
import com.epam.esm.services.GiftCertificateService;

@RestController
@RequestMapping("api/certificates")
public class GiftCertificateController {

	@Autowired
	private GiftCertificateService giftCertificateService;

	@GetMapping
	public ResponseEntity<List<EntityModel<GiftCertificateDTO>>> getAllGiftCertificates() {
		List<GiftCertificateDTO> giftCertificates = giftCertificateService.getAllGiftCertificates();
		List<EntityModel<GiftCertificateDTO>> result = new ArrayList<>();
		for (GiftCertificateDTO giftCertificate : giftCertificates) {
			Link selfLink = linkTo(GiftCertificateController.class).slash(giftCertificate.getId()).withSelfRel();
			EntityModel<GiftCertificateDTO> resource = EntityModel.of(giftCertificate, selfLink);
			result.add(resource);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/{giftCertificateId}")
	public ResponseEntity<EntityModel<GiftCertificateDTO>> getGiftCertificateById(@PathVariable int giftCertificateId) {
//		GiftCertificateDTO giftCertificateDto = giftCertificateService.getGiftCertificateById(giftCertificateId);
		Link selfLink = linkTo(GiftCertificateController.class).slash(giftCertificateId).withSelfRel();
		Link certificates = linkTo(methodOn(GiftCertificateController.class).getAllGiftCertificates())
				.withRel("all-certificates");
		EntityModel<GiftCertificateDTO> resource = EntityModel
				.of(giftCertificateService.getGiftCertificateById(giftCertificateId), selfLink, certificates);
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@GetMapping("/paginate")
	public ResponseEntity<List<GiftCertificateDTO>> getGiftCertificatesByPaging(@RequestParam int page,
			@RequestParam int size) {
		List<GiftCertificateDTO> giftCertificates = giftCertificateService.getAllGiftCertificatesByPaging(page, size);
		return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
	}

	@GetMapping("/tag")
	public ResponseEntity<List<GiftCertificateDTO>> getAllGiftCertificatesByTagNames(@RequestParam List<String> name) {
		List<GiftCertificateDTO> giftCertificatesDto = giftCertificateService.getAllGiftCertificatesByTagNames(name);
		return new ResponseEntity<>(giftCertificatesDto, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<GiftCertificateDTO>> getAllGiftCertificatesBySearching(@RequestParam String value) {
		List<GiftCertificateDTO> giftCertificatesDto = giftCertificateService.getAllGiftCertificatesBySearching(value);
		return new ResponseEntity<>(giftCertificatesDto, HttpStatus.OK);
	}

	@GetMapping("/sort")
	public ResponseEntity<List<GiftCertificateDTO>> getAllGiftCertificatesBySorting(
			@RequestParam(required = false) String orderBy, @RequestParam(required = false) String type) {
		List<GiftCertificateDTO> giftCertificates = giftCertificateService.getAllGiftCertificatesBySorting(orderBy,
				type);
		return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<BaseResponse> saveGiftCertificate(@RequestBody GiftCertificateDTO request) {
		BaseResponse responseEntity = giftCertificateService.saveGiftCertificate(request);
		return new ResponseEntity<>(responseEntity, HttpStatus.CREATED);
	}

	@PutMapping("/{giftCertificateId}")
	public ResponseEntity<BaseResponse> updateGiftCertificate(@PathVariable int giftCertificateId,
			@RequestBody GiftCertificateDTO giftCertificateDto) {
		BaseResponse myResponseEntity = giftCertificateService.updateGiftCertificate(giftCertificateId,
				giftCertificateDto);
		return new ResponseEntity<>(myResponseEntity, HttpStatus.OK);
	}

	@DeleteMapping("/{giftCertificateId}")
	public ResponseEntity<BaseResponse> deleteGiftCertificate(@PathVariable int giftCertificateId) {
		BaseResponse myResponseEntity = giftCertificateService.deleteGiftCertificate(giftCertificateId);
		return new ResponseEntity<>(myResponseEntity, HttpStatus.OK);
	}

}
