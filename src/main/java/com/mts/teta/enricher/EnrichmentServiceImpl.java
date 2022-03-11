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

import java.util.concurrent.CopyOnWriteArrayList;

public class EnrichmentServiceImpl implements EnrichmentService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger LOG = LoggerFactory.getLogger(EnrichmentServiceImpl.class);

    private final MessageValidator validator;
    private final UserDatabase database;
    private final CopyOnWriteArrayList<String> successfullyEnrichedMessages;
    private final CopyOnWriteArrayList<String> unsuccessfullyEnrichedMessages;

    public EnrichmentServiceImpl(MessageValidator validator,
                                 UserDatabase database,
                                 CopyOnWriteArrayList<String> successfullyEnrichedMessages,
                                 CopyOnWriteArrayList<String> unsuccessfullyEnrichedMessages) {
        this.validator = validator;
        this.database = database;
        this.successfullyEnrichedMessages = successfullyEnrichedMessages;
        this.unsuccessfullyEnrichedMessages = unsuccessfullyEnrichedMessages;
    }

    public String enrich(MessageDto messageDto) {
        MessageContent messageContent = validator.validate(messageDto.getContent());

        if (messageContent == null) {
            unsuccessfullyEnrichedMessages.add(messageDto.getContent());
            return messageDto.getContent();
        }

        User actualUser = database.getUser(messageContent.getMsisdn());
        messageContent.setEnrichment(new EnrichmentName(actualUser.getFirstName(), actualUser.getLastName()));

        String resultMessage;

        try {
            resultMessage = objectMapper.writeValueAsString(messageContent);
        } catch (JsonProcessingException j) {
            LOG.debug("serialization error" + j.getMessage());
            return messageDto.getContent();
        }

        successfullyEnrichedMessages.add(resultMessage);
        return resultMessage;
    }
}