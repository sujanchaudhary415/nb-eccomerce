package com.eccomerce.nb_eccomerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AddressRequest {
    @NotBlank
    @Size(min=5, max=30,message = "Street should be between 5 to 30 characters")
    private String street;

    @NotBlank
    @Size(min=5, max=30,message = "Building name should be between 5 to 30 characters")
    private String buildingName;

    @NotBlank
    @Size(min=5, max=30,message = "city name should be between 5 to 30 characters")
    private String city;

    @NotBlank
    @Size(min=5, max=30,message = "State should be between 5 to 30 characters")
    private String state;

    @NotBlank
    @Size(min=5, max=30,message = "Country name should be between 5 to 30 characters")
    private String country;

    @NotBlank
    @Size(min=5, max=30,message = "PinCode should be between 5 to 30 characters")
    private String pinCode;
}
