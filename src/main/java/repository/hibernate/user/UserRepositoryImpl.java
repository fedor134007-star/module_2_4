package repository.hibernate.user;

import model.Event;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> save(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            User savedUser = session.merge(user);
            session.getTransaction().commit();
            return Optional.of(savedUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAll() {
        String hql = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.events e";
        String hqlEvent = "SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.file";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            var users = session.createQuery(hql, User.class).list();
            if (!users.isEmpty()) session.createQuery(hqlEvent, Event.class).list();
            session.getTransaction().commit();
            return users;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User update(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            User updatedUser = session.merge(user);
            session.getTransaction().commit();
            return updatedUser;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Optional<User> getById(Long id) {
        String hql = "SELECT DISTINCT u From User u LEFT JOIN FETCH u.events WHERE u.id = :id";
        String hqlEvent = "SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.file WHERE e.id IN (:ids)";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            User user = session.createQuery(hql, User.class)
                    .setParameter("id", id)
                    .uniqueResult();
            if (user != null && user.getEvents() != null && !user.getEvents().isEmpty()) {
                List<Long> eventIds = user.getEvents()
                        .stream()
                        .map(Event::getId)
                        .toList();

                session.createQuery(hqlEvent, Event.class)
                        .setParameter("ids", eventIds)
                        .list();
            }
            session.getTransaction().commit();
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            User user = session.find(User.class, id);
            if (user == null) return false;
            session.remove(user);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
