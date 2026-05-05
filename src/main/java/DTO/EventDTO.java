package DTO;

import com.google.gson.Gson;

public class EventDTO {
    final Long userId;
    final Long fileId;


    public EventDTO(Long userId, Long fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getFileId() {
        return fileId;
    }

    String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static EventDTO fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, EventDTO.class);
    }
}
