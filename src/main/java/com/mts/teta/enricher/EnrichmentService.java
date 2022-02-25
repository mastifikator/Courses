package com.mts.teta.enricher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.teta.enricher.database.UserDatabase;
import com.mts.teta.enricher.models.EnrichmentName;
import com.mts.teta.enricher.models.MessageContent;
import com.mts.teta.enricher.models.MessageDto;
import com.mts.teta.enricher.models.User;
import com.mts.teta.enricher.validators.MessageValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class EnrichmentService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger LOG = LoggerFactory.getLogger(EnrichmentService.class);

    private final MessageValidator validator;
    private final UserDatabase database;
    private final BlockingQueue<String> successfullyEnrichedMessages;
    private final BlockingQueue<String> unsuccessfullyEnrichedMessages;

    public EnrichmentService(MessageValidator validator,
                             UserDatabase database,
                             BlockingQueue<String> successfullyEnrichedMessages,
                             BlockingQueue<String> unsuccessfullyEnrichedMessages) {
        this.validator = validator;
        this.database = database;
        this.successfullyEnrichedMessages = successfullyEnrichedMessages;
        this.unsuccessfullyEnrichedMessages = unsuccessfullyEnrichedMessages;
    }

    public String enrich(MessageDto messageDto) throws InterruptedException {

        if (!validator.validate(messageDto.getContent())) {
            unsuccessfullyEnrichedMessages.put(messageDto.getContent());
            return messageDto.getContent();
        }

        MessageContent messageContent;

        try {
            messageContent = objectMapper.readValue(messageDto.getContent(), MessageContent.class);
        } catch (JsonProcessingException j) {
            LOG.debug("deserialization error" + j.getMessage());
            unsuccessfullyEnrichedMessages.put(messageDto.getContent());
            return messageDto.getContent();
        }

        User actualUser = database.getUser(messageContent.getMsisdn());
        messageContent.setEnrichment(new EnrichmentName(actualUser.getFirstName(), actualUser.getLastName()));

        try {
            successfullyEnrichedMessages.put(objectMapper.writeValueAsString(messageContent));
        } catch (JsonProcessingException j) {
            LOG.debug("serialization error" + j.getMessage());
        }

        try {
            return objectMapper.writeValueAsString(messageContent);
        } catch (JsonProcessingException j) {
            LOG.debug("serialization error" + j.getMessage());
            return messageDto.getContent();
        }
    }
}