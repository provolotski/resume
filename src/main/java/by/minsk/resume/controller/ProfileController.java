package by.minsk.resume.controller;

import by.minsk.resume.service.NameService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ProfileController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private NameService nameService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String param = request.getParameter("name");
        if (StringUtils.isNotBlank(param))
        {
            request.setAttribute("name", nameService.convertName(param));
        }
            request.getRequestDispatcher("/WEB-INF/JSP/profile.jsp").forward(request, response);

    }
}
