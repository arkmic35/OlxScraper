package com.arkadiusz.michalak.olxscraper.olxfetcher.converter;

public interface Converter<F, T> {
    T convert(F input);
}
