package com.kmpc.algobe.community.post.repository;

import com.kmpc.algobe.community.post.domain.dto.PostListDto;
import com.kmpc.algobe.community.post.domain.dto.PostResponseDto;
import com.kmpc.algobe.community.post.domain.dto.PostSearchType;
import com.kmpc.algobe.community.post.domain.dto.QuestionListDto;
import com.kmpc.algobe.community.post.domain.entity.Anonymous;
import com.kmpc.algobe.community.post.domain.entity.Post;
import com.kmpc.algobe.community.post.domain.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {
    Page<PostListDto> findAllByBoardBoardId(Long boardId, Pageable pageable, String keyword, PostSearchType searchType);

    List<PostListDto> findAllNoticeByBoardId(Long boardId);

    Optional<Question> findByQuestionId(Long postId);

    Optional<Post> findByPostId(Long postId);

    Optional<Anonymous> findByAnonymousId(Long postId);

    Page<QuestionListDto> findAllQuestionByBoardBoardId(Long boardId, Pageable pageable, String keyword, PostSearchType searchType);
}
