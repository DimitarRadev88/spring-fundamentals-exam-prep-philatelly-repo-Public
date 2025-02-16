package com.philately.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyStampsServiceViewModel {

    private String name;
    private String description;
    private String usedPaper;
    private BigDecimal price;
    private String imageUrl;

}
