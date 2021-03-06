package com.tuotiansudai.web.ask.controller;

import com.tuotiansudai.ask.repository.model.QuestionModel;
import com.tuotiansudai.ask.service.QuestionService;
import com.tuotiansudai.dto.BaseDto;
import com.tuotiansudai.dto.BasePaginationDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/")
public class HomeController {

    private final QuestionService questionService;

    @Autowired
    public HomeController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "group", defaultValue = "HOT", required = false) String group,
                              @RequestParam(value = "index", defaultValue = "1", required = false) String index) {

        QuestionGroup questionGroup = QuestionGroup.HOT;
        try {
            questionGroup = QuestionGroup.valueOf(group.toUpperCase());
        } catch (IllegalArgumentException ignore) {
        }

        int page = 1;
        try {
            page = Integer.parseInt(index);
        } catch (NumberFormatException ignored) {
        }

        ModelAndView modelAndView = new ModelAndView("/home");
        if (questionGroup == QuestionGroup.UNRESOLVED) {
            modelAndView.addObject("questions", questionService.findAllUnresolvedQuestions(page));
        }

        if (questionGroup == QuestionGroup.HOT) {
            modelAndView.addObject("questions", questionService.findAllHotQuestions(page));
        }

        if (questionGroup == QuestionGroup.ALL) {
            modelAndView.addObject("questions", questionService.findAllQuestions(page));
        }

        modelAndView.addObject("group", group);

        return modelAndView;
    }

    private enum QuestionGroup {
        ALL, UNRESOLVED, HOT
    }
}
