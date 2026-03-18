package com.learning.trend.model;

public class FarmerInputFeatures {

    private int year;
    private int month;
    private String cropType;
    private String soilType;
    private String region;
    private String season;
    private int rainfall; // in mm
    private int farmerLandSize; // in acres
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
        return "FarmerInputFeatures{" +
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
