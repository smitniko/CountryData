package sheridan.smitniko.midterm.repository;

import java.util.List;

public interface CountryDataRepositoryJdbc {
    void insert(CountryEntityJdbc country);
    CountryEntityJdbc get(int id);
    List<CountryEntityJdbc> getAll();
    void update(CountryEntityJdbc country);
    void delete(int id);
    void deleteAll();
}