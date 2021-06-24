package sheridan.smitniko.midterm.service;


import sheridan.smitniko.midterm.model.CountryForm;
import sheridan.smitniko.midterm.repository.CountryDataRepositoryJdbc;
import sheridan.smitniko.midterm.repository.CountryEntityJdbc;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryDataServiceJdbcImpl {

    CountryDataRepositoryJdbc countryDataRepositoryJdbc;

    public CountryDataServiceJdbcImpl(CountryDataRepositoryJdbc repository){
        this.countryDataRepositoryJdbc = repository;
    }

    private static void copyFormToEntity(CountryForm form, CountryEntityJdbc country){
        //country.setId(form.getId());
        country.setName(form.getName());
        country.setContinent(form.getContinent());
        country.setRegion(form.getRegion());
        country.setSurfaceArea(form.getSurfaceArea());
        country.setPopulation(form.getPopulation());
    }

    private static void copyEntityToForm(CountryEntityJdbc country, CountryForm form){
        form.setId(country.getId());
        form.setName(country.getName());
        form.setContinent(country.getContinent());
        form.setRegion(country.getRegion());
        form.setSurfaceArea(country.getSurfaceArea());
        form.setPopulation(country.getPopulation());
    }


    public void insertCountryForm(CountryForm form){
        CountryEntityJdbc country = new CountryEntityJdbc();
        copyFormToEntity(form, country);
        countryDataRepositoryJdbc.insert(country);
        form.setId(country.getId());
    }

    public CountryForm getCountryForm(int id){
        CountryEntityJdbc country = countryDataRepositoryJdbc.get(id);
        if(country != null){
            CountryForm form = new CountryForm();
            copyEntityToForm(country, form);
            return form;
        }else{
            return null;
        }

    }

    public List<CountryForm> getAllCountryForms(){
        List<CountryForm> forms = new ArrayList<>();
        List<CountryEntityJdbc> countries = countryDataRepositoryJdbc.getAll();
        for(CountryEntityJdbc entity: countries){
            CountryForm form = new CountryForm();
            copyEntityToForm(entity, form);
            forms.add(form);
        }
        return forms;
    }

    public void updateCountryForm(CountryForm form){
        CountryEntityJdbc country = countryDataRepositoryJdbc.get(form.getId());
        if(country != null){
            copyFormToEntity(form, country);
            countryDataRepositoryJdbc.update(country);
        }
    }

    public void deleteCountryForm(int id){
        countryDataRepositoryJdbc.delete(id);
    }

    public void deleteAllCountryForms(){
        countryDataRepositoryJdbc.deleteAll();
    }
}
