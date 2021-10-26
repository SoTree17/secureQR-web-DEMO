package com.sotree.demo.domain.ServerSide;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response Data Transfer Object : FOR AUTHENTICATION
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResDTO {
    private String resURL;  // Information in a Request
}
