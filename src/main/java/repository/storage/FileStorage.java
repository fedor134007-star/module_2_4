package repository.storage;

import dto.FileDTO;
import model.File;

import java.io.InputStream;

public interface FileStorage {

    public FileDTO saveFile(InputStream inputStream, String fileName);

    public byte[] getFile(String filePath);

    public boolean deleteFile(String filePath);
}
