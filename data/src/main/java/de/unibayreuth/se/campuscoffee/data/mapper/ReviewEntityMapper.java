package de.unibayreuth.se.campuscoffee.data.mapper;

import de.unibayreuth.se.campuscoffee.data.persistence.entity.ReviewEntity;
import de.unibayreuth.se.campuscoffee.domain.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewEntityMapper {

    @Mapping(target = "pos", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "approvalCount", defaultValue = "0")
    ReviewEntity toEntity(Review source);

    @Mapping(target = "posId", ignore = true)
    @Mapping(target = "authorId", ignore = true)
    Review fromEntity(ReviewEntity source);
}