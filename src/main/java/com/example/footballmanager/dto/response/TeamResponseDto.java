package com.example.footballmanager.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TeamResponseDto {
    private Long id;
    private String name;
    private String country;
    private String city;
    private Integer commission;
    private BigDecimal balance;
}
