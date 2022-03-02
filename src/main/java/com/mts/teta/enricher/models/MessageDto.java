package com.mts.teta.enricher.models;


import com.mts.teta.enricher.enums.EnrichmentType;

public class MessageDto {
    private String content;
    private EnrichmentType enrichmentType;

    public MessageDto(String content, EnrichmentType enrichmentType) {
        this.content = content;
        this.enrichmentType = enrichmentType;
    }

    public String getContent() {
        return content;
    }

    public EnrichmentType getEnrichmentType() {
        return enrichmentType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEnrichmentType(EnrichmentType enrichmentType) {
        this.enrichmentType = enrichmentType;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "content='" + content + '\'' +
                ", enrichmentType=" + enrichmentType +
                '}';
    }
}
