package com.arkadiusz.michalak.olxscraper;

import com.arkadiusz.michalak.olxscraper.model.ScrapingResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;

import java.util.Arrays;
import java.util.Random;

public class ServerVerticle extends AbstractVerticle {

    public static final int PORT_NUMBER = 8080;
    private final Random random = new Random();

    @Override
    public void start() {
        Router router = Router.router(vertx);
        addOlxOffersEndpointTo(router);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(PORT_NUMBER);

        System.out.println("Server started on port " + PORT_NUMBER);
    }

    private void addOlxOffersEndpointTo(Router router) {
        router.route(HttpMethod.GET, "/offers/olx/:keyword")
                .produces("text/json")
                .handler(routingContext -> {
                    String keyword = routingContext.request().getParam("keyword");

                    ScrapingResponse response = ScrapingResponse.of(
                            Arrays.asList(
                                    ScrapingResponse.ResponseSegment.of("1", "VW Passat zdrowy", "7000 PLN"),
                                    ScrapingResponse.ResponseSegment.of("2", "VW Passat niebity igła", "8500 PLN")
                            )
                    );

                    routingContext.response()
                            .end(Json.encodeToBuffer(response));
                });
    }
}