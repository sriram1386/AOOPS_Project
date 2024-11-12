package servlet;

import dao.UserDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Validate form data (basic example)
        if (username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            response.getWriter().println("All fields are required.");
            return;
        }

        // Check if username already exists
        if (userDao.usernameExists(username)) {
            response.getWriter().println("Username is already taken. Please choose another.");
            return;
        }

        // Create new User object and save to the database
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);  // Consider encrypting passwords in production
        newUser.setEmail(email);

        boolean isRegistered = userDao.registerUser(newUser);

        if (isRegistered) {
            response.sendRedirect("login.html");  // Redirect to login page after successful registration
        } else {
            response.getWriter().println("Registration failed. Please try again.");
        }
    }
}
