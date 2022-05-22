package com.epam.esm.dao.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.esm.daos.TagDAO;
import com.epam.esm.entities.Tag;
import com.epam.esm.rowmappers.TagRowMapper;

@Repository
public class TagDAOImpl implements TagDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Tag> getAllTags() {
		String sql = "select * from tag";
		List<Tag> tags = jdbcTemplate.query(sql, new TagRowMapper());
		return tags;
	}

	@Override
	public Tag getTagById(int id) {
		String sql = "select * from tag where id=?";
		Tag tag = jdbcTemplate.queryForObject(sql, new TagRowMapper(), id);
		return tag;
	}

	@Override
	public int getIdByTagName(String name) {
		String sql = "select id from tag where name=?";
		return jdbcTemplate.queryForObject(sql, Integer.class, name);

	}

	@Override
	public int saveTag(Tag tag) {
		String sql = "insert into tag(name) values(?)";
		int result = jdbcTemplate.update(sql, tag.getName());
		return result;
	}

	@Override
	public int deleteTag(int id) {
		String sql = "delete from tag where id=?";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public boolean isExistByTagName(String tagName) {
		return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT FROM tag WHERE name = ?)", Boolean.class, tagName);
	}

	@Override
	public boolean isExistByTagId(int id) {
		return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT FROM tag WHERE id = ?)", Boolean.class, id);
	}
}
