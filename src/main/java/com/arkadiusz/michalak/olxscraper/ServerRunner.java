package com.arkadiusz.michalak.olxscraper;

import com.arkadiusz.michalak.olxscraper.verticle.HttpServerVerticle;
import com.arkadiusz.michalak.olxscraper.verticle.OlxFetcherVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class ServerRunner {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        deployVerticle(vertx, OlxFetcherVerticle.class.getName())
                .compose(event -> deployVerticle(vertx, HttpServerVerticle.class.getName()));
    }

    private static Future<Object> deployVerticle(Vertx vertx, String className) {
        return Future.future(
                event -> vertx.deployVerticle(className,
                        asyncResult -> {
                            if (asyncResult.succeeded()) {
                                event.complete();
                            } else {
                                event.fail(asyncResult.cause());
                            }
                        })
        );
    }
}
