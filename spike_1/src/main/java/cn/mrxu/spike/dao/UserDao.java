package cn.mrxu.spike.dao;

import cn.mrxu.spike.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    /**
     * 根据 ID 查询对象
     * @param id 对象的ID
     * @return 查询的对象
     */
    @Select("select * from user where id = #{id}")
    User getById(@Param("id") int id);


    /**
     * 插入操作
     * @param user 插入的对象
     * @return 插入后表的行改变量
     */
    @Insert("insert into user (id, name) values (#{id}, #{name})")
    int insert(User user);
}
