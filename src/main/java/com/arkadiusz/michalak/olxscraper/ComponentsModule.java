package com.arkadiusz.michalak.olxscraper;

import com.arkadiusz.michalak.olxscraper.converter.BufferToStringConverter;
import com.arkadiusz.michalak.olxscraper.converter.OffersListToJsonObjectConverter;
import com.arkadiusz.michalak.olxscraper.parser.OlxResultsParser;
import com.arkadiusz.michalak.olxscraper.verticle.HttpServerVerticle;
import com.arkadiusz.michalak.olxscraper.verticle.OlxFetcherVerticle;
import com.google.inject.Exposed;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;

import javax.inject.Singleton;

public class ComponentsModule extends PrivateModule {

    @Provides
    @Singleton
    @Exposed
    public HttpServerVerticle httpServerVerticle() {
        return new HttpServerVerticle();
    }

    @Provides
    @Singleton
    @Exposed
    public OlxFetcherVerticle olxFetcherVerticle() {
        return new OlxFetcherVerticle(bufferToStringConverter(), olxResultsParser(), offersListToJsonObjectConverter());
    }

    @Provides
    @Singleton
    @Exposed
    public BufferToStringConverter bufferToStringConverter() {
        return new BufferToStringConverter();
    }

    @Provides
    @Singleton
    @Exposed
    public OlxResultsParser olxResultsParser() {
        return new OlxResultsParser();
    }

    @Provides
    @Singleton
    @Exposed
    public OffersListToJsonObjectConverter offersListToJsonObjectConverter() {
        return new OffersListToJsonObjectConverter();
    }

    @Override
    protected void configure() {

    }
}
