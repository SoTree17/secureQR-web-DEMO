package com.sotree.demo.domain.ClientSide;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdditionDTO {
    private String authUrl;
    private String token;
    private String crypto;
    private String hash;
}
