package com.epam.esm.dtos;

public class UserAnalysisDTO {

	private Integer userId;
	private String firstName;
	private TagDTO mostUsedTag;
	private Double maxPrice;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public TagDTO getMostUsedTag() {
		return mostUsedTag;
	}

	public void setMostUsedTag(TagDTO mostUsedTag) {
		this.mostUsedTag = mostUsedTag;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

}
