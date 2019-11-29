package com.wwt;

import redis.clients.jedis.Jedis;

/**
 * @author wwt
 * @ClassName Runner.java
 * @Description setnx 实现redis并发锁
 *              setnx (set if not exist)
 * @createTime 2019-11-28 18:22
 */
public class Runner2 implements Runnable {

    @Override
    public void run() {
        Jedis jedis = new Jedis("localhost", 6379);
        while (0 == jedis.setnx("aa", "1")){    //0代表新增失败，库中已存在  1代表新增成功
            //已存在，标识正在锁
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i=0; i<100; i++) {
            int bb = Integer.parseInt(jedis.get("bb") == null ? "0" : jedis.get("bb"));
            jedis.set("bb", bb+1+"");
            System.out.println("======线程：" + Thread.currentThread().getName() + "======="+jedis.get("bb")+"=================");
        }
        jedis.del("aa");

    }
}
