package com.mts.teta.enricher.validators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.teta.enricher.database.InMemoryUserDatabase;
import com.mts.teta.enricher.database.UserDatabase;
import com.mts.teta.enricher.models.EnrichmentName;
import com.mts.teta.enricher.models.MessageContent;
import com.mts.teta.enricher.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class MsisdnValidatorTest {
    private static final int USER_AMOUNT = 5;

    protected UserDatabase db;
    protected ObjectMapper objectMapper;
    protected Logger LOG;

    protected MessageValidator validator;

    @BeforeEach
    public void prepareEnvironment() {
        db = new InMemoryUserDatabase();
        objectMapper = new ObjectMapper();
        LOG = LoggerFactory.getLogger(MsisdnValidatorTest.class);

        validator = new MsisdnValidator(db);

        for (int i = 0; i < USER_AMOUNT; i++) {
            String amount = Integer.toString(i);
            db.addUser(amount, new User(amount, amount + amount + amount));
            LOG.debug("added user: " + db.getUser(amount));
        }
    }

    @Test
    public void successfullyValidateTest() throws JsonProcessingException {
        MessageContent messageContent = new MessageContent("action", "bookCard", "1", new EnrichmentName("", ""));

        assertEquals(messageContent, validator.validate(objectMapper.writeValueAsString(messageContent)));
    }

    @Test
    public void jsonUnsuccessfullyValidateTest() {
        String wrongJson = "{'action':'action', 'page':'bookCard'}";

        assertNull(validator.validate(wrongJson));
    }

    @Test
    public void databaseUnsuccessfullyValidateTest() throws JsonProcessingException {
        MessageContent messageContent = new MessageContent("action", "bookCard", Integer.toString(USER_AMOUNT), new EnrichmentName("", ""));

        assertNull(validator.validate(objectMapper.writeValueAsString(messageContent)));
    }

}