package com.epam.esm.services;

import java.util.List;

import com.epam.esm.dtos.UserAnalysisDTO;
import com.epam.esm.dtos.UserDTO;

public interface UserService {

	public List<UserDTO> getAllUser();

	public UserDTO getUserById(int userId);

	public List<UserDTO> getAllUsersWithOrders();

	public UserDTO getUserWithOrdersById(int userId);
	
	public List<UserAnalysisDTO> getAllUsersWithAnalysis();
	
	public UserAnalysisDTO getUserWithAnalysis(int userId);

}
