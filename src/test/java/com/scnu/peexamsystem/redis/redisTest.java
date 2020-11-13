package com.scnu.peexamsystem.redis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class redisTest {
    @Test
    public void redisTester() {
        Jedis jedis = new Jedis("localhost", 6379, 100000);
        int i = 0;
        try {
            long start = System.currentTimeMillis();// 开始毫秒数
            while (true) {
                long end = System.currentTimeMillis();
                if (end - start >= 1000) {// 当大于等于1000毫秒（相当于1秒）时，结束操作
                    break;
                }
                i++;
                jedis.set("test" + i, i + "");
            }
        } finally {// 关闭连接
            jedis.close();
        }
        // 打印1秒内对Redis的操作次数
        System.out.println("redis每秒操作：" + i + "次");
    }
}
