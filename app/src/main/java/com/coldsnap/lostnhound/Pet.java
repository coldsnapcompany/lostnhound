package com.coldsnap.lostnhound;

public class Pet {

    String name;
    String type;
    String postcode;
    String colour;

    public Pet(String name, String type, String postcode, String colour) {
        this.name = name;
        this.type = type;
        this.postcode = postcode;
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
