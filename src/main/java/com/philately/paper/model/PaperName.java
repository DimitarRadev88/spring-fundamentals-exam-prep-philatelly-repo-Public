package com.philately.paper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaperName {
    WOVE_PAPER("Has an even texture without any particular distinguishing features.", "Wove paper"),
    LAID_PAPER("When held up to the light, shows parallel lines of greater or less width running across the stamp.", "Laid paper"),
    GRANITE_PAPER("Has tiny specks of coloured fibre in it, which can usually be seen with the naked eye.", "Granite paper"),;

    private final String description;
    private final String formattedName;

}
