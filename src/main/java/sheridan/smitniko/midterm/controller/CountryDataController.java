package sheridan.smitniko.midterm.controller;

import sheridan.smitniko.midterm.model.CountryForm;
import sheridan.smitniko.midterm.service.CountryDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CountryDataController {

    private final Logger logger = LoggerFactory.getLogger(CountryDataController.class);

    private static final String[] continents = {
            "--- Select Countries ---",
            "North America", "Africa",
            "South America", "Europe"};

    private final CountryDataService countryDataService;

    public CountryDataController(CountryDataService countryDataService){
        this.countryDataService = countryDataService;
    }

    @GetMapping(value={"/", "/Index"})
    public String index(){
        logger.trace("index() is called");
        return "Index";
    }

    @GetMapping("/AddCountry")
    public ModelAndView addCountry(){
        logger.trace("addCountry() is called");
        ModelAndView modelAndView =
                new ModelAndView("AddCountry",
                        "form", new CountryForm());
        modelAndView.addObject("continents", continents);
        return modelAndView;
    }

    @PostMapping("/InsertCountry")
    public String insertCountry(
            @Validated @ModelAttribute("form") CountryForm form,
            BindingResult bindingResult,
            Model model){
        logger.trace("insertCountry() is called");
        // checking for the input validation errors
        if (bindingResult.hasErrors()) {
            logger.trace("input validation errors");
            //model.addAttribute("form", form);
            model.addAttribute("continents", continents);
            return "AddCountry";
        } else {
            logger.trace("the user inputs are correct");
            countryDataService.insertCountryForm(form);
            return "redirect:ConfirmInsert/" + form.getId();
        }
    }

    @GetMapping("/ConfirmInsert/{id}")
    public String confirmInsert(@PathVariable(name = "id") String strId, Model model){
        logger.trace("confirmInsert() is called");
        try {
            int id = Integer.parseInt(strId);
            logger.trace("looking for the data in the database");
            CountryForm form = countryDataService.getCountryForm(id);
            if (form == null) {
                logger.trace("no data for this id=" + id);
                return "DataNotFound";
            } else {
                logger.trace("showing the data");
                model.addAttribute("form", form);
                return "ConfirmInsert";
            }
        } catch (NumberFormatException e) {
            logger.trace("the id in not an integer");
            return "DataNotFound";
        }
    }

    @GetMapping("/ListCountries")
    public ModelAndView listCountries() {
        logger.trace("listCountries() is called");
        List<CountryForm> list = countryDataService.getAllCountryForms();
        return new ModelAndView("ListCountries",
                "countries", list);
    }

    @GetMapping("/DeleteAll")
    public String deleteAll(){
        logger.trace("deleteAll() is called");
        countryDataService.deleteAllCountryForms();
        return "redirect:ListCountries";
    }

    @GetMapping("CountryDetails/{id}")
    public String countryDetails(@PathVariable String id, Model model){
        logger.trace("countryDetails() is called");
        try {
            CountryForm form = countryDataService.getCountryForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("country", form);
                return "CountryDetails"; // show the Country data in the form to edit
            } else {
                logger.trace("no data for this id=" + id);
                return "DataNotFound";
            }
        } catch (NumberFormatException e) {
            logger.trace("the id is missing or not an integer");
            return "DataNotFound";
        }
    }

    // a user clicks "Delete" link (in the table) to "DeleteCountry"
    @GetMapping("/DeleteCountry")
    public String deleteCountry(@RequestParam String id, Model model) {
        logger.trace("deleteCountry() is called");
        try {
            CountryForm form = countryDataService.getCountryForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("country", form);
                return "DeleteCountry"; // ask "Do you really want to remove?"
            } else {
                return "redirect:ListCountries";
            }
        } catch (NumberFormatException e) {
            return "redirect:ListCountries";
        }
    }

    // a user clicks "Remove Record" button in "DeleteCountry" page,
    // the form submits the data to "RemoveCountry"
    @PostMapping("/RemoveCountry")
    public String removeCountry(@RequestParam String id) {
        logger.trace("removeCountry() is called");
        try {
            countryDataService.deleteCountryForm(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            logger.trace("the id is missing or not an integer");
        }
        return "redirect:ListCountries";
    }

    // a user clicks "Edit" link (in the table) to "EditCountry"
    @GetMapping("/EditCountry")
    public String editCountry(@RequestParam String id, Model model) {
        logger.trace("editCountry() is called");
        try {
            CountryForm form = countryDataService.getCountryForm(Integer.parseInt(id));
            if (form != null) {
                model.addAttribute("form", form);
                model.addAttribute("continents", continents);
                return "EditCountry";
            } else {
                logger.trace("no data for this id=" + id);
                return "redirect:ListCountries";
            }
        } catch (NumberFormatException e) {
            logger.trace("the id is missing or not an integer");
            return "redirect:ListCountries";
        }
    }

    // the form submits the data to "UpdateCountry"
    @PostMapping("/UpdateCountry")
    public String updateCountry(
            @Validated @ModelAttribute("form") CountryForm form,
            BindingResult bindingResult,
            Model model) {
        logger.trace("updateCountry() is called");
        // checking for the input validation errors
        if (bindingResult.hasErrors()) {
            logger.trace("input validation errors");
            //model.addAttribute("form", form);
            model.addAttribute("continents", continents);
            return "EditCountry";
        } else {
            logger.trace("the user inputs are correct");
            countryDataService.updateCountryForm(form);
            logger.debug("id = " + form.getId());
            return "redirect:CountryDetails/" + form.getId();
        }
    }
}
