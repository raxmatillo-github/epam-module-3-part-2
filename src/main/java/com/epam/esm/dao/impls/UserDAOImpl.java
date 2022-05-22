package com.epam.esm.dao.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.esm.daos.UserDAO;
import com.epam.esm.dtos.MostUsedTagDTO;
import com.epam.esm.entities.User;
import com.epam.esm.rowmappers.MostUsedTagRowMapper;
import com.epam.esm.rowmappers.UserRowMapper;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<User> getAllUsers() {
		String sql = "select * from epam_user";
		List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
		return users;
	}

	@Override
	public User getUserById(int id) {
		String sql = "select * from epam_user where id=?";
		User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
		return user;
	}

	@Override
	public MostUsedTagDTO getMostWidelyUsedTagId(int userId) {
		String sql = "select tag_id, count(*) cnt \n" + "from gift_certificate_and_tag \n"
				+ "where gift_certificate_id in(\n" + "	select gift_certificate_id from epam_order where user_id = ?)\n"
				+ "group by tag_id\n" + "order by 2 desc\n" + "limit 1";
		try {
			MostUsedTagDTO result = jdbcTemplate.queryForObject(sql, new MostUsedTagRowMapper(), userId);
			return result;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Double getMaxPrice(int userId) {
		String sql = "select max(price) from epam_order where user_id = ?";
		Double price = jdbcTemplate.queryForObject(sql, Double.class, userId);
		return price;
	}

	@Override
	public boolean isExistByUserId(int userId) {
		return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT FROM epam_user WHERE id = ?)", Boolean.class, userId);
	}

	@Override
	public String getUserNameByUserId(int userId) {
		if (!isExistByUserId(userId))
			return null;
		String sql = "select first_name from epam_user where id = ?";
		String name = jdbcTemplate.queryForObject(sql, String.class, userId);
		return name;
	}

}
