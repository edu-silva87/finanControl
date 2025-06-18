package com.finan_control.email_service.dtos;

import java.util.UUID;

public record EmailRecodDto(UUID userId, 
                            String emailTo,
                            String subject, 
                            String text) {

}
