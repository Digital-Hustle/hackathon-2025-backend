package rnd.sueta.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PlaceType {
    CONCERT_VENUE("Концертная площадка"),
    THEATER("Театр"),
    CINEMA("Кинотеатр"),
    ART_GALLERY("Художественная галерея"),
    MUSEUM("Музей"),

    NIGHTCLUB("Ночной клуб"),
    BAR_PUB("Бар/Паб"),
    BOWLING("Боулинг"),
    KARAOKE("Караоке"),

    STADIUM("Стадион"),
    GYM("Тренажерный зал"),
    SWIMMING_POOL("Бассейн"),

    CONFERENCE_HALL("Конференц-зал"),
    COWORKING("Коворкинг"),
    BUSINESS_CENTER("Бизнес-центр"),

    PARK("Парк"),
    SQUARE("Площадь"),
    URBAN_SPACE("Городское пространство"),

    RESTAURANT("Ресторан"),
    CAFE("Кафе"),
    FOOD_COURT("Фуд-корт"),
    STREET_FOOD("Уличная еда"),

    WEDDING_VENUE("Свадебная площадка"),
    PHOTO_STUDIO("Фотостудия"),
    LOFT_SPACE("Лофт-пространство"),
    INDUSTRIAL_VENUE("Индустриальная площадка"),

    OTHER("Другое");

    private final String displayName;

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static PlaceType fromDisplayName(String displayName) {
        for (PlaceType type : values()) {
            if (type.displayName.equals(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }
}
