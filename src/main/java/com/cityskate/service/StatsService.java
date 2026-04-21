package com.cityskate.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatsService {

    private final Map<String, Integer> methodCalls = new HashMap<>();

    public void increment(String methodName) {
        methodCalls.put(methodName, methodCalls.getOrDefault(methodName, 0) + 1);
    }

    public Map<String, Integer> getStats() {
        return methodCalls;
    }
}