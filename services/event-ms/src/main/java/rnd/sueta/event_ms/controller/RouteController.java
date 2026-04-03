package rnd.sueta.event_ms.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import rnd.sueta.event_ms.constants.UrlPaths;
import rnd.sueta.event_ms.dto.request.CreateRouteRq;
import rnd.sueta.event_ms.dto.response.RouteWithDetailsRs;
import rnd.sueta.event_ms.dto.response.RouteWithPlacesRs;

import java.util.UUID;

@RequestMapping(UrlPaths.ROUTES)
public interface RouteController {

    @GetMapping(UrlPaths.BY_ID)
    RouteWithPlacesRs getById(@PathVariable UUID id);

    @PostMapping(UrlPaths.ROUTES_GENERATE)
    @ResponseStatus(HttpStatus.CREATED)
    RouteWithDetailsRs generateRoute(@RequestBody @Valid CreateRouteRq createRouteRq);
}
