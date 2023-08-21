package com.example.footballmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TeamRequestDto {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Country is required")
    private String country;
    @NotBlank(message = "City is required")
    private String city;
    @PositiveOrZero(message = "Commission must be 0 or greater")
    private Integer commission;
    @PositiveOrZero(message = "Balance must be 0 or greater")
    private BigDecimal balance;
}
