package com.prizz.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * [MULTITHREADING] Asynchronous Audit & Logging Service.
 * Purpose: Decouples time-consuming audit operations from the main console thread.
 * Syllabus: Concurrency with ExecutorService (Thread pool management and clean shutdown).
 */
public class AsyncAuditService {
    private final ExecutorService executorService;

    public AsyncAuditService(int threadPoolSize) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void submitAuditTask(String eventName, String details) {
        executorService.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.printf("[AUDIT-ASYNC] [%s] Event: '%s' | Payload: %s\n", 
                    threadName, eventName, details);
        });
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

