package repository.hibernate.file;

import dto.FileDTO;

import java.util.List;
import java.util.Optional;

public interface FileRepository {
    Optional<FileDTO> save(FileDTO file);

    Optional<FileDTO> getById(Long id);

    boolean delete(FileDTO file);

    List<FileDTO> getAll();
}


