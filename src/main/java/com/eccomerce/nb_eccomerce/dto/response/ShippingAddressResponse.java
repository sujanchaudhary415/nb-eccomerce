package com.eccomerce.nb_eccomerce.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddressResponse {

    private String street;
    private String buildingName;
    private String city;
    private String state;
    private String country;
    private String pinCode;
}
