package rnd.sueta.event_ms.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Contacts {

    private List<String> mobileNumbers;

    private String email;

    private String websiteUrl;

    private Map<String, String> social;
}
