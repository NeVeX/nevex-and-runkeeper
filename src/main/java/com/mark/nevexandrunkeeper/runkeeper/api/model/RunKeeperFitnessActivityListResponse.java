package com.mark.nevexandrunkeeper.runkeeper.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NeVeX on 7/12/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RunKeeperFitnessActivityListResponse implements Serializable {

    @JsonProperty("size")
    private Integer size;
    @JsonProperty("items")
    private List<RunKeeperFitnessActivityResponse> items;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<RunKeeperFitnessActivityResponse> getItems() {
        return items;
    }

    public void setItems(List<RunKeeperFitnessActivityResponse> items) {
        this.items = items;
    }
}
