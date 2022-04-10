package com.example.test_java_task.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("agreeTerms")
    private Integer agreeTerms;
    @JsonProperty("sectors")
    private List<Integer> sectors;
}