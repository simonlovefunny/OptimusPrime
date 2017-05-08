package com.blog.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;


import com.blog.model.User;

/**
 * 
 * @ClassName UserDao
 * @Description 
 * @author 昭毅
 * @Date 2017年5月8日 下午2:53:26
 * @version 1.0.0
 */
@Transactional
@Repository
public class UserDao extends HibernateDaoSupport{
    
    @Resource
    public void setHibernateTemplate(SessionFactory sessionFactory){
     
        super.setSessionFactory(sessionFactory);
        
        
    }
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return List<Blog> 返回Blog列表
     */
    public List<User> findByUsername(final String username) {
        return this.getHibernateTemplate().execute(new HibernateCallback<List<User>>() {
            @Transactional
            public List<User> doInHibernate(Session session) {
                Query query = session.createQuery("from User where username=" + username);
                return query.list();
            }
        });
    }
    
    public void save(User user){
        
        this.getHibernateTemplate().saveOrUpdate(user);
    }
    
    public void delete(User user){
        
        this.getHibernateTemplate().delete(user);
    }
    
    public User findById(Integer id){
        
        return this.getHibernateTemplate().get(User.class, id);
    }
    
    public List<User> findAll(){
        
        return this.getHibernateTemplate().loadAll(User.class);
    }
}
