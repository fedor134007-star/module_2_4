package repository.hibernate.file;

import dto.FileDTO;
import model.File;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class FileRepositoryImpl implements FileRepository {

    @Override
    public Optional<FileDTO> save(FileDTO file) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            File savedUser = session.merge(file.toEntity());
            session.getTransaction().commit();
            return Optional.of(new FileDTO(savedUser));
        } catch (Exception e) {
            return Optional.empty();
        }
    }



    @Override
    public Optional<FileDTO> getById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            File fileOpt = session.find(File.class, id);
            session.getTransaction().commit();
            return Optional.of(new FileDTO(fileOpt));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(FileDTO file) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.remove(file.toEntity());
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<FileDTO> getAll() {
        String hql = "SELECT DISTINCT f FROM File f";
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            var files =  session.createQuery(hql, File.class).list();
            session.getTransaction().commit();
            return files.stream().map(FileDTO::new).toList();
        } catch (Exception e) {
            return null;
        }
    }
}
