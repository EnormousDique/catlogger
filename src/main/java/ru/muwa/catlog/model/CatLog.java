package ru.muwa.catlog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "cat_log")
@Data
public class CatLog {

    public static final Double GLUCOSE_LEVEL_NOT_SPECIFIED = -1.0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime recordedAt;

    // уровень сахара
    private Double glucoseLevel;

    // был ли сделан укол
    private boolean insulinInjected;

    // доза укола в мл
    @DecimalMin("0.0")
    private Double insulinDoseMl;

    // был ли покормлен
    private boolean fed;

    // тип корма
    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    // порция в граммах
    @Min(0)
    private Integer portionGr;

    // калорийность порции
    @Min(0)
    private Integer portionCal;

    // комментарий к записи
    private String comment;

}
