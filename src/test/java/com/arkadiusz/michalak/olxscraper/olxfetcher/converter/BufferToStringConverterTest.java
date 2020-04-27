package com.arkadiusz.michalak.olxscraper.olxfetcher.converter;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BufferToStringConverterTest {

    private final BufferToStringConverter converter = new BufferToStringConverter();

    @Test
    void shouldBeAbleToConvert() {
        //given
        String text = "Test text";
        Buffer input = new BufferImpl().appendString(text);

        //when
        String result = converter.convert(input);

        //then
        assertEquals(text, result);
    }
}