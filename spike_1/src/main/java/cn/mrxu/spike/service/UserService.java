package cn.mrxu.spike.service;

import cn.mrxu.spike.dao.UserDao;
import cn.mrxu.spike.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {


    @Autowired
    private UserDao userDao;


    /**
     * 测试查询操作
     * @param id 查询的ID
     * @return 查询的对象
     */
    public User getById(int id){
        return userDao.getById(id);
    }


    /**
     * 测试事务
     * @return 事务是否成功
     */
    //@Transactional
    public boolean tx(){
        User user1 = new User();
        user1.setId(4);
        user1.setName("4444");
        userDao.insert(user1);

        User user2 = new User();
        user2.setId(1);
        user2.setName("5555");
        userDao.insert(user2);

        return true;
    }
}
