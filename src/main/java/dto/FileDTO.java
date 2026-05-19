package dto;

import com.google.gson.Gson;
import model.File;

public class FileDTO {
    private Long id;
    private String name;
    private String filePath;
    private byte[] fileContent;  // byte[], а не String

    public FileDTO() {
    }

    public FileDTO(File file) {
        this.id = file.getId();
        this.name = file.getName();
        this.filePath = file.getFilePath();
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

    // Конвертация обратно в Entity
    public File toEntity() {
        File file = new File();
        if (this.id != null) file.setId(this.id);
        if (this.name != null) file.setName(this.name);
        if (this.filePath != null) file.setFilePath(this.filePath);
        return file;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static FileDTO fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, FileDTO.class);
    }
}