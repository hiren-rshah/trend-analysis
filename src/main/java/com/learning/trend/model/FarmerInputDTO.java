package com.learning.trend.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class FarmerInputDTO {


    @JsonAlias("year")
    private int year;

    @JsonAlias("month")
    private int month;

    @JsonAlias("crop_type")
    private String cropType;
    @JsonAlias("soil_type")
    private String soilType;

    @JsonAlias("region")
    String region;

    @JsonAlias("season")
    private String season;

    @JsonAlias("rainfall")
    private int rainfall; // in mm

    @JsonAlias("farmer_land_size")
    private int farmerLandSize; // in acres

    @JsonAlias("previous_input_usage")
    private String previousInputUsage;

    // Getter and Setter methods

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getRainfall() {
        return rainfall;
    }

    public void setRainfall(int rainfall) {
        this.rainfall = rainfall;
    }

    public int getFarmerLandSize() {
        return farmerLandSize;
    }

    public void setFarmerLandSize(int farmerLandSize) {
        this.farmerLandSize = farmerLandSize;
    }

    public String getPreviousInputUsage() {
        return previousInputUsage;
    }

    public void setPreviousInputUsage(String previousInputUsage) {
        this.previousInputUsage = previousInputUsage;
    }


    @Override
    public String toString() {
        return "FarmerInputDTO{" +
                "year=" + year +
                ", month=" + month +
                ", cropType='" + cropType + '\'' +
                ", soilType='" + soilType + '\'' +
                ", region='" + region + '\'' +
                ", season='" + season + '\'' +
                ", rainfall=" + rainfall +
                ", farmerLandSize=" + farmerLandSize +
                ", previousInputUsage='" + previousInputUsage + '\'' +
                '}';
    }
}
