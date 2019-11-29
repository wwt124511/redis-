package com.wwt;

import redis.clients.jedis.Jedis;

/**
 * @author wwt
 * @ClassName RedisTest.java
 * @Description TODO
 * @createTime 2019-11-28 18:13
 */
public class RedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);

        //test(jedis);
        //test02();
        test03();
        //System.out.println(jedis.get("bb"));

        //jedis.close();
    }

    public static void test01(Jedis jedis){
        //设置 key value
        jedis.set("aa","1");
    }

    public static void test(){
        new Thread(new Runner()).start();
        new Thread(new Runner()).start();
    }

    public static void test02(){
        new Thread(new Runner2()).start();
        new Thread(new Runner2()).start();
        new Thread(new Runner2()).start();
        new Thread(new Runner2()).start();
        new Thread(new Runner2()).start();
    }

    public static void test03(){
        new Thread(new Runner3()).start();
        new Thread(new Runner3()).start();
        new Thread(new Runner3()).start();
        new Thread(new Runner3()).start();
        new Thread(new Runner3()).start();
    }

}
