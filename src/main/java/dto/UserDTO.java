package dto;

import com.google.gson.Gson;
import model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String name;
    private List<EventDTO> events;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        if (user.getEvents() != null) {
            this.events = user.getEvents().stream()
                    .map(EventDTO::new)
                    .collect(Collectors.toList());
        }
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

    // toJson метод
    public String toJson() {
        return new Gson().toJson(this);
    }

    public static UserDTO fromJson(String json) {
        return new Gson().fromJson(json, UserDTO.class);
    }

    public User toEntity() {
        User user = new User();
        if (id != null) user.setId(id);
        if (name != null) user.setName(this.name);
        if (events != null && !events.isEmpty()) {
            user.setEvents(this.events.stream().map(EventDTO::toEntity).collect(Collectors.toList()));
        }
        return user;
    }
}