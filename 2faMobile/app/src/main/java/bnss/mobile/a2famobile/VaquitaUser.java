package bnss.mobile.a2famobile;

import java.io.Serializable;

public class VaquitaUser implements Serializable {
    String firstName, lastName;


    public VaquitaUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString(){
        return firstName + lastName;
    }
}
