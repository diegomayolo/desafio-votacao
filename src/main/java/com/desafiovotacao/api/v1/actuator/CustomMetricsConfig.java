package com.desafiovotacao.api.v1.actuator;

import com.desafiovotacao.api.v1.repositories.VoteRepository;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomMetricsConfig
{
    @Bean
    public MeterBinder customMetrics( VoteRepository repository ) 
    {
        return (meterRegistry) ->  Gauge.builder( "votes.qtd", repository::count )
                                        .register( meterRegistry );
    }
    
    @Bean
    public CountedAspect countedAspect( MeterRegistry meterRegistry )
    {
        return new CountedAspect( meterRegistry );
    }
    
    @Bean
    public TimedAspect timedAspect( MeterRegistry meterRegistry )
    {
        return new TimedAspect( meterRegistry );
    }
}
