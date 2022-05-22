package com.epam.esm.service.impls;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.daos.GiftCertificateAndTagDAO;
import com.epam.esm.daos.GiftCertificateDAO;
import com.epam.esm.dtos.GiftCertificateDTO;
import com.epam.esm.dtos.TagDTO;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.AlreadyExistException;
import com.epam.esm.exceptions.FieldRequiredException;
import com.epam.esm.exceptions.GenericException;
import com.epam.esm.exceptions.NotFoundException;
import com.epam.esm.response.entities.BaseResponse;
import com.epam.esm.response.entities.BaseResponseBody;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.TagService;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

	@Autowired
	private GiftCertificateDAO giftCertificateDao;

	@Autowired
	private GiftCertificateAndTagDAO giftCertificateAndTagDao;

	@Autowired
	private TagService tagService;

	@Override
	public List<GiftCertificateDTO> getAllGiftCertificates() {
		List<GiftCertificate> giftCertificates = giftCertificateDao.getAllGiftCertificates();
		return populateListGiftCertificateDTO(giftCertificates);
	}

	@Override
	public GiftCertificateDTO getGiftCertificateById(int id) {
		boolean isExist = giftCertificateDao.isExistByCertificateId(id);
		if (!isExist) {
			throw new NotFoundException("Gift Certificate with id: " + id + " is not found to get.");
		}
		GiftCertificate giftCertificate = giftCertificateDao.getGiftCertificateById(id);
		GiftCertificateDTO giftCertificateDto = populateGiftCertificateDTO(giftCertificate);
		return giftCertificateDto;
	}

	@Override
	public List<GiftCertificateDTO> getAllGiftCertificatesByPaging(int page, int size) {
		if (page < 0 || size < 0)
			throw new GenericException("Request Params must not be negativ.");
		List<GiftCertificate> giftCertificates = giftCertificateDao.getGiftCertificatesByPaging(page, size);
		return populateListGiftCertificateDTO(giftCertificates);
	}

	@Override
	public List<GiftCertificateDTO> getAllGiftCertificatesByTagNames(List<String> tagNames) {
		if (tagNames.size() == 0) {
			throw new GenericException("Tag name is required");
		}
		List<GiftCertificateDTO> result = new ArrayList<>();
		for (String tagName : tagNames) {
			int tagId = tagService.getIdByTagName(tagName);
			if (tagId == -1)
				continue;
			List<Integer> giftCertificateIds = giftCertificateAndTagDao.findAllGiftCertificateIdsByTagId(tagId);
			for (int giftCertificateId : giftCertificateIds) {
				GiftCertificate giftCertificate = giftCertificateDao.getGiftCertificateById(giftCertificateId);
				GiftCertificateDTO giftCertificateDTO = populateGiftCertificateDTO(giftCertificate);
				result.add(giftCertificateDTO);
			}
		}
		return result;
	}

	@Override
	public List<GiftCertificateDTO> getAllGiftCertificatesBySearching(String value) {

		if (value == null) {
			throw new GenericException("Value is required to search");
		}

		List<GiftCertificate> giftCertificates = giftCertificateDao.getAllGiftCertificatesBySearching(value);
		return populateListGiftCertificateDTO(giftCertificates);
	}

	@Override
	public List<GiftCertificateDTO> getAllGiftCertificatesBySorting(String orderBy, String type) {

		List<String> fields = Arrays.asList("name", "description", "date");
		if (orderBy != null && !fields.contains(orderBy))
			throw new GenericException("Order By can be 'name', 'description' or 'date'");

		List<String> types = Arrays.asList("asc", "desc");
		if (type != null && !types.contains(type))
			throw new GenericException("type can be 'asc' or 'desc'");

		if (orderBy == null)
			orderBy = "name";

		if (orderBy.equals("date"))
			orderBy = "create_date";

		if (type == null)
			type = "asc";

		List<GiftCertificate> sortedGiftCertificates = giftCertificateDao.getAllGiftCertificatesBySorting(orderBy,
				type);
		return populateListGiftCertificateDTO(sortedGiftCertificates);
	}

	private List<GiftCertificateDTO> populateListGiftCertificateDTO(List<GiftCertificate> giftCertificates) {
		List<GiftCertificateDTO> result = new ArrayList<>();
		for (GiftCertificate giftCertificate : giftCertificates) {
			GiftCertificateDTO populatedGiftCertificateDTO = populateGiftCertificateDTO(giftCertificate);
			result.add(populatedGiftCertificateDTO);
		}
		return result;
	}

	private GiftCertificateDTO populateGiftCertificateDTO(GiftCertificate giftCertificate) {
		GiftCertificateDTO result = new GiftCertificateDTO();
		result.setId(giftCertificate.getId());
		result.setName(giftCertificate.getName());
		result.setDescription(giftCertificate.getDescription());
		result.setPrice(giftCertificate.getPrice());
		result.setDuration(giftCertificate.getDuration());
		result.setCreateDate(giftCertificate.getCreateDate());
		result.setLastUpdateDate(giftCertificate.getLastUpdateDate());

		List<TagDTO> tagDtos = getGiftCertificateTags(giftCertificate.getId());
		result.setTags(tagDtos);
		return result;
	}

	private List<TagDTO> getGiftCertificateTags(int giftCertificateId) {
		List<TagDTO> tagDtos = new ArrayList<>();
		List<Integer> tagIds = giftCertificateAndTagDao.findAllTagIdsByGiftCertificateId(giftCertificateId);
		for (Integer tagId : tagIds) {
			TagDTO tagDto = tagService.getTagById(tagId);
			tagDtos.add(tagDto);
		}
		return tagDtos;
	}

	@Override
	public BaseResponse saveGiftCertificate(GiftCertificateDTO giftCertificateDto) {

		String giftCertificateName = giftCertificateDto.getName();
		if (giftCertificateName == null)
			throw new FieldRequiredException("Name field is required");

		boolean isExist = giftCertificateDao.isExistByCertificateName(giftCertificateName);
		if (isExist)
			throw new AlreadyExistException("'" + giftCertificateName + "' is already exist");

		GiftCertificate giftCertificate = new GiftCertificate();
		giftCertificate.setName(giftCertificateName);
		giftCertificate.setDescription(giftCertificateDto.getDescription());
		giftCertificate.setPrice(giftCertificateDto.getPrice());
		giftCertificate.setDuration(giftCertificateDto.getDuration());
		giftCertificate.setCreateDate(LocalDateTime.now());
		giftCertificate.setLastUpdateDate(LocalDateTime.now());

		int result = giftCertificateDao.saveGiftCertificate(giftCertificate);
		if (result == 0)
			throw new GenericException("Error happened during saving new Gift Certificate");

		List<TagDTO> tags = giftCertificateDto.getTags();
		if (tags != null) {
			int savedGiftCertificateId = giftCertificateDao.getLastSavedGiftCertificateId();
			List<Integer> savedTagIds = getShouldBeSavedTagIds(savedGiftCertificateId, tags);
			for (int savedTagId : savedTagIds) {
				giftCertificateAndTagDao.saveAssosiation(savedGiftCertificateId, savedTagId);
			}
		}
		BaseResponseBody responseBody = new BaseResponseBody(
				"'" + giftCertificateName.toUpperCase() + "' is saved successfully", 9999);
		BaseResponse responseEntity = new BaseResponse(200, responseBody);
		return responseEntity;
	}

	private List<Integer> getShouldBeSavedTagIds(int giftCertificateId, List<TagDTO> tags) {
		List<Integer> shouldBeSavedTagIds = new ArrayList<>();
		for (TagDTO tag : tags) {
			boolean isExist = tagService.isExistByTagName(tag.getName());
			if (!isExist)
				tagService.saveTag(tag);
			int tagId = tagService.getIdByTagName(tag.getName());
			boolean checkAssosiation = giftCertificateAndTagDao.checkAssosiation(giftCertificateId, tagId);
			if (!checkAssosiation)
				shouldBeSavedTagIds.add(tagId);
		}
		return shouldBeSavedTagIds;
	}

	@Override
	public BaseResponse updateGiftCertificate(int giftCertificateId, GiftCertificateDTO giftCertificateDto) {

		if (giftCertificateDto.getName() != null || giftCertificateDto.getDescription() != null
				|| giftCertificateDto.getTags() != null) {
			throw new GenericException("You can only update price and duration of certificate");
		}

		if (giftCertificateDto.getPrice() == null && giftCertificateDto.getDuration() == null) {
			throw new GenericException("No field to update.");
		}

		ArrayList<Object> fields = new ArrayList<>();
		String sql = "update gift_certificate set ";
		int sqlLength = sql.length();
		if (giftCertificateDto.getPrice() != null) {
			sql += "price = ?";
			fields.add(giftCertificateDto.getPrice());
		}
		if (giftCertificateDto.getDuration() != null) {
			if (sql.length() != sqlLength)
				sql += ", ";
			sql += "duration = ?";
			fields.add(giftCertificateDto.getDuration());
		}

		sql += ", last_update_date = ? where id=?";
		fields.add(LocalDateTime.now());
		fields.add(giftCertificateId);
		Object[] params = fields.toArray();
		int result = giftCertificateDao.updateGiftCertificate(sql, params);
		if (result == 0)
			throw new GenericException("Error during updating");

		BaseResponseBody responseBody = new BaseResponseBody("Updated successfully", 9999);
		BaseResponse responseEntity = new BaseResponse(200, responseBody);
		return responseEntity;
	}

	@Override
	public BaseResponse deleteGiftCertificate(int id) {
		boolean isExist = giftCertificateDao.isExistByCertificateId(id);
		if (!isExist) {
			throw new NotFoundException("Gift Certificate with id: " + id + " is not found to delete.");
		}
		int result = giftCertificateDao.deleteGiftCertificate(id);
		if (result == 0)
			throw new GenericException("Error during deletion");
		giftCertificateAndTagDao.deleteAllByGiftCertificateId(id);
		BaseResponseBody responseBody = new BaseResponseBody("Deleted successfully", 9999);
		BaseResponse responseEntity = new BaseResponse(200, responseBody);
		return responseEntity;
	}

	@Override
	public String getGiftCertificateNameById(int certificateId) {
		return giftCertificateDao.getGiftCertificateNameById(certificateId);
	}

}
