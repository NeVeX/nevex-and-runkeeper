package com.mark.nevexandrunkeeper.model.runkeeper;

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
public class RunKeeperFriendsReplyWrapperResponse implements Serializable {

    @JsonProperty("size")
    private Integer size;
    @JsonProperty("items")
    private List<RunKeeperFriendsReplyResponse> items;
    @JsonProperty("next")
    private String nextUri;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<RunKeeperFriendsReplyResponse> getItems() {
        return items;
    }

    public void setItems(List<RunKeeperFriendsReplyResponse> items) {
        this.items = items;
    }

    public String getNextUri() {
        return nextUri;
    }

    public void setNextUri(String nextUri) {
        this.nextUri = nextUri;
    }
}
