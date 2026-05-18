package service.event;

import dto.EventDTO;

import java.util.List;

public interface EventService {
    public EventDTO create(EventDTO eventDTO);

    public EventDTO getById(Long id);

    public List<EventDTO> getAll();

    public boolean delete(Long id);

}
