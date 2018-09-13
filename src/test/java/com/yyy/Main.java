package com.yyy;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class Main {

    String host = "localhost";
    String key = "QUEEN";
    Jedis jedis = null;

    @Before
    public void prepare() {
        jedis = new Jedis(host);
    }

    @Test
    public void test() {
        jedis.set("hello", "hello");
        System.out.println(jedis.get("hello"));
    }

    @Test
    public void test0() throws InterruptedException {
        System.out.println("...test0...");
        String uuid = UUID.randomUUID().toString();
        RedisTool.tryGetDistributedLock(jedis, key, uuid, 7 * 1000);
        System.out.println("...do something...");
        Thread.sleep(10 * 1000);
        System.out.println("...lock expire...");
        Thread.sleep(10 * 1000);
        RedisTool.releaseDistributedLock(jedis, key, uuid);
    }

    @Test
    public void test1() throws InterruptedException {
        System.out.println("...test1...");
        String uuid = UUID.randomUUID().toString();
        RedisTool.tryGetDistributedLock(jedis, key, uuid, 30 * 1000);
        System.out.println("...do something...");
        Thread.sleep(15 * 1000);
        RedisTool.releaseDistributedLock(jedis, key, uuid);
    }
}
