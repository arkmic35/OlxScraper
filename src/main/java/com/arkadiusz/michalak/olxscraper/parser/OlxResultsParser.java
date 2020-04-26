package com.arkadiusz.michalak.olxscraper.parser;

import com.arkadiusz.michalak.olxscraper.model.Offer;
import io.netty.buffer.ByteBufInputStream;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class OlxResultsParser {

    public List<Offer> parse(Buffer buffer) {
        throw new UnsupportedOperationException();
    }

    private String convertToPayloadString(Buffer buffer) throws IOException {
        InputStream inputStream = new ByteBufInputStream(buffer.getByteBuf());
        byte[] bytes = inputStream.readAllBytes();

        return new String(bytes);
    }
}
