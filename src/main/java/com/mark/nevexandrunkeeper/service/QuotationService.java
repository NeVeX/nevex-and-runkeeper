package com.mark.nevexandrunkeeper.service;

import com.mark.nevexandrunkeeper.model.QuotationResponse;
import com.mark.nevexandrunkeeper.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by NeVeX on 7/13/2016.
 */
@Service
public class QuotationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuotationService.class.getName());

    @Value("${quotation.url}")
    private String quotationUrl;

    private static QuotationResponse DEFAULT_QUOTE;

    static {
        DEFAULT_QUOTE = new QuotationResponse();
        DEFAULT_QUOTE.setAuthor("NeVeX");
        DEFAULT_QUOTE.setText("Well done - so proud of you! Keep it up! :-)");
    }

    public QuotationResponse getQuote() {
        for ( int i = 0; i < 3; i++) {
            QuotationResponse response = tryGetQuote();
            if (response != null) {
                return response;
            }
        }
        return DEFAULT_QUOTE;
    }

    private QuotationResponse tryGetQuote() {
        try {
            return HttpClientUtil.execute(quotationUrl, null, "GET", QuotationResponse.class);
        } catch (Exception e ) {
            LOGGER.error("There was a problem contacting the quotation service ["+quotationUrl+"]. Error: "+e.getMessage());
        }
        return null;
    }

}
