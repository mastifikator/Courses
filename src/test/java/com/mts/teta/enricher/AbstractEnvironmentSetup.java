package com.mts.teta.enricher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.teta.enricher.database.InMemoryUserDatabase;
import com.mts.teta.enricher.database.UserDatabase;
import com.mts.teta.enricher.models.User;
import com.mts.teta.enricher.validators.MessageValidator;
import com.mts.teta.enricher.validators.MsisdnValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractEnvironmentSetup {
    private static final int USER_AMOUNT = 100;

    protected UserDatabase db;
    protected ObjectMapper objectMapper;
    protected MessageValidator validator;
    protected BlockingQueue<String> successfullyEnrichedMessages;
    protected BlockingQueue<String> unsuccessfullyEnrichedMessages;
    protected EnrichmentService enrichmentService;
    protected Logger LOG;

    @BeforeEach
    public void prepareDatabase() {
        db = new InMemoryUserDatabase();
        LOG = LoggerFactory.getLogger(AbstractEnvironmentSetup.class);

        for (int i = 0; i < USER_AMOUNT; i++) {
            String amount = Integer.toString(i);
            db.addUser(amount, new User(amount, amount + amount + amount));
            LOG.debug("added user: " + db.getUser(amount));
        }

        objectMapper = new ObjectMapper();
        validator = new MsisdnValidator(db);
        successfullyEnrichedMessages = new LinkedBlockingQueue<>();
        unsuccessfullyEnrichedMessages = new LinkedBlockingQueue<>();

        enrichmentService = new EnrichmentService(validator,
                db,
                successfullyEnrichedMessages,
                unsuccessfullyEnrichedMessages);
    }

    @AfterEach
    public void clearQueue() throws InterruptedException {

        System.out.println("Successfully Message: ");
        while (!successfullyEnrichedMessages.isEmpty()) {
            System.out.println(successfullyEnrichedMessages.take());
        }

        System.out.println("Unsuccessfully Message: ");
        while (!unsuccessfullyEnrichedMessages.isEmpty()) {
            System.out.println(unsuccessfullyEnrichedMessages.take());
        }
    }

}
