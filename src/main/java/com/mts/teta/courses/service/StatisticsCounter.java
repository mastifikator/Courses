package com.mts.teta.courses.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StatisticsCounter {
    private static final Logger LOG = LoggerFactory.getLogger(StatisticsCounter.class);

    public void countHandlerCall(String comment) {
        LOG.info(comment);
    }

}
