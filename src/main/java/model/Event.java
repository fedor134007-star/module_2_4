package model;

import com.google.gson.Gson;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;


    public Event() {
    }

    public Event(Long id, User user, File file) {
        this.id = id;
        this.user = user;
        this.file = file;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(user, event.user) && Objects.equals(file, event.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, file);
    }


    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Event fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Event.class);
    }


}



