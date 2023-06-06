package com.kmpc.algobe.community.comment.repository;

import com.kmpc.algobe.community.comment.domain.dto.CommentListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom{
    Page<CommentListDto> findAllByPostId(Long postId, Pageable pageable);
}
