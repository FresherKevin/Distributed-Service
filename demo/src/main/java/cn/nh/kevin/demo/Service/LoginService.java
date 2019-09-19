package cn.nh.kevin.demo.Service;

import cn.nh.kevin.demo.DTO.ResultDTO;
import cn.nh.kevin.demo.DTO.UserDTO;
import cn.nh.kevin.demo.Enum.MessageEnum;
import cn.nh.kevin.demo.Enum.ResultEnum;
import cn.nh.kevin.demo.Mapper.UserMapper;
import cn.nh.kevin.demo.Util.Redis.RedisUtil;
import cn.nh.kevin.demo.Util.Time.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 标题:
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-08-26 19:29
 */
@Service
public class LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private String blackList = "blacklist";
    private int max = 5;
    private long keyOnceExpreTime = 60;
    private long blackKeyExpireTime = 1 * 60 * 1000;
    private long delta = 1;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;

    /**
     * @param id
     * @param password
     * @return java.lang.String
     * @Description //登录验证
     * @Author xck
     * @Date 2019/8/28 15:30
     **/
    public ResultDTO check(String id, String password) {
        if (id == null) {
            return new ResultDTO(ResultEnum.FAIL, MessageEnum.idEmptyMessage);
        } else if (password == null) {
            return new ResultDTO(ResultEnum.FAIL, MessageEnum.passwordEmptyMessage);
        } else {
            if (isInBlackList(id)) {
                return new ResultDTO(ResultEnum.FAIL, MessageEnum.wrongTooMuchMessage);
            }
            UserDTO userDTO = userMapper.findUser(id);
            if (userDTO == null) {
                return new ResultDTO(ResultEnum.FAIL, MessageEnum.idNotFindMessage);
            }
            if (userDTO.getPassword().equals(password)) {
                redisUtil.remove(id);
                return new ResultDTO(ResultEnum.SUCCESS, MessageEnum.checkSuccessMessage);
            } else {
                redisUtil.incr(id, delta, keyOnceExpreTime);
                if ((int) redisUtil.get(id) > max) {
                    redisUtil.remove(id);
                    long currentTime = System.currentTimeMillis() + blackKeyExpireTime;
                    Date date = new Date(currentTime);
                    redisUtil.hset(blackList, id, TimeUtil.date2String(date));
                }
                return new ResultDTO(ResultEnum.FAIL, MessageEnum.passwordErrorMessage);
            }
        }
    }

    public boolean isInBlackList(String id) {
        String time = ((String) redisUtil.hashGet(blackList, id));
        if (time == null) return false;
        String timeNow = TimeUtil.date2String(new Date());

        if (TimeUtil.timeCompare(time, timeNow)) {
            redisUtil.hdel(blackList, id);
            return false;
        } else return true;
    }


    /**
     * @param userDTO
     * @return cn.nh.kevin.demo.Enum.ResultEnum
     * @Description //注册
     * @Author xck
     * @Date 2019/8/28 15:30
     **/
    public ResultEnum register(UserDTO userDTO) {
        LOGGER.info("id为{}", userDTO.getId());
        try {
            userMapper.insertUser(userDTO);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                LOGGER.error("id={},已存在", userDTO.getId());
                throw e;
            } else {
                LOGGER.error("注册id={}异常", userDTO.getId());
            }
        }

        return ResultEnum.SUCCESS;
    }

    /**
     * @param id
     * @return cn.nh.kevin.demo.DTO.UserDTO
     * @Description //查询信息
     * @Author xck
     * @Date 2019/8/28 15:31
     **/
    public UserDTO queryById(String id) {
        return userMapper.findUser(id);
    }
}