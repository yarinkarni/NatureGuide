package com.example.natureguide;


public class NatureLocation {

    //מחלקה שמהווה בסיס לאובייקטים של מיקומים בטבע
    //fields
    private String name;
    private String title;
    private String description;
    private int image;
    private double latLangv, latLangv1;


    //ctor
    public NatureLocation(String name, String title, String description, int image, double latLangv, double latLangv1) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.image = image;
        this.latLangv = latLangv;
        this.latLangv1 = latLangv1;
    }

    public NatureLocation() {
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getLatLangv() {
        return latLangv;
    }

    public void setLatLangv(double latLangv) {
        this.latLangv = latLangv;
    }

    public double getLatLangv1() {
        return latLangv1;
    }

    public void setLatLangv1(double latLangv1) {
        this.latLangv1 = latLangv1;
    }

    //methods
    @Override
    public String toString() {
        return "NatureLocation{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
