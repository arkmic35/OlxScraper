package com.arkadiusz.michalak.olxscraper;

import io.vertx.core.Vertx;

import java.util.function.Consumer;

public class ServerRunner {
    public static void main(String[] args) {
        Class<ServerVerticle> verticleClass = ServerVerticle.class;

        Consumer<Vertx> runner = vertx ->
                vertx.deployVerticle(verticleClass.getName());

        Vertx vertx = Vertx.vertx();
        runner.accept(vertx);
    }
}
