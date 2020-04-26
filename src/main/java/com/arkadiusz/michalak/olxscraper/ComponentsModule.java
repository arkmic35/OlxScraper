package com.arkadiusz.michalak.olxscraper;

import com.arkadiusz.michalak.olxscraper.converter.BufferToStringConverter;
import com.arkadiusz.michalak.olxscraper.model.OffersDtoCodec;
import com.arkadiusz.michalak.olxscraper.parser.OlxResultsParser;
import com.arkadiusz.michalak.olxscraper.verticle.HttpServerVerticle;
import com.arkadiusz.michalak.olxscraper.verticle.OlxFetcherVerticle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Exposed;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;

import javax.inject.Singleton;

public class ComponentsModule extends PrivateModule {

    @Provides
    @Singleton
    @Exposed
    public HttpServerVerticle httpServerVerticle() {
        return new HttpServerVerticle(objectMapper());
    }

    @Provides
    @Singleton
    @Exposed
    public OlxFetcherVerticle olxFetcherVerticle() {
        return new OlxFetcherVerticle(
                new OffersDtoCodec(objectMapper()),
                new BufferToStringConverter(),
                new OlxResultsParser()
        );
    }

    @Provides
    @Singleton
    @Exposed
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Override
    protected void configure() {

    }
}
