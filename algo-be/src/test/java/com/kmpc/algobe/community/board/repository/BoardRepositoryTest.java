package com.kmpc.algobe.community.board.repository;

import com.kmpc.algobe.community.board.domain.entity.Board;
import com.kmpc.algobe.community.board.domain.entity.BoardType;
import com.kmpc.algobe.config.TestQuerydslConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DisplayName("Board Repository Test")
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Import(TestQuerydslConfig.class)
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @AfterEach
    void clearRepo(){
        boardRepository.deleteAll();
    }

    @Nested
    @DisplayName("게시판 이름 존재 여부 확인")
    class ExistsByBoardName{
        @Test
        @DisplayName("게시판이 존재하는 경우")
        void existByBoardName_exist_success(){
            // given
            String boardName = "자유게시판";
            Board existBoard = Board.builder()
                    .boardName(boardName)
                    .boardType(BoardType.GENERAL)
                    .build();

            boardRepository.save(existBoard);

            // when
            Boolean isExist = boardRepository.existsBoardByBoardName(boardName);

            // then
            assertThat(isExist).isEqualTo(true);
        }

        @Test
        @DisplayName("게시판이 존재하지 않는 경우")
        void existByBoardName_nonExist_success(){
            // given
            String boardName = "자유게시판";
            Board existBoard = Board.builder()
                    .boardName(boardName)
                    .boardType(BoardType.GENERAL)
                    .build();

            boardRepository.save(existBoard);

            // when
            Boolean isExist = boardRepository.existsBoardByBoardName(boardName + "1");

            // then
            assertThat(isExist).isEqualTo(false);
        }
    }
}