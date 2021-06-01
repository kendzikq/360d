package com.example._360d.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class NBPResponse {

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public NBPResponse(Content[] content){
        this.content = content;
    }

    private Content[] content;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {

        private String table;
        private String no;
        private LocalDate effectiveDate;
        private Rate[] rates;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Rate {

        private String currency;
        private String code;
        private Double mid;
    }
}
