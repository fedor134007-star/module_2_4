package repository.hibernate.user;

import dto.UserDTO;
import model.Event;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<UserDTO> save(UserDTO user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            User newUser = user.toEntity();
            User savedUser = session.merge(newUser);
            session.getTransaction().commit();
            return Optional.of(new UserDTO(savedUser));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserDTO> getAll() {
        String hql = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.events e";
        String hqlEvent = "SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.file";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            var users = session.createQuery(hql, User.class).list();
            if (!users.isEmpty()) session.createQuery(hqlEvent, Event.class).list();
            session.getTransaction().commit();
            return users.stream().map(UserDTO::new).toList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDTO update(UserDTO user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            User updatedUser = session.merge(user.toEntity());
            session.getTransaction().commit();
            return new UserDTO(updatedUser);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Optional<UserDTO> getById(Long id) {
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
            return Optional.of(new UserDTO(user));
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
