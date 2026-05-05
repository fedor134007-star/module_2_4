package service.event;

import model.Event;

import java.util.List;

public interface EventService {
    public Event create(Long userId, Long fileId);

    public Event getById(Long id);

    public List<Event> getAll();

    public boolean delete(Long id);

}
