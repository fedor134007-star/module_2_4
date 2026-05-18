package repository.hibernate.event;

import dto.EventDTO;
import model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository {

    Optional<EventDTO> save(EventDTO event);

    Optional<EventDTO> getById(Long id);

    List<EventDTO> getAll();

    boolean delete(Long id);
}
