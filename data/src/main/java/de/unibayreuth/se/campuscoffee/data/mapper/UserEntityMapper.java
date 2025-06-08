package de.unibayreuth.se.campuscoffee.data.mapper;

import de.unibayreuth.se.campuscoffee.data.persistence.UserEntity;
import de.unibayreuth.se.campuscoffee.domain.model.User;
import org.mapstruct.Mapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

//@Mapper(componentModel = "spring")
//@ConditionalOnMissingBean
//public interface UserEntityMapper {
//
//    User fromEntity(UserEntity source);
//
//    UserEntity toEntity(User source);
//}