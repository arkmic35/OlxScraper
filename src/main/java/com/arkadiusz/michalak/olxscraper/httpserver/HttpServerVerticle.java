package com.arkadiusz.michalak.olxscraper.httpserver;

import com.arkadiusz.michalak.olxscraper.Profile;
import com.arkadiusz.michalak.olxscraper.olxfetcher.model.OffersDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class HttpServerVerticle extends AbstractVerticle {

    private final ObjectMapper objectMapper;
    private final Profile profile;

    private static final int PORT_NUMBER = 8080;

    @Override
    public void start() {
        Router router = Router.router(vertx);

        if (profile == Profile.DEVELOPMENT) {
            log.warn("Development profile is set");

            router.route().handler(CorsHandler.create(".*.")
                    .allowedMethod(HttpMethod.GET)
                    .allowedHeader("Access-Control-Request-Method")
                    .allowedHeader("Access-Control-Allow-Credentials")
                    .allowedHeader("Access-Control-Allow-Origin")
                    .allowedHeader("Access-Control-Allow-Headers")
                    .allowedHeader("Content-Type"));
        }

        router.get("/offers/olx/:keyword").handler(this::getOlxOffersHandler);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(PORT_NUMBER, listeningResult -> {
                    if (listeningResult.succeeded()) {
                        log.info("Server started at port " + PORT_NUMBER);
                    } else {
                        log.error("Couldn't start server at port " + PORT_NUMBER);
                    }
                });
    }

    private void getOlxOffersHandler(RoutingContext context) {
        String keyword = context.request().getParam("keyword");
        log.info("GET /offers/olx/" + keyword);
        DeliveryOptions options = new DeliveryOptions().addHeader("keyword", keyword);

        vertx.eventBus()
                .request("olxScrapingQueue", null, options, (Handler<AsyncResult<Message<OffersDto>>>) reply -> {
                    if (reply.succeeded()) {
                        try {
                            OffersDto offersDto = reply.result().body();

                            context.response().putHeader("Content-Type", "text/json");
                            context.response().end(objectMapper.writeValueAsString(offersDto));

                            log.info("Endpoint handler finished OK");
                        } catch (JsonProcessingException e) {
                            context.fail(e);
                            log.error("Endpoint handler failed", e);
                        }
                    } else {
                        context.fail(reply.cause());
                        log.error("Endpoint handler failed", reply.cause());
                    }
                });
    }
}