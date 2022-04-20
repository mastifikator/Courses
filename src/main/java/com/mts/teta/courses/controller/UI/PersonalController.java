package com.mts.teta.courses.controller.UI;

import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.service.StatisticsCounter;
import com.mts.teta.courses.service.UserLister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonalController {

    @Autowired
    private UserLister userLister;

    @Autowired
    private StatisticsCounter statisticsCounter;

    @GetMapping("/personal")
    public String personal(Authentication auth, Model model) {
        statisticsCounter.countHandlerCall("personal");

        UserPrincipal user = userLister.userByUsername(auth.getName());

        model.addAttribute("title", "Добро пожаловать в личный кабинет " +
                auth.getName());
        model.addAttribute("user", user);

        return "personal.html";
    }

    @PostMapping("/personal")
    public String personalEdit(Authentication auth, Model model) {
        statisticsCounter.countHandlerCall("personal");

        UserPrincipal user = userLister.userByUsername(auth.getName());

        model.addAttribute("title", "Добро пожаловать в личный кабинет " +
                auth.getName());
        model.addAttribute("user", user);

        return "personal.html";
    }
}
