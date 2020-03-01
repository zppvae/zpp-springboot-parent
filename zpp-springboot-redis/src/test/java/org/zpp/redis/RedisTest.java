package org.zpp.redis;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.zpp.springboot.redis.RedisApplication;
import org.zpp.springboot.redis.model.User;
import org.zpp.springboot.redis.repository.UserRepository;
import org.zpp.springboot.redis.service.UserService;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisTest {

    private static final Logger log = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Serializable> redisCacheTemplate;

    @Rule
    public ContiPerfRule i = new ContiPerfRule();

    @Autowired
    private UserRepository userRepository;

    /**
     * 用户业务逻辑注入
     */
    @Autowired
    private UserService userService;

    @Test
    public void get() {

        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        IntStream.range(0, 1000).forEach(i ->
                executorService.execute(() -> stringRedisTemplate.opsForValue().increment("kk", 1))
        );
        stringRedisTemplate.opsForValue().set("k1", "v1");
        final String k1 = stringRedisTemplate.opsForValue().get("k1");
        log.info("[字符缓存结果] - [{}]", k1);

        String key = "redis:user:1";
        redisCacheTemplate.opsForValue().set(key, new User(1, "u1", "pa",10));

        final User user = (User) redisCacheTemplate.opsForValue().get(key);
        log.info("[对象缓存结果] - [{}]", user);
    }

    /**
     * 性能测试
     * 10万次查询，100个线程同时操作findAll方法
     */
    @Test
    @PerfTest(invocations = 100000, threads = 100)
    public void contextLoads() {
        userService.findAll();
    }

    /**
     * 测试全部缓存
     */
    @Test
    public void findAll() {
        userService.findAll();
    }

    @Test
    public void test() throws Exception {
        userRepository.save(new User(1,"222", "123456",10));

        User u1 = userRepository.findByUserName("222");
        System.out.println("第一次查询：" + u1.getUserAge());

        User u2 = userRepository.findByUserName("222");
        System.out.println("第二次查询：" + u2.getUserAge());

        u1.setUserAge(20);
        userRepository.save(u1);
        User u3 = userRepository.findByUserName("222");
        System.out.println("第三次查询：" + u3.getUserAge());
    }
}