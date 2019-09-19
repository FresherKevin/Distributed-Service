package cn.nh.kevin.demo.Util.Redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 标题: Redis使用工具类
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-09-04 14:32
 */
@Component
public class RedisUtil {
    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @param keys
     * @return void
     * @Description 批量删除
     * @Author xck
     * @Date 2019/9/5 13:24
     **/
    public void batchRemove(String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * @param key
     * @return void
     * @Description 删除key-value
     * @Author xck
     * @Date 2019/9/5 13:24
     **/
    public void remove(String key) {
        if (hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * @param key
     * @return boolean
     * @Description 判断key是否存在
     * @Author xck
     * @Date 2019/9/5 13:26
     **/
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @param key
     * @param time
     * @return boolean
     * @Description 设置缓存失效时间
     * @Author xck
     * @Date 2019/9/4 19:36
     **/
    public boolean expire(String key, long time) {
        try {
            if (hasKey(key)) {
                if (time > 0) {
                    redisTemplate.expire(key, time, TimeUnit.SECONDS);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @return long
     * @Description 获取有效时间
     * @Author xck
     * @Date 2019/9/5 13:44
     **/
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * @param key
     * @param value
     * @return boolean
     * @Description 普通缓存存入
     * @Author xck
     * @Date 2019/9/4 19:39
     **/
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @param value
     * @param time
     * @return boolean
     * @Description 带时间缓存
     * @Author xck
     * @Date 2019/9/5 14:17
     **/
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @param delta
     * @return long
     * @Description 递增   并设置失效时间
     * @Author xck
     * @Date 2019/9/5 13:49
     **/
    public long incr(String key, long delta, long time) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        long returnValue = redisTemplate.opsForValue().increment(key, delta);
        expire(key, time);
        return returnValue;
    }

    /**
     * @param key
     * @param delta
     * @return long
     * @Description 递增
     * @Author xck
     * @Date 2019/9/11 14:53
     **/
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * @param key
     * @param delta
     * @return long
     * @Description //递减
     * @Author xck
     * @Date 2019/9/5 13:51
     **/
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * @param key
     * @return java.lang.Object
     * @Description 普通缓存获取
     * @Author xck
     * @Date 2019/9/4 19:41
     **/
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }


    /********************************HASH*********************************************/
    /********************************HASH*********************************************/
    /**
     * @param key
     * @param item
     * @return java.lang.Object
     * @Description 根据key和item获取
     * @Author xck
     * @Date 2019/9/5 13:53
     **/
    public Object hashGet(String key, String item) {
        if (hasKey(key)) {
            return redisTemplate.opsForHash().get(key, item);
        } else return null;
    }

    /**
     * @param key
     * @return Map<Object, Object>
     * @Description 根据key获取map
     * @Author xck
     * @Date 2019/9/5 14:00
     **/
    public Map<Object, Object> getHashMap(String key) {
        return key == null ? null : redisTemplate.opsForHash().entries(key);
    }

    /**
     * @param key
     * @param map
     * @param time
     * @return boolean
     * @Description 设置map
     * @Author xck
     * @Date 2019/9/5 14:03
     **/
    public boolean setHashMap(String key, Map<String, Object> map, Long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @param map
     * @return boolean
     * @Description 设置map
     * @Author xck
     * @Date 2019/9/5 14:09
     **/
    public boolean setHashMap(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @param item
     * @param value
     * @return boolean
     * @Description 向一张hash表中放入数据, 如果不存在将创建
     * @Author xck
     * @Date 2019/9/5 14:18
     **/
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @param item
     * @param value
     * @param time
     * @return boolean
     * @Description 向一张hash表中放入数据, 如果不存在将创建
     * @Author xck
     * @Date 2019/9/5 14:18
     **/
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @param item
     * @return void
     * @Description 删除hash表值
     * @Author xck
     * @Date 2019/9/5 14:19
     **/
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * @param key
     * @param item
     * @return boolean
     * @Description 判断hash表中是否有该项的值
     * @Author xck
     * @Date 2019/9/5 14:21
     **/
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * @param key
     * @param item
     * @param by
     * @return double
     * @Description hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @Author xck
     * @Date 2019/9/5 14:24
     **/
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * @param key
     * @param item
     * @param by
     * @return double
     * @Description hash递减
     * @Author xck
     * @Date 2019/9/5 14:24
     **/
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
    /********************************SET*********************************************/
    /********************************SET*********************************************/
    /**
     * @param key
     * @return Set<Object>
     * @Description 根据key获取Set中的所有值
     * @Author xck
     * @Date 2019/9/5 14:25
     **/
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param key
     * @param values
     * @return long  成功个数
     * @Description 将数据放入set缓存
     * @Author xck
     * @Date 2019/9/5 14:27
     **/
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param key
     * @param value
     * @return boolean
     * @Description set中是否含有key
     * @Author xck
     * @Date 2019/9/5 14:26
     **/
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @param time
     * @param values
     * @return long
     * @Description 将set数据放入缓存
     * @Author xck
     * @Date 2019/9/5 14:28
     **/
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param key
     * @return long
     * @Description 获取set缓存大小
     * @Author xck
     * @Date 2019/9/5 14:30
     **/
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param key
     * @param values
     * @return long
     * @Description 移除值为value的set
     * @Author xck
     * @Date 2019/9/5 14:31
     **/
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /********************************list*********************************************/
    /********************************list*********************************************/
    /**
     * @param key
     * @param start
     * @param end
     * @return java.util.List<java.lang.Object>
     * @Description 获取list缓存
     * @Author xck
     * @Date 2019/9/5 14:34
     **/
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param key
     * @param value
     * @return boolean
     * @Description 将list放入缓存
     * @Author xck
     * @Date 2019/9/5 14:37
     **/
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @param value
     * @param time
     * @return boolean
     * @Description 将list放入缓存
     * @Author xck
     * @Date 2019/9/5 14:38
     **/
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @param value
     * @return boolean
     * @Description 将list放入缓存
     * @Author xck
     * @Date 2019/9/5 14:41
     **/
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @param value
     * @return boolean
     * @Description 将list放入缓存
     * @Author xck
     * @Date 2019/9/5 14:41
     **/
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @return long
     * @Description 获取list大小
     * @Author xck
     * @Date 2019/9/5 14:35
     **/
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param key
     * @param index
     * @param value
     * @return boolean
     * @Description 根据索引修改list中的某条数据
     * @Author xck
     * @Date 2019/9/5 14:42
     **/
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key
     * @param count
     * @param value
     * @return long
     * @Description 移除N个值为value
     * @Author xck
     * @Date 2019/9/5 14:43
     **/
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param key
     * @param index
     * @return java.lang.Object
     * @Description 根据index获取list中的值
     * @Author xck
     * @Date 2019/9/5 14:36
     **/
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
