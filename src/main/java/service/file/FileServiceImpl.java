package service.file;

import dto.FileDTO;
import repository.hibernate.file.FileRepository;
import repository.storage.FileStorage;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class FileServiceImpl implements FileService {
    final FileRepository fileRepository;
    final FileStorage fileStorage;

    public FileServiceImpl(FileRepository fileRepository, FileStorage fileStorage) {
        this.fileRepository = fileRepository;
        this.fileStorage = fileStorage;
    }

    @Override
    public FileDTO create(InputStream inputStream, String fileName) {
        FileDTO savedFile = fileStorage.saveFile(inputStream, fileName);
        Optional<FileDTO> opt = fileRepository.save(savedFile);
        return opt.orElse(null);
    }

    @Override
    public FileDTO getById(Long id) {
        Optional<FileDTO> opt = fileRepository.getById(id);
        opt.ifPresent(file -> {
            byte[] fileBytes = fileStorage.getFile(file.getFilePath());
            file.setFileContent(fileBytes);
        });
        return opt.orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        FileDTO file = getById(id);
        if (file != null) {
            fileStorage.deleteFile(file.getFilePath());
            return fileRepository.delete(file);
        }
        return false;
    }

    @Override
    public List<FileDTO> getAll() {
        return fileRepository.getAll();
    }
}
