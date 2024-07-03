package com.example.openschoolaop.service;

import com.example.openschoolaop.annotation.TrackAsyncTime;
import com.example.openschoolaop.annotation.TrackTime;
import com.example.openschoolaop.exception.TestMethodInterruptedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class TestService {

    @TrackTime
    public Long methodRandomExecutionTime() {
        Random random = new Random();
        long randomLong = random.nextLong(1000, 5000);
        try {
            Thread.sleep(randomLong);
        } catch (InterruptedException e) {
            throw new TestMethodInterruptedException("methodRandomExecutionTime was interrupted");
        }
        return randomLong;
    }

    @TrackAsyncTime
    public Long methodRandomExecutionTimeAsync() {
        long randomLong = ThreadLocalRandom.current().nextLong(1000, 5000);
        try {
            Thread.sleep(randomLong);
        } catch (InterruptedException e) {
            throw new TestMethodInterruptedException("methodRandomExecutionTimeAsync was interrupted");
        }
        return randomLong;
    }
}
