package controller;

import dto.EventDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.hibernate.event.EventRepository;
import repository.hibernate.event.EventRepositoryImpl;
import repository.hibernate.file.FileRepository;
import repository.hibernate.file.FileRepositoryImpl;
import repository.hibernate.user.UserRepository;
import repository.hibernate.user.UserRepositoryImpl;
import service.event.EventService;
import service.event.EventServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/events/*")
public class EventController extends HttpServlet {

    EventRepository eventRepository = new EventRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();
    FileRepository fileRepository = new FileRepositoryImpl();
    EventService eventService = new EventServiceImpl(eventRepository, userRepository, fileRepository);

    @Override
    public void init() throws ServletException {
        IO.println("Init event controller");
    }

    // GET /api/events - get all users
    // GET /api/events{id} - get user by id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            List<EventDTO> events = eventService.getAll();
            StringBuilder json = new StringBuilder();
            events.forEach((event) -> json.append(event.toJson()));
            resp.getWriter().write(String.valueOf(json));
        } else {
            Long id = Long.valueOf(info.substring(1));
            EventDTO event = eventService.getById(id);
            resp.getWriter().write(event.toJson());
        }

    }

    // POST /api/events - create new user
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String result = getResultRequest(req);

        EventDTO eventDTO = EventDTO.fromJson(result);

        if (eventDTO.getUserId() == null || eventDTO.getFile().getId() == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"User or file not found\"}");
            return;
        }
        try {
            EventDTO event = eventService.create(eventDTO);
            resp.getWriter().write(event.toJson());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"event dont save\"}");
        }

    }

    // PUT /api/users/{id} - delete user
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String info = req.getPathInfo();
        if (info != null && info.length() > 1) {
            Long id = Long.valueOf(info.substring(1));
            boolean result = eventService.delete(id);
            if (result) resp.getWriter().write("true");
            else resp.getWriter().write("false");
        }
    }

    @Override
    public void destroy() {
        IO.println("Destroy event controller");
    }


    private String getResultRequest(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) sb.append(line);
        return sb.toString();
    }
}
