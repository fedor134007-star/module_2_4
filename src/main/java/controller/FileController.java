package controller;


import dto.FileDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import repository.hibernate.file.FileRepository;
import repository.hibernate.file.FileRepositoryImpl;
import repository.storage.FileStorage;
import repository.storage.FileStorageImpl;
import service.file.FileService;
import service.file.FileServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/v1/files/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1 MB - временное хранилище
        maxFileSize = 1024 * 1024 * 10,       // 10 MB - максимальный размер файла
        maxRequestSize = 1024 * 1024 * 15     // 15 MB - максимальный размер всего запроса
)
public class FileController extends HttpServlet {

    FileRepository fileRepository = new FileRepositoryImpl();
    FileStorage fileStorage = new FileStorageImpl();
    FileService fileService = new FileServiceImpl(fileRepository, fileStorage);

    @Override
    public void init() throws ServletException {
        IO.println("Init user controller");
    }

    // GET /api/files - get all users
    // GET /api/files{id} - get user by id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            List<FileDTO> files = fileService.getAll();
            StringBuilder json = new StringBuilder();
            files.forEach((file) -> json.append(file.toJson()));
            resp.getWriter().write(String.valueOf(json));
        } else if (info.length() > 1) {
            Long id = Long.valueOf(info.substring(1));
            FileDTO file = fileService.getById(id);
            if (file == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"File not found\"}");
                return;
            }
            resp.getWriter().write(file.toJson());
        }
    }

    // POST /api/files - create new user
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            Part filePart = req.getPart("file");
            if (filePart == null || filePart.getSize() == 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"File is required\"}");
                return;
            }
            FileDTO createdFile = fileService.create(filePart.getInputStream(), filePart.getSubmittedFileName());
            resp.getWriter().write(createdFile.toJson());
        } catch (Exception e) {
            IO.println(e.getMessage());
        }

    }


    // PUT /api/files/{id} - delete user
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String info = req.getPathInfo();
        if (info != null && info.length() > 1) {
            Long id = Long.valueOf(info.substring(1));
            boolean result = fileService.delete(id);
            if (result) resp.getWriter().write("true");
            else resp.getWriter().write("false");
        }
    }

    @Override
    public void destroy() {
        IO.println("Destroy user controller");
    }


    private String getResultRequest(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) sb.append(line);
        return sb.toString();
    }
}
