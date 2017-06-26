package com.mark.nevexandrunkeeper.service.quotes;

import com.mark.nevexandrunkeeper.model.QuotationResponse;

import java.util.Optional;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
interface Quoter {

    /**
     * Returns a quote, or an empty optional if none can be provided
     */
    Optional<QuotationResponse> getQuote();

}
