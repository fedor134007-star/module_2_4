package repository.hibernate.event;

import dto.EventDTO;
import model.Event;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventRepositoryImpl implements EventRepository {

    @Override
    public Optional<EventDTO> save(EventDTO event) {

        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Event savedEvent = session.merge(event.toEntity());
            session.getTransaction().commit();
            return Optional.of(new EventDTO(savedEvent));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public Optional<EventDTO> getById(Long id) {
        String hql = "From Event e LEFT JOIN FETCH e.file WHERE e.id = :id";
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Event event = session.createQuery(hql, Event.class)
                    .setParameter("id", id)
                    .uniqueResult();
            session.getTransaction().commit();
            return Optional.of(new EventDTO(event));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<EventDTO> getAll() {
        String hql = "SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.file f";
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            var events = session.createQuery(hql, Event.class).list();
            session.getTransaction().commit();
            return events.stream().map(EventDTO::new).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Event event = session.find(Event.class, id);
            if (event == null) return false;
            session.remove(event);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
