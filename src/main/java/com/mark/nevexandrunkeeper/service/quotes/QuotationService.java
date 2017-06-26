package com.mark.nevexandrunkeeper.service.quotes;

import com.mark.nevexandrunkeeper.model.QuotationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by NeVeX on 7/13/2016.
 */
@Service
public class QuotationService {

    private static QuotationResponse DEFAULT_QUOTE;
    private final Quoter quoter;

    @Autowired
    private QuotationService(Quoter quoter) {
        this.quoter = quoter;
    }

    static {
        DEFAULT_QUOTE = new QuotationResponse();
        DEFAULT_QUOTE.setAuthor("NeVeX");
        DEFAULT_QUOTE.setText("Well done - so proud of you! Keep it up! :-)");
    }

    public QuotationResponse getQuote() {
        for ( int i = 0; i < 3; i++) {
            Optional<QuotationResponse> response = this.quoter.getQuote();
            if (response.isPresent()) {
                return response.get();
            }
        }
        // If we don't get a response above, return a default quote
        return DEFAULT_QUOTE;
    }


}
