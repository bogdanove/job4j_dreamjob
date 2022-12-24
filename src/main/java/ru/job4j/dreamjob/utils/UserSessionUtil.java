package ru.job4j.dreamjob.utils;

import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

public class UserSessionUtil {

    public static void addUserToModel(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }
}
