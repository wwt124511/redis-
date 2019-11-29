package com.wwt;

import redis.clients.jedis.Jedis;

/**
 * @author wwt
 * @ClassName Runner.java
 * @Description exist 实现redis并发锁-失败
 * @createTime 2019-11-28 18:22
 */
public class Runner implements Runnable {

    private Jedis jedis;

    public Runner() {
    }

    public Runner(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public void run() {
        Jedis jedis = new Jedis("localhost", 6379);
        while (jedis.exists("aa")){
            //已存在，标识正在锁
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        jedis.set("aa", "1");
        for (int i=0; i<100; i++) {
            int bb = Integer.parseInt(jedis.get("bb") == null ? "0" : jedis.get("bb"));
            jedis.set("bb", bb+1+"");
            System.out.println("======线程：" + Thread.currentThread().getName() + "======="+jedis.get("bb")+"=================");
        }
        jedis.del("aa");

    }
}
