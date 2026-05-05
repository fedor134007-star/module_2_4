package repository.hibernate.event;

import model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository {

    Optional<Event> save(Event event);

    Optional<Event> getById(Long id);

    List<Event> getAll();

    boolean delete(Long id);
}
