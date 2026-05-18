package dto;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Event;
import model.User;

public class EventDTO {
    private Long id;
    private Long userId;
    private FileDTO file;

    public EventDTO() {
    }

    public EventDTO(Event event) {
        this.id = event.getId();
        if (event.getUser() != null) {
            this.userId = event.getUser().getId();
        }
        if (event.getFile() != null) {
            this.file = new FileDTO(event.getFile());
        }
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public FileDTO getFile() {
        return file;
    }

    public void setFile(FileDTO file) {
        this.file = file;
    }

    // Конвертация обратно в Entity
    public Event toEntity() {
        Event event = new Event();
        if (this.id != null) event.setId(this.id);
        if (this.getUserId() != null) {
            User user = new User();
            user.setId(this.userId);
            event.setUser(user);
        }
        if (this.file != null) event.setFile(this.file.toEntity());
        return event;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static EventDTO fromJson(String json) {
        EventDTO eventDTO = new EventDTO();

        // parsing JSON in object
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        // get userId
        if (jsonObject.has("userId")) {
            eventDTO.setUserId(jsonObject.get("userId").getAsLong());
        }

        // Достаем fileId и создаем FileDTO
        if (jsonObject.has("fileId")) {
            Long fileId = jsonObject.get("fileId").getAsLong();
            FileDTO fileDTO = new FileDTO();
            fileDTO.setId(fileId);
            eventDTO.setFile(fileDTO);
        }

        return eventDTO;
    }
}