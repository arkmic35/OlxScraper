package com.arkadiusz.michalak.olxscraper.olxfetcher.model;

import com.arkadiusz.michalak.olxscraper.olxfetcher.exception.ConversionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OffersDtoCodec implements MessageCodec<OffersDto, OffersDto> {
    private final ObjectMapper objectMapper;

    @Override
    public void encodeToWire(Buffer buffer, OffersDto offersDto) {
        try {
            buffer.appendString(objectMapper.writeValueAsString(offersDto));
        } catch (JsonProcessingException e) {
            throw new ConversionException(e);
        }
    }

    @Override
    public OffersDto decodeFromWire(int pos, Buffer buffer) {
        try {
            String json = buffer.getString(pos, buffer.length() - 1);
            return objectMapper.readValue(json, OffersDto.class);
        } catch (JsonProcessingException e) {
            throw new ConversionException(e);
        }
    }

    @Override
    public OffersDto transform(OffersDto offersDto) {
        return offersDto;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
