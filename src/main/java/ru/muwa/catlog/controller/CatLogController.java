package ru.muwa.catlog.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.muwa.catlog.model.CatLog;
import ru.muwa.catlog.model.ChartPoint;
import ru.muwa.catlog.model.FoodType;
import ru.muwa.catlog.service.CatLogService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class CatLogController {

    private final CatLogService service;

    public CatLogController(CatLogService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {

        // Получаем список логов
        Page<CatLog> p =  service.findAllByOrderByRecordedAtDesc(PageRequest.of(0, 10));
        model.addAttribute("page", p);

        // Получаем точки для графика
        List<ChartPoint> logs = service.findAllByOrderByRecordedAtDesc(PageRequest.of(0, 24))
                .stream()
                .filter(l -> !l.getGlucoseLevel().equals(CatLog.GLUCOSE_LEVEL_NOT_SPECIFIED))
                .map(c -> new ChartPoint(c.getRecordedAt(), c.getGlucoseLevel()))
                .sorted(Comparator.comparing(ChartPoint::x))
                .collect(Collectors.toList());


        model.addAttribute("chartData",logs);

        return "index";
    }

    @GetMapping("/logs")
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {

        // Получаем список логов
        Page<CatLog> p =  service.findAllByOrderByRecordedAtDesc(PageRequest.of(page, size));
        model.addAttribute("page", p);

        return "logs/list";
    }

    @GetMapping("/logs/new")
    public String createForm(Model model) {
        CatLog log = new CatLog();
        log.setRecordedAt(LocalDateTime.now());
        model.addAttribute("catLog", log);
        model.addAttribute("foodTypes", FoodType.values());
        return "logs/form";
    }

    @DeleteMapping("/logs/delete/{id}")
    public String delete(@PathVariable long id, Model model) {
        service.delete(id);
        return "redirect:/";
    }

    @PostMapping("/logs")
    public String save(@Valid @ModelAttribute("catLog") CatLog catLog, BindingResult br, Model model) {

        // Проверка данных формы на корректность
        if (br.hasErrors()) {
            model.addAttribute("foodTypes", FoodType.values());
            return "logs/form"; // Возвращаем форму на перезаполнение
        }

        // Если уровень сахара не указан, ставим заглушку
        if (catLog.getGlucoseLevel() == null
                || catLog.getGlucoseLevel().isNaN()
                || catLog.getGlucoseLevel().equals(0.0)
        )
            catLog.setGlucoseLevel(CatLog.GLUCOSE_LEVEL_NOT_SPECIFIED);

        // --- Расчёт калорийности при кормлении ---
        if (catLog.isFed() && catLog.getFoodType() != null && catLog.getPortionGr() != null) {
            FoodType ft = catLog.getFoodType();
            double calories = ((catLog.getPortionGr() / 100.0 ) * ft.getKcalPer100Gram());
            catLog.setPortionCal((int)calories);
        // Если не указана, калорийность нулевая
        } else {
            catLog.setPortionCal(0);
        }

        // Сохраняем
        service.save(catLog);
        return "redirect:/";
    }

    @GetMapping("/logs/{id}")
    public String view(@PathVariable Long id, Model model) {
        var opt = service.findById(id);
        if (opt.isEmpty()) return "redirect:/logs";
        model.addAttribute("catLog", opt.get());
        return "logs/view";
    }

    @GetMapping("/chart-data")
    public String chartData(@RequestParam(defaultValue = "24", required = false) int limit, Model model) {
        
        List<ChartPoint> logs = service.findAll(PageRequest.of(0, limit))
                      .stream()
                      .filter(l -> !l.getGlucoseLevel().equals(CatLog.GLUCOSE_LEVEL_NOT_SPECIFIED))
                      .map(c -> new ChartPoint(c.getRecordedAt(), c.getGlucoseLevel()))
                      .sorted(Comparator.comparing(ChartPoint::x))
                      .collect(Collectors.toList());

        model.addAttribute("chartData",
                logs.size() < 21?
                        logs
                        :
                        logs.subList(logs.size()-21,logs.size()-1));

        return "logs/chart";

    }

}

