package com.arkadiusz.michalak.olxscraper.verticle;

import com.arkadiusz.michalak.olxscraper.converter.BufferToStringConverter;
import com.arkadiusz.michalak.olxscraper.converter.OffersListToJsonObjectConverter;
import com.arkadiusz.michalak.olxscraper.model.Offer;
import com.arkadiusz.michalak.olxscraper.parser.OlxResultsParser;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class OlxFetcherVerticle extends AbstractVerticle {

    private final BufferToStringConverter bufferToStringConverter;
    private final OlxResultsParser olxResultsParser;
    private final OffersListToJsonObjectConverter offersListToJsonObjectConverter;

    @Override
    public void start(Promise<Void> startPromise) {

        vertx.eventBus()
                .consumer("olxScrapingQueue", this::onMessage);

        startPromise.complete();

        log.info("OlxFetcherVerticle started");
    }

    private void onMessage(Message<Void> message) {
        String keyword = message.headers().get("keyword");
        String requestUri = String.format("/oferty/q-%s/", keyword);

        HttpRequest<JsonObject> accept = WebClient.create(vertx)
                .get(443, "olx.pl", requestUri)
                .ssl(true)
                .putHeader("Accept", "application/json")
                .expect(ResponsePredicate.SC_OK)
                .as(BodyCodec.create(buffer -> {
                    String html = bufferToStringConverter.convert(buffer);
                    List<Offer> offers = olxResultsParser.parse(html);

                    return offersListToJsonObjectConverter.convert(offers);
                }));

        accept.send(
                result -> {
                    if (result.succeeded()) {
                        message.reply(result.result().body());
                    } else {
                        message.reply(result.cause());
                    }
                }
        );
    }
}
