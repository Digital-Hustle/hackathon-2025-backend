package rnd.sueta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ci_trainee.authms.config.BaseMapperConfig;
import ru.ci_trainee.authms.dto.request.RegisterUserRq;
import ru.ci_trainee.authms.dto.request.UserLoginRq;
import ru.ci_trainee.authms.model.UserWithCredentials;
import ru.ci_trainee.authms.model.entity.User;

@Mapper(config = BaseMapperConfig.class)
public interface UserMapper {

    UserWithCredentials convert(RegisterUserRq registerUserRq);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User convert(UserLoginRq userLoginRq);
}
