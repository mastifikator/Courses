package com.mts.teta.enricher.models;

import java.util.Objects;

public class MessageContent {
    private String action;
    private String page;
    private String msisdn;
    private EnrichmentName enrichment;

    public MessageContent() {
    }

    public MessageContent(String action, String book_card, String msisdn, EnrichmentName enrichment) {
        this.action = action;
        this.page = book_card;
        this.msisdn = msisdn;
        this.enrichment = enrichment;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public EnrichmentName getEnrichment() {
        return enrichment;
    }

    public void setEnrichment(EnrichmentName enrichment) {
        this.enrichment = enrichment;
    }

    @Override
    public String toString() {
        return "MessageContent{" +
                "action='" + action + '\'' +
                ", page='" + page + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", enrichment=" + enrichment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageContent that = (MessageContent) o;
        return Objects.equals(action, that.action) && Objects.equals(page, that.page) && Objects.equals(msisdn, that.msisdn) && Objects.equals(enrichment, that.enrichment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, page, msisdn, enrichment);
    }
}
