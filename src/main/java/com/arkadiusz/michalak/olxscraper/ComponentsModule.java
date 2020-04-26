package com.arkadiusz.michalak.olxscraper;

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
        return new OlxFetcherVerticle(olxResultsParser());
    }

    @Provides
    @Singleton
    @Exposed
    public OlxResultsParser olxResultsParser() {
        return new OlxResultsParser();
    }

    @Override
    protected void configure() {

    }
}
