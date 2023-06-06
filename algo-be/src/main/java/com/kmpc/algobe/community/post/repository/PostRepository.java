package com.kmpc.algobe.community.post.repository;

import com.kmpc.algobe.community.post.domain.dto.PostListDto;
import com.kmpc.algobe.community.post.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository<T extends Post> extends JpaRepository<T, Long>, PostRepositoryCustom {
}
