package com.example.footballmanager.dto.request;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class TeamRequestDto {
    private String name;
    private String country;
    private String city;
    private Double commission;
    private BigDecimal balance;
    private List<Long> playerIds;
}
