package com.mark.nevexandrunkeeper.quote.forismatic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created by NeVeX on 7/13/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class ForismaticResponse implements Serializable {

    @JsonProperty("quoteText")
    private String text;
    @JsonProperty("quoteAuthor")
    private String author;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return True if the author and text is not blank
     */
    boolean isValid() {
        return !StringUtils.isEmpty(author) && !StringUtils.isEmpty(text);
    }

}