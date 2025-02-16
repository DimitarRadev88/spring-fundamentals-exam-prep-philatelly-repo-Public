package com.philately.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferedStampsServiceViewModel {

    private UUID id;
    private String name;
    private String description;
    private String ownerUsername;
    private String imageUrl;

}
