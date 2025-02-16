package com.philately.stamp.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PurchasableStampServiceModel {

    private UUID id;
    private String name;
    private BigDecimal price;
    private String ImageUrl;

}
