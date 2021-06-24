package sheridan.smitniko.midterm.service;

import sheridan.smitniko.midterm.model.CountryForm;

import java.util.List;


public class CountryDataService {

    void insertCountryForm(CountryForm form);

    List<CountryForm> getAllCountryForms();

    void deleteAllCountryForms();

    void deleteCountryForm(int id);

    CountryForm getCountryForm(int id);

    void updateCountryForm(CountryForm form);
}
