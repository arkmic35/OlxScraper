package com.arkadiusz.michalak.olxscraper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

import java.util.Random;

public class ServerVerticle extends AbstractVerticle {

    public static final int PORT_NUMBER = 8080;
    private final Random random = new Random();

    @Override
    public void start() {
        Router router = Router.router(vertx);

        router.route(HttpMethod.GET, "/offers/olx/:keyword")
                .handler(routingContext -> {
                    String keyword = routingContext.request().getParam("keyword");

                    routingContext
                            .response()
                            .putHeader("content-type", "text/json")
                            .end("Looking for " + keyword + " in OLX.");
                });

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(PORT_NUMBER);

        System.out.println("Server started on port " + PORT_NUMBER);
    }
}