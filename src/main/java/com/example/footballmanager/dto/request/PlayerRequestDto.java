package com.example.footballmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PlayerRequestDto {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;
    @NotNull(message = "Career start date is required")
    private LocalDate careerStartDate;
    @NotNull(message = "Team ID is required")
    private Long teamId;
}
