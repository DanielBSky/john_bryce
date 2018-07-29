package com.brodsky.bussinessLogic.job;

public class TestDailyJob {
    public static void main(String[] args) throws InterruptedException {
        CouponExpirationDailyJob job = new CouponExpirationDailyJob();

        Thread myJob = new Thread(job);
        myJob.start();

        Thread.sleep(7000);

        job.stop();


    }
}
