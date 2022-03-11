package com.mts.teta.courses.config;

import com.mts.teta.courses.service.StatisticsCounter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StaticsConfig {
    public StatisticsCounter statisticsCounter() {
        return new StatisticsCounter();
    }
}
