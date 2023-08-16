package com.example.footballmanager.dto.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TeamRequestDto {
    private String name;
    private String country;
    private String city;
    private Integer commission;
    private BigDecimal balance;
}
