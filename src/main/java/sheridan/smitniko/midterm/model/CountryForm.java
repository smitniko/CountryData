package sheridan.smitniko.midterm.model;

import javax.validation.constraints.*;
import java.io.Serializable;

public class CountryForm implements Serializable {

    private int id = 0;

    @NotBlank
    @Size(max = 30)
    @Pattern(regexp = "[A-Za-z]*")
    private String name = "";

    @NotBlank
    @Size(max = 30)
    @Pattern(regexp = "[A-Za-z]*")
    private String continent = "";

    @NotBlank
    @Pattern(regexp = "[A-Za-z]*")
    private String region = "";

    @NotNull
    @Min(1)
    @Max(9999)
    private int surfaceArea = 1;

    @NotNull
    @Min(1)
    @Max(9999)
    private int population = 1;


    public CountryForm() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(int surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

}
