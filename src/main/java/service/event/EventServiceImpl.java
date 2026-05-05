package service.event;

import model.Event;
import model.File;
import model.User;
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
    public Event create(Long userId, Long fileId) {
        User user = userRepository.getById(userId).orElse(null);
        File file = fileRepository.getById(fileId).orElse(null);

        if (user == null || file == null) throw new NullPointerException();

        Event event = new Event();
        event.setUser(user);
        event.setFile(file);

        Optional<Event> opt = eventRepository.save(event);
        return opt.orElse(null);
    }

    @Override
    public Event getById(Long id) {
        Optional<Event> opt = eventRepository.getById(id);
        return opt.orElse(null);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.getAll();
    }

    @Override
    public boolean delete(Long id) {
        return eventRepository.delete(id);
    }
}
