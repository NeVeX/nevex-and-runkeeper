package com.mark.nevexandrunkeeper.service.quotes;

import com.mark.nevexandrunkeeper.config.ApplicationProperties;
import com.mark.nevexandrunkeeper.model.QuotationResponse;
import com.mark.nevexandrunkeeper.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
@Service
class ForismaticQouter implements Quoter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForismaticQouter.class.getName());
    private final String quotationUrl;

    @Autowired
    ForismaticQouter(ApplicationProperties applicationProperties) {
        this.quotationUrl = applicationProperties.getQuotation().getForismaticUrl();
    }

    @Override
    public Optional<QuotationResponse> getQuote() {
        QuotationResponse response = null;
        try {
            response = HttpClientUtil.execute(quotationUrl, null, "GET", QuotationResponse.class);
        } catch (Exception e ) {
            LOGGER.error("There was a problem contacting the Forismatic quotation API [{}]. Error message: [{}]", quotationUrl, e.getMessage());
        }
        return Optional.ofNullable(response);
    }

}
