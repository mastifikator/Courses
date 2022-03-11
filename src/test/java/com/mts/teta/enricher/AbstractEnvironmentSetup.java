package com.mts.teta.enricher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.teta.enricher.database.InMemoryUserDatabase;
import com.mts.teta.enricher.database.UserDatabase;
import com.mts.teta.enricher.models.User;
import com.mts.teta.enricher.validators.MessageValidator;
import com.mts.teta.enricher.validators.MsisdnValidator;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractEnvironmentSetup {
    private static final int USER_AMOUNT = 100;

    protected UserDatabase db;
    protected ObjectMapper objectMapper;
    protected MessageValidator validator;
    protected CopyOnWriteArrayList<String> successfullyEnrichedMessages;
    protected CopyOnWriteArrayList<String> unsuccessfullyEnrichedMessages;
    protected EnrichmentServiceImpl enrichmentServiceImpl;
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
        successfullyEnrichedMessages = new CopyOnWriteArrayList<>();
        unsuccessfullyEnrichedMessages = new CopyOnWriteArrayList<>();

        enrichmentServiceImpl = new EnrichmentServiceImpl(validator,
                db,
                successfullyEnrichedMessages,
                unsuccessfullyEnrichedMessages);
    }

}
