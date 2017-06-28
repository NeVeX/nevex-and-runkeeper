package com.mark.nevexandrunkeeper.quote;

import java.util.Optional;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
public interface Quoter {

    /**
     * Returns a quote, or an empty optional if none can be provided
     */
    Optional<Quote> getQuote();

}
