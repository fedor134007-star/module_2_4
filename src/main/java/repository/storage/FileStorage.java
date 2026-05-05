package repository.storage;

import model.File;

import java.io.InputStream;

public interface FileStorage {

    public File saveFile(InputStream inputStream,  String fileName);

    public byte[] getFile(String filePath);

    public boolean deleteFile(String filePath);
}
