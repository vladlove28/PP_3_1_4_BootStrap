package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        if (user == null) {
            throw new NullPointerException("User not found");
        }
        entityManager.remove(user);
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    // готов
    @Override
    public User findUserByName(String userName) {
        return entityManager.createQuery("Select u from User u left join fetch u.roles where u.name =:username", User.class)
                .setParameter("username", userName).getSingleResult();
    }

    @Override
    public User saveUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void update(User user) {
        entityManager.persist(entityManager.merge(user));
    }

}
