package rnd.sueta.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EventType {
    CONCERT("Концерт"),
    FESTIVAL("Фестиваль"),

    THEATER_PLAY("Театральная постановка"),
    STANDUP_COMEDY("Стендап-комедия"),
    OPERA("Опера"),
    BALLET("Балет"),

    LECTURE("Лекция"),
    WORKSHOP("Мастер-класс"),
    CONFERENCE("Конференция"),
    TRAINING("Тренинг"),

    SPORTS_MATCH("Спортивный матч"),
    TOURNAMENT("Турнир"),
    MARATHON("Марафон"),

    PARTY("Вечеринка"),
    NIGHTLIFE("Ночное мероприятие"),

    EXHIBITION("Выставка"),

    COOKING_MASTERCLASS("Кулинарный мастер-класс"),

    NETWORKING("Нетворкинг"),
    BUSINESS_MEETUP("Бизнес-встреча"),

    KIDS_EVENT("Детское мероприятие"),
    FAMILY_DAY("Семейный день"),
    ANIMATION_SHOW("Анимационное шоу"),

    CHARITY("Благотворительное мероприятие"),

    OTHER_EVENT("Другое событие");

    private final String displayName;

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static EventType fromDisplayName(String displayName) {
        for (EventType type : values()) {
            if (type.displayName.equals(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }
}
