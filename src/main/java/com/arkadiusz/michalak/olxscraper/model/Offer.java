package com.arkadiusz.michalak.olxscraper.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class Offer {
    String id;
    String name;
    String price;
}
