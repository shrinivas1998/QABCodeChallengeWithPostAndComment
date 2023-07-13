package com.docManageSystem.QDBChallnge.service;

import feign.RetryableException;
import feign.Retryer;

public class MyRetryer implements Retryer {

        private final long period;
        private final int maxAttempts;
        private int attempt = 1;

        public MyRetryer(long period, int maxAttempts) {
            this.period = period;
            this.maxAttempts = maxAttempts;
        }

        @Override
        public void continueOrPropagate(RetryableException e) {
            if (++attempt > maxAttempts) {
                throw e;
            }
            if (e.status() == 500) {
                // remove Authorization first, otherwise Feign will add a new Authorization header
                // cause github responses a 400 bad request
                e.request();
                e.request();
                try {
                    Thread.sleep(period);
                } catch (InterruptedException ex) {
                    throw e;
                }
            } else {
                throw e;
            }
        }

        // Access an external api to obtain new token
        // In this example, we can simply return a fixed token to demonstrate how Retryer works
        private String getNewToken() {
            return "newToken";
        }

        @Override
        public Retryer clone() {
            return new MyRetryer(period, maxAttempts);
        }
}