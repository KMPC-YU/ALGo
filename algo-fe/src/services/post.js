import { instance } from './index.js'

// 게시글 목록
export function getPostsList(board_id, page) {
    return instance.get(`/api/v1/boards/${board_id}/posts?page=${page}&size=10&sort=createdAt,DESC`)
}

// 게시글 작성
export function postWrite(board_id, postData) {
    return instance.post(`/api/v1/boards/${board_id}/posts`, {
        title: postData.title,
        content: postData.content,
        point: postData.point,
        notice: postData.notice,
    })
}

// 게시글 수정
export function postModify(board_id, post_id, postData) {
    return instance.patch(`/api/v1/boards/${board_id}/posts/${post_id}`, {
        title: postData.title,
        content: postData.content,
        point: postData.point,
        notice: postData.notice,
    })
}

// 게시글 삭제
export function postDelete(board_id, post_id) {
    return instance.delete(`/api/v1/boards/${board_id}/posts/${post_id}`)
}

// 게시글 상세조회
export function postDetail(board_id, post_id) {
    return instance.get(`/api/v1/boards/${board_id}/posts/${post_id}`)
}

// 게시판 정보
export function BoardInfo(board_id) {
    return instance.get(`/api/v1/boards/${board_id}/types`)
}