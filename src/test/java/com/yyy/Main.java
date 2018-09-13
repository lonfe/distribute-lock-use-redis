package com.yyy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import redis.clients.jedis.Jedis;

import java.util.*;

@RunWith(Parameterized.class)
public class Main {

    private String host;
    private String key;
    Jedis jedis;

    public Main(String host, String key, Jedis jedis) {
        this.host = host;
        this.key = key;
        this.jedis = jedis;
    }

    @Before
    public void prepare() {
        System.out.println("...prepare...");
        jedis = new Jedis(host);
    }

    @Parameters
    public static Collection<Object[]> para() {
        System.out.println("...parameter...");
        return Arrays.asList(new Object[][] {{"localhost", "QUEEN", null}});
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
