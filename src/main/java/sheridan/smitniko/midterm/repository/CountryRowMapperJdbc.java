package sheridan.smitniko.midterm.repository;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

final class CountryRowMapperJdbc implements RowMapper<CountryEntityJdbc> {

    @Override
    public CountryEntityJdbc mapRow(ResultSet rs, int rowNum) throws SQLException {
        CountryEntityJdbc entityJdbc = new CountryEntityJdbc();
        entityJdbc.setId(rs.getInt("id"));
        entityJdbc.setName(rs.getString("name"));
        entityJdbc.setContinent(rs.getString("continent"));
        entityJdbc.setRegion(rs.getString("region"));
        entityJdbc.setSurfaceArea(rs.getInt("surface_area"));
        entityJdbc.setPopulation(rs.getInt("population"));
        return entityJdbc;
    }
}