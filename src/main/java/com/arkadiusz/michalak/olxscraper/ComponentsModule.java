package com.arkadiusz.michalak.olxscraper;

import com.arkadiusz.michalak.olxscraper.httpserver.HttpServerVerticle;
import com.arkadiusz.michalak.olxscraper.olxfetcher.OlxFetcherVerticle;
import com.arkadiusz.michalak.olxscraper.olxfetcher.converter.BufferToStringConverter;
import com.arkadiusz.michalak.olxscraper.olxfetcher.model.OffersDtoCodec;
import com.arkadiusz.michalak.olxscraper.olxfetcher.parser.OlxResultsParser;
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
