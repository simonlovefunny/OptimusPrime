package com.blog.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blog.dao.UserDao;
import com.blog.model.User;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestUser {
    
    private UserDao userDAO;

    @Resource
    public void setUserDAO(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Test
    //≤‚ ‘save
    public void testSave() throws Exception {
        User user = new User();
        user.setUid(1);
        user.setUsername("123");
        user.setPassword("456");
        userDAO.save(user);
    }
}
