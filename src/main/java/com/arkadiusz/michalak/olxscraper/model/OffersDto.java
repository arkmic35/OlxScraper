package com.arkadiusz.michalak.olxscraper.model;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class OffersDto {
    List<Offer> offers;

    @Value(staticConstructor = "of")
    public static class Offer {
        String id;
        String name;
        String price;
    }
}
