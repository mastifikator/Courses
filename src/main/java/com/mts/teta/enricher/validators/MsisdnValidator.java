package com.mts.teta.enricher.validators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.teta.enricher.database.UserDatabase;
import com.mts.teta.enricher.models.MessageContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsisdnValidator implements MessageValidator {

    private final UserDatabase database;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger LOG = LoggerFactory.getLogger(MsisdnValidator.class);

    public MsisdnValidator(UserDatabase database) {
        this.database = database;
    }

    @Override
    public MessageContent validate(String message) {

        MessageContent messageContent;

        try {
            messageContent = objectMapper.readValue(message, MessageContent.class);
            LOG.debug("success validating " + messageContent);
        } catch (JsonProcessingException | IllegalArgumentException j) {
            LOG.debug("validating error " + j.getMessage());
            return null;
        }

        if (database.checkUser(messageContent.getMsisdn())) {
            LOG.debug("user for " + messageContent.getMsisdn() + " found");
            return messageContent;
        } else {
            LOG.debug("user for " + messageContent.getMsisdn() + " not found");
            return null;
        }

    }
}
