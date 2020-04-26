package com.arkadiusz.michalak.olxscraper.converter;

public interface Converter<F, T> {
    T convert(F input);
}
