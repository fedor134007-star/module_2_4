package repository.storage;

import model.File;
import utils.FileStorageConfig;

import java.io.FileOutputStream;
import java.io.InputStream;

import java.io.*;


public class FileStorageImpl implements FileStorage {


    @Override
    public File saveFile(InputStream inputStream, String fileName) {

        FileStorageConfig.checkDirectory();

        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        String filePath = FileStorageConfig.DIR + uniqueFileName;

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File fileEntity = new File();
        fileEntity.setName(uniqueFileName);
        fileEntity.setFilePath(filePath);
        return fileEntity;
    }

    @Override
    public byte[] getFile(String filePath) {
        FileStorageConfig.checkDirectory();
        java.io.File file = new java.io.File(filePath);
        byte[] data = new byte[(int) file.length()];

        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    @Override
    public boolean deleteFile(String filePath) {
        FileStorageConfig.checkDirectory();
        java.io.File file = new java.io.File(filePath);
        return file.delete();
    }
}
