package com.eccomerce.nb_eccomerce.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressUpdateRequest {

    @Size(min = 5, max = 100)
    private String street;
    @Size(min = 2, max = 100)
    private String buildingName;
    @Size(min = 2, max = 50)
    private String city;
    @Size(min = 2, max = 50)
    private String state;
    @Size(min = 2, max = 50)
    private String country;
    @Size(min = 4, max = 10)
    private String pinCode;
}
