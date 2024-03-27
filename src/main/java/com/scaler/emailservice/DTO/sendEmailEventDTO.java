package com.scaler.userservice.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class sendEmailEventDTO {
    private String to;
    private String from;
    private String subject;
    private String body;
}
