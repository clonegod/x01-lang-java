package clonegod.framework.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import clonegod.framework.web.redis.RedisConstants;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MonitorHelper {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void count(final String uri) {
        final long seconds = System.currentTimeMillis() / 1000;
        
        for (Integer prec : RedisConstants.PRECISION) {
        	long startSlice = seconds / prec * prec;
        	String hash = String.format("%s:%s", prec, uri);
        	redisTemplate.opsForZSet().add("known:", hash, 0);
        	redisTemplate.opsForHash().increment(("count:" + hash), String.valueOf(startSlice), 1l);
        }
        log.info("监控记数执行完成for {}", uri);
        
        /**java.lang.UnsupportedOperationException: Pipeline is currently not supported for JedisClusterConnection.*/
//        redisTemplate.executePipelined(
//        		new RedisCallback<Object>() {
//                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                        for (Integer prec : RedisConstants.PRECISION) {
//                            long startSlice = seconds / prec * prec;
//                            String hash = String.format("%s:%s", prec, uri);
//                            connection.zAdd("known:".getBytes(), 0, hash.getBytes());
//                            connection.hIncrBy(("count:" + hash).getBytes(), String.valueOf(startSlice).getBytes(), 1l);
//                        }
//                        log.info("监控记数执行完成for {}", uri);
//                        return null;
//            }
//        });
    }
    
}
