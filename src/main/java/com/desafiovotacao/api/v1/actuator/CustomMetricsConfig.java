package com.desafiovotacao.api.v1.actuator;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomMetricsConfig
{
    @Bean
    public CountedAspect countedAspect( MeterRegistry meterRegistry ) {
        return new CountedAspect( meterRegistry );
    }
    
    @Bean
    public TimedAspect timedAspect( MeterRegistry meterRegistry ) {
        return new TimedAspect( meterRegistry );
    }
}
