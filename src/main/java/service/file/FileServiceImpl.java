package service.file;

import model.File;
import repository.hibernate.file.FileRepository;
import repository.storage.FileStorage;
import repository.storage.FileStorageImpl;

import java.io.InputStream;
import java.util.Optional;

public class FileServiceImpl implements FileService {
    final FileRepository fileRepository;

    final FileStorage fileStorage = new FileStorageImpl();

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File create(InputStream inputStream, String fileName) {
        File savedFile = fileStorage.saveFile(inputStream, fileName);
        Optional<File> opt = fileRepository.save(savedFile);
        return opt.orElse(null);
    }

    @Override
    public File getById(Long id) {
        Optional<File> opt = fileRepository.getById(id);
        opt.ifPresent(file -> {
            byte[] fileBytes = fileStorage.getFile(file.getFilePath());
            file.setFileContent(fileBytes);
        });
        return opt.orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        File file = getById(id);
        if (file != null) {
            fileStorage.deleteFile(file.getFilePath());
            return fileRepository.delete(file);
        }
        return false;
    }
}
