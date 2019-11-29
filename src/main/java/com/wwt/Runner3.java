package com.wwt;

import redis.clients.jedis.Jedis;

/**
 * @author wwt
 * @ClassName Runner.java
 * @Description incr 实现redis并发锁
 *              incrnx (set if not exist)
 * @createTime 2019-11-28 18:22
 */
public class Runner3 implements Runnable {

    /**
     * 这种加锁的思路是， key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作进行加一。
     * 然后其它用户在执行 INCR 操作进行加一时，如果返回的数大于 1 ，说明这个锁正在被使用当中。
     *
     *
     *     1、 客户端A请求服务器获取key的值为1表示获取了锁
     *     2、 客户端B也去请求服务器获取key的值为2表示获取锁失败sdf
     *     3、 客户端A执行代码完成，删除锁
     *     4、 客户端B在等待一段时间后在去请求的时候获取key的值为1表示获取锁成功
     *     5、 客户端B执行代码完成，删除锁
     *
     *     $redis->incr($key);
     *     $redis->expire($key, $ttl); //设置生成时间为1秒
    */
    @Override
    public void run() {
        Jedis jedis = new Jedis("localhost", 6379);
        while (jedis.incr("aa") > 1){
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
