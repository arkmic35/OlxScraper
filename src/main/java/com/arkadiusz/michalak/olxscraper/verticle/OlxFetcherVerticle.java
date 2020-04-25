package com.arkadiusz.michalak.olxscraper.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

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

        JsonObject result = new JsonObject().put("offers", offersArray);

        message.reply(result);
    }
}
