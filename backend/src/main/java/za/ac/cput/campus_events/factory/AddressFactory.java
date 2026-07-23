package za.ac.cput.campus_events.factory;

import za.ac.cput.campus_events.domain.Address;

public class AddressFactory {

    public static Address createAddress(String street, String suburb, String city,
                                        String postalCode, String province){

        if(street == null || street.length() < 3){
            return null;
        }

        if(suburb == null || suburb.length() < 3){
            return null;
        }

        if(city == null || city.length() < 3){
            return null;
        }

        if(postalCode == null || postalCode.length() < 3){
            return null;
        }

        if(province == null || province.length() < 3){
            return null;
        }



        return new Address.Builder()
                .setStreet(street)
                .setCity(city)
                .setPostalCode(postalCode)
                .setSuburb(suburb)
                .setProvince(province)
                .build();
    }
}
