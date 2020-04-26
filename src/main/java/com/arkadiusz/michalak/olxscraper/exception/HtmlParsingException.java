package com.arkadiusz.michalak.olxscraper.exception;

public class HtmlParsingException extends RuntimeException {
    private static final String MESSAGE = "An error has occurred when trying to parse result HTML";

    public HtmlParsingException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
