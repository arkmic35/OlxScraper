package com.arkadiusz.michalak.olxscraper.converter;

import com.arkadiusz.michalak.olxscraper.model.Offer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OffersListToJsonObjectConverterTest {

    private final OffersListToJsonObjectConverter converter = new OffersListToJsonObjectConverter();

    @Test
    void shouldBeAbleToConvert() {
        //given
        List<Offer> offers = Arrays.asList(
                Offer.of("1", "First item", "21.99 PLN"),
                Offer.of("2", "Second item", "31.99 PLN")
        );


        JsonObject expectedJsonObject = new JsonObject()
                .put("offers", new JsonArray()
                        .add(new JsonObject().put("id", "1").put("name", "First item").put("price", "21.99 PLN"))
                        .add(new JsonObject().put("id", "2").put("name", "Second item").put("price", "31.99 PLN"))
                );

        //when
        JsonObject result = converter.convert(offers);

        //then
        assertEquals(expectedJsonObject, result);
    }
}