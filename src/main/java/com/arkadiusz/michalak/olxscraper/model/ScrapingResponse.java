package com.arkadiusz.michalak.olxscraper.model;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class ScrapingResponse {
    List<ResponseSegment> offers;

    @Value(staticConstructor = "of")
    public static class ResponseSegment {
        String id;
        String name;
        String price;
    }
}
