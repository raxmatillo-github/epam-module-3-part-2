package com.epam.esm.daos;

import java.util.List;

import com.epam.esm.dtos.MostUsedTagDTO;
import com.epam.esm.entities.User;

public interface UserDAO {

	public List<User> getAllUsers();

	public User getUserById(int userId);

	public MostUsedTagDTO getMostWidelyUsedTagId(int userId);

	public Double getMaxPrice(int userId);

	public boolean isExistByUserId(int userId);
	
	public String getUserNameByUserId(int userId);

}
