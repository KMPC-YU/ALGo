import { instance } from './index.js'

// 로그인
export function createBoard(boardData) {
    return instance.post('/api/v1/boards', {
        board_type : boardData.board_type,
        board_name : boardData.board_name,
    })
}

export function getBoardList(category) {
    return instance.get(`/api/v1/boards?category=${category}`)
}