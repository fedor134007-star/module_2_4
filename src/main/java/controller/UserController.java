package controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import repository.hibernate.user.UserRepository;
import repository.hibernate.user.UserRepositoryImpl;
import service.user.UserServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/users/*")
public class UserController extends HttpServlet {

    UserRepository userRepository = new UserRepositoryImpl();
    UserServiceImpl userService = new UserServiceImpl(userRepository);

    @Override
    public void init() throws ServletException {
        IO.println("Init user controller");
    }

    // GET /api/users - get all users
    // GET /api/users{id} - get user by id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String info = req.getPathInfo();
        if (info == null || info.equals("/")) {
            List<User> users = userService.getAll();
            StringBuilder json = new StringBuilder();
            users.forEach((user) -> json.append(user.toJson()));
            resp.getWriter().write(String.valueOf(json));
        } else {
            Long id = Long.valueOf(info.substring(1));
            User user = userService.getById(id);
            resp.getWriter().write(user.toJson());
        }
    }

    // POST /api/users - create new user
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String result = getResultRequest(req);
        User user = User.fromJson(result);
        if (checkUser(user, resp)) {
            User savedUser = userService.create(user);
            resp.getWriter().write(savedUser.toJson());
        }
    }

    // PUT /api/users/{id} - update user
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String result = getResultRequest(req);
        User user = User.fromJson(result);
        if (checkUser(user, resp)) {
            User savedUser = userService.update(user);
            resp.getWriter().write(user.toJson());
        }

    }

    // PUT /api/users/{id} - delete user
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String info = req.getPathInfo();
        if (info != null && info.length() > 1) {
            Long id = Long.valueOf(info.substring(1));
            boolean result = userService.delete(id);
            if (result) resp.getWriter().write("true");
            else resp.getWriter().write("false");
        }
    }

    @Override
    public void destroy() {
        IO.println("Destroy user controller");
    }

    private boolean checkUser(User user, HttpServletResponse resp)
            throws IOException {
        if (user.getName() == null || user.getName().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Username is empty\"}");
            return false;
        }
        return true;
    }

    private String getResultRequest(HttpServletRequest req)
            throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) sb.append(line);
        return sb.toString();
    }
}
