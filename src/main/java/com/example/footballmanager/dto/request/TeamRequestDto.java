package com.example.footballmanager.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    @Min(value = 0, message = "Commission must be 0 or greater")
    @Max(value = 10, message = "Commission must be less than or equal to 10")
    private Integer commission;
    @PositiveOrZero(message = "Balance must be 0 or greater")
    private BigDecimal balance;
}
