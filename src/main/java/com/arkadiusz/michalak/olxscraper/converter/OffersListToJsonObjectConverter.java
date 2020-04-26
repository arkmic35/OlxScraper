package com.arkadiusz.michalak.olxscraper.converter;

import com.arkadiusz.michalak.olxscraper.model.Offer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class OffersListToJsonObjectConverter implements Converter<List<Offer>, JsonObject> {

    @Override
    public JsonObject convert(List<Offer> input) {

        JsonObject result = new JsonObject();
        JsonArray offersArray = new JsonArray();

        for (Offer offer : input) {
            JsonObject object = new JsonObject()
                    .put("id", offer.getId())
                    .put("name", offer.getName())
                    .put("price", offer.getPrice());

            offersArray.add(object);
        }

        result.put("offers", offersArray);

        return result;
    }
}
