package com.mts.teta.courses.controller.UI;

import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.dto.UI.RequestToRegistration;
import com.mts.teta.courses.dto.UI.UserRequestToChange;
import com.mts.teta.courses.service.StatisticsCounter;
import com.mts.teta.courses.service.UserLister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class PersonalController {

    @Autowired
    private UserLister userLister;

    @Autowired
    private StatisticsCounter statisticsCounter;

    //Registration

    @GetMapping("/registration")
    public String registration(Model model) {
        statisticsCounter.countHandlerCall("registration");

        model.addAttribute("title", "Форма регистрации");
        model.addAttribute("requestToRegistration", new RequestToRegistration());

        return "registration.html";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute @Valid RequestToRegistration requestToRegistration, Errors errors) {
        statisticsCounter.countHandlerCall("registration " + requestToRegistration.getUsername());

        if (errors.hasErrors()) {
            return "registration.html";
        } else {
            userLister.createUserFromRegistration(requestToRegistration);
            return "redirect:/login";
        }
    }

    //Personal account page

    @GetMapping("/personal")
    public String personal(Authentication auth, Model model) {
        UserPrincipal user = userLister.userByUsername(auth.getName());
        statisticsCounter.countHandlerCall("personal " + user.getUserId());

        model.addAttribute("title", "Добро пожаловать в личный кабинет " +
                auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("userRequestToChange", new UserRequestToChange());
        model.addAttribute("passedLessons", user.getLessons());

        return "personal.html";
    }

    @PostMapping("/personal")
    public String personalEdit(@ModelAttribute UserRequestToChange userRequestToChange,
                               Authentication auth) {
        UserPrincipal user = userLister.userByUsername(auth.getName());
        statisticsCounter.countHandlerCall("edit personal " + user.getUserId());

        userLister.changeUserFromUI(user.getUserId(), userRequestToChange);

        return "redirect:/logout";
    }
}
