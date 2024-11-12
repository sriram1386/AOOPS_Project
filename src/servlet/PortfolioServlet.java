package servlet;

import dao.ArtworkDao;
import model.Artwork;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.google.gson.Gson;

@WebServlet("/PortfolioServlet")
public class PortfolioServlet extends HttpServlet {
    private ArtworkDao artworkDao = new ArtworkDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Assuming the user ID is stored in the session after login
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        // Check if user is logged in
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println("Please log in to view your portfolio.");
            return;
        }

        // Fetch artworks for the user
        List<Artwork> artworks = artworkDao.getArtworksByUserId(userId);

        // Convert artworks list to JSON and send it in the response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(artworks));
        out.flush();
    }
}
