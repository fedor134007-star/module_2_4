package repository.hibernate.file;

import model.File;

import java.util.Optional;

public interface FileRepository {
    Optional<File> save(File file);

    Optional<File> getById(Long id);

    boolean delete(File file);
}


