package ru.ci_trainee.authms.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BaseMapperConfig {
}
