package com.mts.teta.enricher.validators;

import com.mts.teta.enricher.models.MessageContent;

public interface MessageValidator {
    MessageContent validate(String messageContent);
}
