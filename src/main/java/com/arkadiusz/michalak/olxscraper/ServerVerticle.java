package com.arkadiusz.michalak.olxscraper;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

import java.util.Random;

public class ServerVerticle extends AbstractVerticle {

    public static final int PORT_NUMBER = 8080;
    private final Random random = new Random();

    @Override
    public void start() {
        Router router = Router.router(vertx);

        router.route().handler(routingContext ->
                routingContext
                        .response()
                        .putHeader("content-type", "text/html")
                        .end("Hello World " + random.nextInt(10)));

        vertx.createHttpServer().requestHandler(router).listen(PORT_NUMBER);

        System.out.println("Server started on port " + PORT_NUMBER);
    }
}