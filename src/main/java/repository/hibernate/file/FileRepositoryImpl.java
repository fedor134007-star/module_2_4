package repository.hibernate.file;

import model.File;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtil;

import java.util.Optional;

public class FileRepositoryImpl implements FileRepository {

    @Override
    public Optional<File> save(File file) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            File savedUser = session.merge(file);
            session.getTransaction().commit();
            return Optional.of(savedUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }



    @Override
    public Optional<File> getById(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            File fileOpt = session.find(File.class, id);
            session.getTransaction().commit();
            return Optional.of(fileOpt);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(File file) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.remove(file);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
