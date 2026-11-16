package collections;

import models.ServiceRequest;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * [COLLECTIONS] Double-Ended Dispatcher Queue.
 * Purpose: Manages standard FIFO request scheduling and LIFO emergency preemption.
 * Syllabus: Collections Framework (Queue / Deque using ArrayDeque).
 */
public class RequestDispatcherQueue {
    private final Deque<ServiceRequest> dispatchQueue;

    public RequestDispatcherQueue() {
        this.dispatchQueue = new ArrayDeque<>();
    }

    public synchronized void enqueueStandardRequest(ServiceRequest request) {
        dispatchQueue.addLast(request);
    }

    public synchronized void enqueueEmergencyRequest(ServiceRequest request) {
        dispatchQueue.addFirst(request);
    }

    public synchronized ServiceRequest dispatchNext() {
        return dispatchQueue.pollFirst();
    }

    public synchronized int pendingCount() {
        return dispatchQueue.size();
    }

    public synchronized boolean hasPending() {
        return !dispatchQueue.isEmpty();
    }
}

