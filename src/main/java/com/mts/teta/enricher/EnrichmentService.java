package com.mts.teta.enricher;

import com.mts.teta.enricher.models.MessageDto;

public interface EnrichmentService {

    String enrich(MessageDto messageDto) throws InterruptedException;
}
