package in.emagna.cachepoc.config;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
public class SnowflakeIdWorker {

    private static final long EPOCH = 1288834974657L; // Custom epoch (e.g., 1st June 2021)
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long sequenceBits = 12L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long workerId;
    private final long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;
    public SnowflakeIdWorker(long workerId, long datacenterId) {
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException("Worker ID is out of range");
        }
        if (datacenterId < 0 || datacenterId > maxDatacenterId) {
            throw new IllegalArgumentException("Datacenter ID is out of range");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        log.info("datacenterId-------------------------:{}",datacenterId);
        long timestamp = 1288834974657L;
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        log.info("Date and time :{}",dateTime);
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Invalid system clock. Clock moved backwards.");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & ((1 << sequenceBits) - 1);
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

     return ((timestamp - EPOCH) << (workerIdBits + datacenterIdBits + sequenceBits))
                | (datacenterId << (workerIdBits + sequenceBits))
                | (workerId << sequenceBits)
                | sequence;

    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
