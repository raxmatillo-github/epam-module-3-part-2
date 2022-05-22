package com.epam.esm.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.epam.esm.dtos.MostUsedTagDTO;

public class MostUsedTagRowMapper implements RowMapper<MostUsedTagDTO> {

	@Override
	public MostUsedTagDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MostUsedTagDTO result = new MostUsedTagDTO();
		result.setTagId(rs.getInt("tag_id"));
		result.setCount(rs.getInt("cnt"));
		return result;
	}

}
