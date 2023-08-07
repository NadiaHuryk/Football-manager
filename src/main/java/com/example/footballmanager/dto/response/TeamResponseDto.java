package com.example.footballmanager.dto.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class TeamResponseDto {
    private Long id;
    private String name;
    private String country;
    private String city;
    private Double commission;
    private BigDecimal balance;
    private List<Long> playerIds;
}
