package service.event;

import dto.EventDTO;
import dto.FileDTO;
import dto.UserDTO;
import repository.hibernate.event.EventRepository;
import repository.hibernate.file.FileRepository;
import repository.hibernate.user.UserRepository;

import java.util.List;
import java.util.Optional;

public class EventServiceImpl implements EventService {
    final EventRepository eventRepository;
    final UserRepository userRepository;
    final FileRepository fileRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, FileRepository fileRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public EventDTO create(EventDTO eventDTO) {
        UserDTO user = userRepository.getById(eventDTO.getUserId()).orElse(null);
        FileDTO file = fileRepository.getById(eventDTO.getFile().getId()).orElse(null);

        if (user == null || file == null) throw new NullPointerException();
        Optional<EventDTO> opt = eventRepository.save(eventDTO);
        return opt.orElse(null);
    }

    @Override
    public EventDTO getById(Long id) {
        Optional<EventDTO> opt = eventRepository.getById(id);
        return opt.orElse(null);
    }

    @Override
    public List<EventDTO> getAll() {
        return eventRepository.getAll();
    }

    @Override
    public boolean delete(Long id) {
        return eventRepository.delete(id);
    }
}
