package com.arkadiusz.michalak.olxscraper.olxfetcher.converter;

import com.arkadiusz.michalak.olxscraper.olxfetcher.exception.ConversionException;
import io.netty.buffer.ByteBufInputStream;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;
import java.io.InputStream;

public class BufferToStringConverter implements Converter<Buffer, String> {

    @Override
    public String convert(Buffer input) {
        try {
            InputStream inputStream = new ByteBufInputStream(input.getByteBuf());
            byte[] bytes = inputStream.readAllBytes();
            return new String(bytes);
        } catch (IOException e) {
            throw new ConversionException(e);
        }
    }
}
