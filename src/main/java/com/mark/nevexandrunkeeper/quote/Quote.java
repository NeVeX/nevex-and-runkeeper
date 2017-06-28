package com.mark.nevexandrunkeeper.quote;

import org.springframework.util.StringUtils;

/**
 * Created by Mark Cunningham on 6/27/2017.
 */
public final class Quote {

    private final String text;
    private final String author;

    public Quote(String author, String text) {
        if (StringUtils.isEmpty(author)) {
            throw new IllegalArgumentException("Provided quote author is blank");
        }
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Provided quote text is blank");
        }
        this.author = author;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public String getQuote() {
        return text + " - " + author;
    }

}
