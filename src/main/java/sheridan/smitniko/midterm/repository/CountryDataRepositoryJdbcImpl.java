package sheridan.smitniko.midterm.repository;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CountryDataRepositoryJdbcImpl implements CountryDataRepositoryJdbc {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public CountryDataRepositoryJdbcImpl(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            JdbcTemplate jdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(CountryEntityJdbc country) {
        String update = "INSERT INTO country "
                + "(name, continent, region, surface_area, population) "
                + "VALUES "
                + "(:name, :continent, :region, :surface_area, :population)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        GeneratedKeyHolder keys = new GeneratedKeyHolder();
        params.addValue("name", country.getName().trim());
        params.addValue("continent", country.getContinent().trim());
        params.addValue("region", country.getRegion());
        params.addValue("surface_area", country.getSurfaceArea());
        params.addValue("population", country.getPopulation());
        namedParameterJdbcTemplate.update(update, params, keys);
        country.setId(keys.getKey()!=null?keys.getKey().intValue():0);
    }

    @Override
    public CountryEntityJdbc get(int id) {
        String query = "SELECT * FROM country WHERE ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        CountryEntityJdbc country = null;
        params.addValue("id", id);
        try {
            country = namedParameterJdbcTemplate.queryForObject(
                    query, params, new CountryRowMapperJdbc());
        } catch (DataAccessException e) {
            // the code above throws an exception if the record is not found
        }
        return country;
    }

    @Override
    public List<CountryEntityJdbc> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM country ORDER BY continent, name",
                new CountryRowMapperJdbc());
    }

    @Override
    public void update(CountryEntityJdbc country) {
        jdbcTemplate.update(
                "UPDATE country SET "
                        + "name = ?, continent = ?, "
                        + "region = ?, surface_area = ?, "
                        + "population = ? "
                        + "WHERE id = ?",
                country.getName().trim(), country.getContinent().trim(),
                country.getRegion(), country.getSurfaceArea(),
                country.getPopulation(),
                country.getId());
    }

    @Override
    public void delete(int id) {
        String update = "DELETE FROM country WHERE id = ?";
        jdbcTemplate.update(update, id);
    }

    @Override
    public void deleteAll() {
        String update = "TRUNCATE TABLE country";
        jdbcTemplate.update(update);
    }

}