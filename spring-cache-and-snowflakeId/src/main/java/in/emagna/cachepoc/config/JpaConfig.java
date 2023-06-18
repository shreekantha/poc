package in.emagna.cachepoc.config;

import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class JpaConfig {

    @Value("${snowflake.datacenterId}")
    private long datacenterId;

    @Value("${snowflake.workerId}")
    private long workerId;


    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator() {
        return new SnowflakeIdGenerator(datacenterId,workerId);
    }
}
