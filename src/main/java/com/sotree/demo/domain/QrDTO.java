package com.sotree.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrDTO {
    private String authUrl;
    private String data;
    private int c_index;
    private int width;
    private int height;
}
