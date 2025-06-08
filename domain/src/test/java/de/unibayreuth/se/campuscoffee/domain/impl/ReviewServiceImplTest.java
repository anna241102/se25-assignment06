package de.unibayreuth.se.campuscoffee.domain.impl;

import de.unibayreuth.se.campuscoffee.domain.model.Review;
import de.unibayreuth.se.campuscoffee.data.mapper.ReviewEntityMapper;
import de.unibayreuth.se.campuscoffee.persistence.entity.ReviewEntity;
import de.unibayreuth.se.campuscoffee.persistence.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceImplTest {

    private ReviewRepository reviewRepository;
    private ReviewEntityMapper mapper;
    private ReviewServiceImpl service;

    @BeforeEach
    void setup() {
        reviewRepository = mock(ReviewRepository.class);
        mapper = mock(ReviewEntityMapper.class);
        service = new ReviewServiceImpl(reviewRepository, mapper);
    }

    @Test
    void testCreateSetsDefaultsAndSavesReview() {
        Review input = new Review();
        input.setPosId(1L);
        input.setAuthorId(1L);
        input.setReview("Test");

        ReviewEntity entity = new ReviewEntity();
        ReviewEntity saved = new ReviewEntity();
        Review mappedBack = new Review();

        when(mapper.toEntity(any())).thenReturn(entity);
        when(reviewRepository.save(entity)).thenReturn(saved);
        when(mapper.fromEntity(saved)).thenReturn(mappedBack);

        Review result = service.create(input);

        assertEquals(mappedBack, result);
        verify(reviewRepository).save(entity);
        verify(mapper).toEntity(any());
        verify(mapper).fromEntity(saved);
    }

}
@Test
void testGetByIdReturnsReview() {
    Long reviewId = 1L;
    ReviewEntity entity = new ReviewEntity();
    Review mapped = new Review();

    when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(entity));
    when(mapper.fromEntity(entity)).thenReturn(mapped);

    Review result = service.getById(reviewId);

    assertEquals(mapped, result);
    verify(reviewRepository).findById(reviewId);
    verify(mapper).fromEntity(entity);
}

@Test
void testGetByIdThrowsWhenNotFound() {
    Long reviewId = 99L;
    when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> service.getById(reviewId));
    assertTrue(exception.getMessage().contains("Review not found"));
}

@Test
void testGetApprovedByPosIdReturnsList() {
    Long posId = 1L;
    ReviewEntity entity = new ReviewEntity();
    Review mapped = new Review();

    when(reviewRepository.findAllByPosIdAndApprovedTrue(posId)).thenReturn(List.of(entity));
    when(mapper.fromEntity(entity)).thenReturn(mapped);

    List<Review> result = service.getApprovedByPosId(posId);

    assertEquals(1, result.size());
    assertEquals(mapped, result.get(0));
}

@Test
void testApproveThrowsIfUserIsAuthor() {
    Long reviewId = 1L;
    UserDto userDto = new UserDto();
    userDto.setId(42L);

    ReviewEntity entity = new ReviewEntity();
    Review review = new Review();
    review.setAuthorId(42L); // = userDto

    when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(entity));
    when(mapper.fromEntity(entity)).thenReturn(review);

    RuntimeException exception = assertThrows(RuntimeException.class,
            () -> service.approve(reviewId, userDto));

    assertTrue(exception.getMessage().contains("Users cannot approve"));
}


