package com.arkadiusz.michalak.olxscraper.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;

import java.util.function.Function;

public class OlxFetcherVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {

        vertx.eventBus()
                .consumer("olxScrapingQueue", this::onMessage);

        startPromise.complete();

        System.out.println("OlxFetcherVerticle started");
    }

    private void onMessage(Message<Void> message) {
        String keyword = message.headers().get("keyword");
        String requestUri = String.format("/oferty/q-%s/", keyword);

        Function<Buffer, JsonObject> function = buffer -> {
            JsonArray offersArray = new JsonArray()
                    .add(
                            new JsonObject()
                                    .put("id", "1")
                                    .put("name", keyword + " dobry stan")
                                    .put("price", "7000 PLN")
                    )
                    .add(
                            new JsonObject()
                                    .put("id", "1")
                                    .put("name", keyword + " igła")
                                    .put("price", "8000 PLN")
                    );

            return new JsonObject().put("offers", offersArray);
        };

        HttpRequest<JsonObject> accept = WebClient.create(vertx)
                .get(443, "olx.pl", requestUri)
                .ssl(true)
                .putHeader("Accept", "application/json")
                .expect(ResponsePredicate.SC_OK)
                .as(BodyCodec.create(function));

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
