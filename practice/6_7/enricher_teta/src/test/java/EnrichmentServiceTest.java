import com.fasterxml.jackson.core.JsonProcessingException;
import enums.EnrichmentType;
import models.EnrichmentName;
import models.MessageContent;
import models.MessageDto;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

public class EnrichmentServiceTest extends AbstractEnvironmentSetup {

    @Test
    public void testEnrich() throws InterruptedException {
        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);

            for (int i = 0; i < numberOfThreads; i++) {
                final String msisdn = Integer.toString(i);
                service.submit(() -> {
                    try {

                        MessageContent messageContent = new MessageContent("action", "bookCard", msisdn);
                        MessageDto messageDto = new MessageDto(objectMapper.writeValueAsString(messageContent), EnrichmentType.MSISDN);

                        String enrichedMessage = enrichmentService.enrich(messageDto);

                        MessageContent resultMessage = objectMapper.readValue(enrichedMessage, MessageContent.class);
                        EnrichmentName resultEnrichment = new EnrichmentName(msisdn, msisdn + msisdn + msisdn);

                        assertEquals(resultMessage.getMsisdn(), messageContent.getMsisdn());
                        assertEquals(resultMessage.getAction(), messageContent.getAction());
                        assertEquals(resultMessage.getEnrichment(), resultEnrichment);

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (JsonProcessingException j){
                        LOG.debug("serialize error" + j.getMessage());
                    }
                    latch.countDown();
                });
            }

        latch.await();

        System.out.println("Successfully Message: ");
            while(!successfullyEnrichedMessage.isEmpty()){
                System.out.println(successfullyEnrichedMessage.take());
            }

        System.out.println("Unsuccessfully Message: ");
            while(!unsuccessfullyEnrichedMessage.isEmpty()){
                System.out.println(unsuccessfullyEnrichedMessage.take());
            }

    }
}