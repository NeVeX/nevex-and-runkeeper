package com.mark.nevexandrunkeeper.quote.forismatic;

import com.mark.nevexandrunkeeper.config.ApplicationProperties;
import com.mark.nevexandrunkeeper.quote.Quote;
import com.mark.nevexandrunkeeper.quote.Quoter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Created by Mark Cunningham on 6/26/2017.
 */
@Service
class ForismaticQouter implements Quoter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForismaticQouter.class.getName());
    private final String quotationUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final HttpEntity API_HTTP_ENTITY;

    static {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Cache-Control", "no-cache");
        // We have to set a user agent - otherwise we get 403's from the API! :-)
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        API_HTTP_ENTITY = new HttpEntity<>(headers);
    }

    @Autowired
    ForismaticQouter(ApplicationProperties applicationProperties) {
        this.quotationUrl = applicationProperties.getQuotation().getForismaticUrl();
    }

    @Override
    public Optional<Quote> getQuote() {

        ResponseEntity<ForismaticResponse> response;
        try {
            response = restTemplate.exchange(quotationUrl, HttpMethod.GET, API_HTTP_ENTITY, ForismaticResponse.class);
        } catch (RestClientException ex) {
            LOGGER.error("An error occurred while trying to get a quote from the forismatic api. Url [{}].", quotationUrl, ex);
            return Optional.empty();
        }

        if ( !response.getStatusCode().is2xxSuccessful()) {
            LOGGER.warn("Received a non-ok code [{}] from the forismatic api. Url [{}].", response.getStatusCode(), quotationUrl);
            return Optional.empty();
        }

        Quote quote = null;
        if ( response.getBody() != null && response.getBody().isValid()) {
            quote = new Quote(response.getBody().getAuthor(), response.getBody().getText());
        }
        return Optional.ofNullable(quote);
    }

}
