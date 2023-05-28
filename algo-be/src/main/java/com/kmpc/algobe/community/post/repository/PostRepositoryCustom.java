package com.kmpc.algobe.community.post.repository;

import com.kmpc.algobe.community.post.domain.dto.PostListDto;
import com.kmpc.algobe.community.post.domain.dto.PostResponseDto;
import com.kmpc.algobe.community.post.domain.dto.PostSearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<PostListDto> findAllByBoardBoardId(Long boardId, Pageable pageable, String keyword, PostSearchType searchType);

}
