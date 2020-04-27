package com.arkadiusz.michalak.olxscraper;

import com.arkadiusz.michalak.olxscraper.httpserver.HttpServerVerticle;
import com.arkadiusz.michalak.olxscraper.olxfetcher.OlxFetcherVerticle;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

public class ServerRunner {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Injector injector = Guice.createInjector(Stage.PRODUCTION, new ComponentsModule());
        HttpServerVerticle httpServerVerticle = injector.getInstance(HttpServerVerticle.class);
        OlxFetcherVerticle olxFetcherVerticle = injector.getInstance(OlxFetcherVerticle.class);

        deployVerticle(vertx, olxFetcherVerticle)
                .compose(event -> deployVerticle(vertx, httpServerVerticle));
    }

    private static Future<Object> deployVerticle(Vertx vertx, Verticle verticle) {
        return Future.future(
                event -> vertx.deployVerticle(verticle,
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
