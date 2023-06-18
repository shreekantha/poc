package in.emagna.cachepoc.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.io.Serializable;
@Slf4j
public class SnowflakeIdGenerator implements IdentifierGenerator {
    private final SnowflakeIdWorker idWorker;

    public SnowflakeIdGenerator(long datacenterId, long workerId) {
        this.idWorker = new SnowflakeIdWorker(datacenterId, workerId);
    }
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return idWorker.nextId();
    }
}