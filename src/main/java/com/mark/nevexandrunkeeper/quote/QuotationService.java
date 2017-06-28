package com.mark.nevexandrunkeeper.quote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by NeVeX on 7/13/2016.
 */
@Service
public class QuotationService {

    private static final Quote DEFAULT_QUOTE = new Quote("NeVeX", "Well done - so proud of you! Keep it up! :-)");
    private final Quoter quoter;

    @Autowired
    private QuotationService(Quoter quoter) {
        this.quoter = quoter;
    }

    public Quote getQuote() {
        for ( int i = 0; i < 3; i++) {
            Optional<Quote> response = this.quoter.getQuote();
            if (response.isPresent()) {
                return response.get();
            }
        }
        // If we don't get a response above, return a default quote
        return DEFAULT_QUOTE;
    }


}
