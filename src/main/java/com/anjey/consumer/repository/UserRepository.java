package com.anjey.consumer.repository;

import com.anjey.consumer.entity.User;
import com.anjey.consumer.entity.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


@Repository
public class UserRepository {

    @Autowired
    SessionFactory sessionFactory;

        public User findByUserName(String username) {

        Session session = this.sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class,username);
        return user;

    }

    public List getAllUsers(){
        List users = new ArrayList<User>();
        users = sessionFactory.getCurrentSession().createNativeQuery("from User")
                .list();
        return users;
    }


    public void createUser(String username, String password, Set roles) {
        Session session = this.sessionFactory.getCurrentSession();
        User user=new User(username,password,roles);
        Iterator<String> iterator = roles.iterator();
        while (iterator.hasNext()) {
            String role = iterator.next();
            UserRole userRole= new UserRole(user, role);
            session.persist(userRole);
        }
        session.persist(user);

    }
}