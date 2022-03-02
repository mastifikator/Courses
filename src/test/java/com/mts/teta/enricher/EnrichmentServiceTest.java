package com.mts.teta.enricher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mts.teta.enricher.enums.EnrichmentType;
import com.mts.teta.enricher.models.EnrichmentName;
import com.mts.teta.enricher.models.MessageContent;
import com.mts.teta.enricher.models.MessageDto;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnrichmentServiceTest extends AbstractEnvironmentSetup {

    private static final int THREAD_COUNT = 99;

    @Test
    public void testSuccessfullyEnrich() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            String msisdn = Integer.toString(i);
            service.submit(() -> {
                try {
                    MessageContent messageContent = new MessageContent("action", "bookCard", msisdn, new EnrichmentName("", ""));
                    MessageDto messageDto = new MessageDto(objectMapper.writeValueAsString(messageContent), EnrichmentType.MSISDN);

                    String enrichedMessage = enrichmentService.enrich(messageDto);

                    MessageContent resultMessage = objectMapper.readValue(enrichedMessage, MessageContent.class);
                    EnrichmentName resultEnrichment = new EnrichmentName(msisdn, msisdn + msisdn + msisdn);

                    assertEquals(resultMessage.getMsisdn(), messageContent.getMsisdn());
                    assertEquals(resultMessage.getAction(), messageContent.getAction());
                    assertEquals(resultMessage.getEnrichment(), resultEnrichment);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (JsonProcessingException j) {
                    LOG.debug("serialize error" + j.getMessage());
                }
                latch.countDown();
            });
        }

        latch.await();

        assertEquals(successfullyEnrichedMessages.size(), THREAD_COUNT);
    }

    @Test
    public void testUnsuccessfullyEnrich() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            String msisdn = i + "error";
            service.submit(() -> {
                try {
                    MessageContent messageContent = new MessageContent("anotherAction", "anotherBookCard", msisdn, new EnrichmentName("", ""));
                    MessageDto messageDto = new MessageDto(objectMapper.writeValueAsString(messageContent), EnrichmentType.MSISDN);

                    String enrichedMessage = enrichmentService.enrich(messageDto);

                    MessageContent resultMessage = objectMapper.readValue(enrichedMessage, MessageContent.class);
                    EnrichmentName resultEnrichment = new EnrichmentName("", "");

                    assertEquals(resultMessage.getMsisdn(), messageContent.getMsisdn());
                    assertEquals(resultMessage.getAction(), messageContent.getAction());
                    assertEquals(resultMessage.getEnrichment(), resultEnrichment);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (JsonProcessingException j) {
                    LOG.debug("serialize error" + j.getMessage());
                }
                latch.countDown();
            });
        }

        latch.await();

        assertEquals(unsuccessfullyEnrichedMessages.size(), THREAD_COUNT);
    }


}