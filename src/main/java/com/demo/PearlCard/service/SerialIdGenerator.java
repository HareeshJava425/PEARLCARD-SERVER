package com.demo.PearlCard.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class SerialIdGenerator {

    private final AtomicInteger counter;
    
    public SerialIdGenerator() {
        this.counter = new AtomicInteger(0);
    }
    
    /**
     * Generate next serial number: 1, 2, 3, 4, etc.
     */
    public Integer getNextId() {
        return counter.incrementAndGet();
    }
    
    /**
     * Get current counter value
     */
    public Integer getCurrentId() {
        return counter.get();
    }
    
    /**
     * Reset counter to 0 (next ID will be 1)
     */
    public void resetCounter() {
        counter.set(0);
    }
    
    /**
     * Set counter to specific value
     */
    public void setCounter(int value) {
        counter.set(value);
    }

}
