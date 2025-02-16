package com.philately.web.dto;

import com.philately.paper.model.PaperName;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StampAddBindingModel {

    @Size(min = 5, max = 20, message = "Name length must be between 5 and 20 characters!")
    private String name;
    @Size(min = 5, max = 25, message = "Description length must be between 5 and 25 characters!")
    private String description;
    @Pattern(regexp = "^http(s)?://.+\\.(jpeg|jpg|png)$", message = "Please enter valid image URL!")
    private String imageUrl;
    @NotNull(message = "Price cannot be empty!")
    @DecimalMin(value = "0", inclusive = false, message = "Price must be greater than '0'!")
    private BigDecimal price;
    @NotNull(message = "You must select paper type!")
    private PaperName paperName;

}
