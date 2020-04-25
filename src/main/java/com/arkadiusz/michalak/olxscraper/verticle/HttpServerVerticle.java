package com.arkadiusz.michalak.olxscraper.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class HttpServerVerticle extends AbstractVerticle {
    private static final int PORT_NUMBER = 8080;

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.get("/offers/olx/:keyword").handler(this::getOlxOffersHandler);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(PORT_NUMBER, listeningResult -> {
                    if (listeningResult.succeeded()) {
                        System.out.println("Server started at port " + PORT_NUMBER);
                    } else {
                        System.err.println("Couldn't start server at port " + PORT_NUMBER);
                    }
                });
    }

    private void getOlxOffersHandler(RoutingContext context) {
        String keyword = context.request().getParam("keyword");
        DeliveryOptions options = new DeliveryOptions().addHeader("keyword", keyword);

        vertx.eventBus()
                .request("olxScrapingQueue", null, options, reply -> {
                    if (reply.succeeded()) {
                        JsonObject body = (JsonObject) reply.result().body();

                        context.response().putHeader("Content-Type", "text/json");
                        context.response().end(String.valueOf(body));
                    } else {
                        context.fail(reply.cause());
                    }
                });
    }
}