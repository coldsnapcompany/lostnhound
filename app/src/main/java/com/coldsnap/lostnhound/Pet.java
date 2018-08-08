package com.coldsnap.lostnhound;

public class Pet {

    String name;
    String type;
    String postcode;
    String colour;
    String image;

    public Pet() {
    }

    public Pet(String name, String type, String postcode, String colour, String image) {

        if(name.trim().equals("")) { //if the pet has no name collar and nothing is entered
            name = "< unknown name >";
        }

        this.name = name;
        this.type = type;
        this.postcode = postcode;
        this.colour = colour;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
