package com.thecodinglab.imdbclone.utility;

import java.util.Date;

// config for ms!
public class StopWatch {
  private Date start;
  private long splitTime;

  public StopWatch() {}

  public void start() {
    this.start = new Date();
  }

  public long stop() {
    this.splitTime = (new Date()).getTime() - this.start.getTime();
    return this.splitTime;
  }

  public StopWatch reset() {
    this.start = null;
    this.splitTime = 0L;
    return this;
  }
}

//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        Thread.sleep(1234);
//        System.out.println("findAccountsForDeletion took " + stopWatch.stop());
