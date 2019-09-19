package cn.nh.kevin.demo.Mapper;

import cn.nh.kevin.demo.DTO.UserDTO;
import org.apache.ibatis.annotations.*;

/**
 * 标题:UserMapper
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-08-28 14:13
 */
@Mapper
public interface UserMapper {
    @Insert("update user set info=#{info} where id = #{id}")
    void updateUser(@Param("id") String id, UserDTO userDTO);

    @Select("Select * from user where id = #{id} and ROW_COUNT()<1")
    @Results({@Result(property = "info", column = "information")})
    UserDTO findUser(@Param("id") String id);

    @Delete("delete from user where id = #{id}")
    void deleteUser(@Param("id") String id);

    @Insert("insert into user(id,name,password,information) values(#{id},#{name},#{password},#{info})")
    void insertUser(UserDTO userDTO);
}
