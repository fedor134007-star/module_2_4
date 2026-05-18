package service.file;

import dto.FileDTO;

import java.io.InputStream;
import java.util.List;

public interface FileService {
    FileDTO create(InputStream inputStream, String fileName);

    FileDTO getById(Long id);

    boolean delete(Long id);

    List<FileDTO> getAll();
}


