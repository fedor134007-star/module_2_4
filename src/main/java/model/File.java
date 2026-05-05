package model;

import com.google.gson.Gson;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String name;

    @Column(name = "file_path")
    private String filePath;

    @Transient
    private byte[] fileContent;

    public File() {
    }

    public File(Long id, String name, String filePath) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
    }


    public File(Long id, String name, String filePath, byte[] fileContent) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.fileContent = fileContent;
    }


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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(id, file.id) && Objects.equals(name, file.name) && Objects.equals(filePath, file.filePath) && Objects.deepEquals(fileContent, file.fileContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, filePath, Arrays.hashCode(fileContent));
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static File fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, File.class);
    }
}
