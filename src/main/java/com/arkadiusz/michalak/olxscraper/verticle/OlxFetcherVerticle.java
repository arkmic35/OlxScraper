package com.arkadiusz.michalak.olxscraper.verticle;

import com.arkadiusz.michalak.olxscraper.converter.BufferToStringConverter;
import com.arkadiusz.michalak.olxscraper.model.OffersDto;
import com.arkadiusz.michalak.olxscraper.model.OffersDtoCodec;
import com.arkadiusz.michalak.olxscraper.parser.OlxResultsParser;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class OlxFetcherVerticle extends AbstractVerticle {

    private final OffersDtoCodec offersDtoCodec;
    private final BufferToStringConverter bufferToStringConverter;
    private final OlxResultsParser olxResultsParser;

    @Override
    public void start(Promise<Void> startPromise) {

        vertx.eventBus()
                .registerDefaultCodec(OffersDto.class, offersDtoCodec)
                .consumer("olxScrapingQueue", this::onMessage);

        startPromise.complete();

        log.info("OlxFetcherVerticle started");
    }

    private void onMessage(Message<Void> message) {
        String keyword = message.headers().get("keyword");
        String requestUri = String.format("/oferty/q-%s/", keyword);

        HttpRequest<OffersDto> accept = WebClient.create(vertx)
                .get(443, "olx.pl", requestUri)
                .ssl(true)
                .putHeader("Accept", "application/json")
                .expect(ResponsePredicate.SC_OK)
                .as(BodyCodec.create(buffer -> {
                    String html = bufferToStringConverter.convert(buffer);
                    return olxResultsParser.parse(html);
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
