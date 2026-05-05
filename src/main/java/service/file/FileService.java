package service.file;

import model.File;

import java.io.InputStream;

public interface FileService {
    File create(InputStream inputStream, String fileName);

    File getById(Long id);

    boolean delete(Long id);
}


