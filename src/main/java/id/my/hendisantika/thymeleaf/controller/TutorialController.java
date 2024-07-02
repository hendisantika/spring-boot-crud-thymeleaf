package id.my.hendisantika.thymeleaf.controller;

import id.my.hendisantika.thymeleaf.entity.Tutorial;
import id.my.hendisantika.thymeleaf.repository.TutorialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-crud-thymeleaf
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/2/24
 * Time: 19:42
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class TutorialController {

    private final TutorialRepository tutorialRepository;

    @GetMapping("/tutorials")
    public String getAll(Model model, @RequestParam(required = false) String keyword,
                         @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "3") int size) {
        try {
            List<Tutorial> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page - 1, size);

            Page<Tutorial> pageTuts;
            if (keyword == null) {
                pageTuts = tutorialRepository.findAll(paging);
            } else {
                pageTuts = tutorialRepository.findByTitleContainingIgnoreCase(keyword, paging);
                model.addAttribute("keyword", keyword);
            }

            tutorials = pageTuts.getContent();

            model.addAttribute("tutorials", tutorials);
            model.addAttribute("currentPage", pageTuts.getNumber() + 1);
            model.addAttribute("totalItems", pageTuts.getTotalElements());
            model.addAttribute("totalPages", pageTuts.getTotalPages());
            model.addAttribute("pageSize", size);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "tutorials";
    }

    @GetMapping("/tutorials/new")
    public String addTutorial(Model model) {
        Tutorial tutorial = new Tutorial();
        tutorial.setPublished(true);

        model.addAttribute("tutorial", tutorial);
        model.addAttribute("pageTitle", "Create new Tutorial");

        return "tutorial_form";
    }

    @PostMapping("/tutorials/save")
    public String saveTutorial(Tutorial tutorial, RedirectAttributes redirectAttributes) {
        try {
            tutorialRepository.save(tutorial);

            redirectAttributes.addFlashAttribute("message", "The Tutorial has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/tutorials";
    }

    @GetMapping("/tutorials/{id}")
    public String editTutorial(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Tutorial tutorial = tutorialRepository.findById(id).get();

            model.addAttribute("tutorial", tutorial);
            model.addAttribute("pageTitle", "Edit Tutorial (ID: " + id + ")");

            return "tutorial_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/tutorials";
        }
    }
}
