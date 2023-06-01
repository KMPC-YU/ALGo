import { instance } from './index.js'

// 댓글 목록
export function getCommentsList(board_id, post_id, page) {
    return instance.get(`/api/v1/boards/${board_id}/posts/${post_id}/comments?page=${page}&size=10&sort=createdAt,DESC`)
}

// 댓글 추가
export function commentWrite(board_id, post_id, content, parent) {
    return instance.post(`/api/v1/boards/${board_id}/posts/${post_id}/comments`, {
        content: content,
        parent: parent
    })
}

export function commentModify(board_id, post_id, comment_id, content) {
    return instance.patch(`/api/v1/boards/${board_id}/posts/${post_id}/comments/${comment_id}`, {
        content: content,
    })
}

export function commentDelete(board_id, post_id, comment_id) {
    return instance.delete(`/api/v1/boards/${board_id}/posts/${post_id}/comments/${comment_id}`)
}