package com.arkadiusz.michalak.olxscraper.parser;

import com.arkadiusz.michalak.olxscraper.model.Offer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class OlxResultsParser {

    public List<Offer> parse(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("#offers_table > tbody > tr.wrap");
        List<Offer> offers = new ArrayList<>(elements.size());

        for (Element element : elements) {

            String id = element
                    .select("td > div > table")
                    .attr("data-id");

            String name = element
                    .select("td > div > table > tbody > tr:nth-child(1) > td.title-cell > div > h3 > a > strong")
                    .first()
                    .html();

            String price = element
                    .select("td > div > table > tbody > tr:nth-child(1) > td.wwnormal.tright.td-price > div > p > strong")
                    .first()
                    .html();

            offers.add(Offer.of(id, name, price));
        }

        return offers;
    }
}
