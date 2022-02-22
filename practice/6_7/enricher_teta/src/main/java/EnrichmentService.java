import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.UserDatabase;
import models.EnrichmentName;
import models.MessageContent;
import models.MessageDto;
import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import validators.MessageValidator;

import java.util.concurrent.BlockingQueue;

public class EnrichmentService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger LOG = LoggerFactory.getLogger(EnrichmentService.class);

    private final MessageValidator validator;
    private final UserDatabase database;
    private final BlockingQueue<String> successfullyEnrichedMessage;
    private final BlockingQueue<String> unsuccessfullyEnrichedMessage;

    public EnrichmentService(MessageValidator validator,
                             UserDatabase database,
                             BlockingQueue<String> successfullyEnrichedMessage,
                             BlockingQueue<String> unsuccessfullyEnrichedMessage){
        this.validator = validator;
        this.database = database;
        this.successfullyEnrichedMessage = successfullyEnrichedMessage;
        this.unsuccessfullyEnrichedMessage = unsuccessfullyEnrichedMessage;
    }

    public String enrich(MessageDto messageDto) throws InterruptedException{

        if(!validator.validate(messageDto.getContent())){
            unsuccessfullyEnrichedMessage.put(messageDto.getContent());
            return messageDto.getContent();
        }

        MessageContent messageContent;

        try {
            messageContent = objectMapper.readValue(messageDto.getContent(), MessageContent.class);
        }catch (JsonProcessingException j){
            LOG.debug("deserialization error" + j.getMessage());
            unsuccessfullyEnrichedMessage.put(messageDto.getContent());
            return messageDto.getContent();
        }

        User actualUser = database.getUser(messageContent.getMsisdn());
        messageContent.setEnrichment(new EnrichmentName(actualUser.getFirstName(), actualUser.getLastName()));

        try {
            successfullyEnrichedMessage.put(objectMapper.writeValueAsString(messageContent));
        }catch (JsonProcessingException j){
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