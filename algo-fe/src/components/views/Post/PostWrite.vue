<template>
  <div class="container py-5">
    <h2 class="mb-4">{{ boardName }}</h2>
    <div class="mt-3">
      <div class="form-group">
        <label for="title">제목</label>
        <div class="row align-items-center">
          <div class="col-10">
            <input type="text" v-model="title" class="form-control mt-2" id="title" placeholder="글 제목을 입력해주세요." maxlength="50" required>
          </div>
          <div class="col-2">
            <div class="form-check">
              <input class="form-check-input" type="checkbox" value="" id="is_notice" v-model="is_notice">
              <label class="form-check-label" for="is_notice">
                공지사항 등록
              </label>
            </div>
          </div>
        </div>
        <div v-if="boardType === 'QUESTION'">
          <label for="point" class="mt-4">채택 포인트 (보유 포인트 : 0)</label>
          <input class="form-control" id="point" v-model="point">
        </div>
      </div>
      <br/>
      <div class="form-group">
        <label for="editor">본문</label>
        <div class="editor mt-2" v-if="editor" id="editor">
          <MenuBar className="editor__header" :editor="editor"/>
          <EditorContent className="editor__content" :editor="editor"/>
        </div>
      </div>
    </div>
    <div class="text-end mt-3">
      <div class="btn-group" role="group" aria-label="Basic example">
        <button type="button" class="btn btn-primary" @click="postWrite">
          <i class="fa-solid fa-pencil me-1"></i>
          글쓰기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import Highlight from '@tiptap/extension-highlight'
import TaskItem from '@tiptap/extension-task-item'
import TaskList from '@tiptap/extension-task-list'
import StarterKit from '@tiptap/starter-kit'
import { useEditor, EditorContent } from '@tiptap/vue-3'
import * as PostAPI from '@/services/post.js'

import MenuBar from '@compo/editor/MenuBar.vue'
import {ref, computed, onMounted, watch} from "vue";
import { useRoute } from 'vue-router'
import Swal from "sweetalert2";
import router from "@/router/router.js";

export default {
  components: {
    EditorContent,
    MenuBar,
  },
  setup() {
    const editor = useEditor({
      content: '',
      extensions: [
        StarterKit,
        Highlight,
        Image,
        TaskItem,
        TaskList,
      ],
    })

    const route = useRoute()
    const boardID = computed(() => {
      return route.params.board_id
    })

    const boardName = ref('')

    watch(boardID, () => {
      getBoardInfo()
    })

    onMounted(() => {
      getBoardInfo()
      if (route.path.endsWith('modify')) getPostDetail()
    })

    const getBoardInfo = () => {
      PostAPI.BoardInfo(boardID.value).then((res) => {
        boardName.value = res.data.board_name
        boardType.value = res.data.board_type
      })
    }

    const getPostDetail = () => {
      let postID = route.params.post_id
      PostAPI.postDetail(boardID.value, postID).then((res) => {
        title.value = res.data.title
        editor.value.commands.setContent(res.data.content)
        is_notice.value = res.data.notice
        point.value = res.data.point
      }).catch((err) => {
        console.error(err)
      })
    }

    const title = ref('')
    const is_notice = ref(false)
    const boardType = ref('')
    const point = ref(0)

    const postWrite = () => {
      if (title.value === '' || editor.value.getHTML() === '<p></p>') {
        alert('게시글 내용을 모두 작성해주세요.')
      } else {
        if (route.path.endsWith('modify')) {
          PostAPI.postModify(boardID.value, route.params.post_id, {
            title: title.value,
            content: editor.value.getHTML(),
            point: point.value,
            notice: is_notice.value,
          }).then(() => {
            router.push({ name: 'PostList', params: { board_id: boardID.value }, query: { page: 1 } })
          })
        } else {
          PostAPI.postWrite(boardID.value, {
            title: title.value,
            content: editor.value.getHTML(),
            point: point.value,
            notice: is_notice.value,
          }).then(() => {
            router.push({ name: 'PostList', params: { board_id: boardID.value }, query: { page: 1 } })
          })
        }
      }
    }

    return {
      editor, title, postWrite, is_notice, boardID, boardName, boardType, point
    }
  }
}
</script>

<style lang="scss">
//.btn-default {
//  color: #394263;
//  background-color: #ffffff;
//  border-color: #dbe1e8;
//  font-weight: bold;
//  letter-spacing: 0.05em;
//}
//.btn-default:hover,
//.btn-default:active,
//.btn-default:focus {
//  background: #fafafa;
//  //color: #ffffff;
//  border-color: #000000;
//}

.editor {
  background-color: #FFF;
  border: var(--bs-border-width) solid var(--bs-border-color);
  border-radius: 0.375rem;
  color: #0D0D0D;
  display: flex;
  flex-direction: column;
  max-height: 26rem;

  &__header {
    align-items: center;
    //background: #0d0d0d;
    border-bottom: var(--bs-border-width) solid var(--bs-border-color);
    border-top-left-radius: 0.25rem;
    border-top-right-radius: 0.25rem;
    display: flex;
    flex: 0 0 auto;
    flex-wrap: wrap;
    padding: 0.25rem;
  }

  &__content {
    flex: 1 1 auto;
    overflow-x: hidden;
    overflow-y: auto;
    padding: 1.25rem 1rem;
    -webkit-overflow-scrolling: touch;
  }

  &__footer {
    align-items: center;
    border-top: 3px solid #0D0D0D;
    color: #0D0D0D;
    display: flex;
    flex: 0 0 auto;
    flex-wrap: wrap;
    font-size: 12px;
    font-weight: 600;
    justify-content: space-between;
    padding: 0.25rem 0.75rem;
    white-space: nowrap;
  }

  /* Some information about the status */
  &__status {
    align-items: center;
    border-radius: 5px;
    display: flex;

    &::before {
      background: rgba(#0D0D0D, 0.5);
      border-radius: 50%;
      content: ' ';
      display: inline-block;
      flex: 0 0 auto;
      height: 0.5rem;
      margin-right: 0.5rem;
      width: 0.5rem;
    }

    &--connecting::before {
      background: #616161;
    }

    &--connected::before {
      background: #B9F18D;
    }
  }

  &__name {
    button {
      background: none;
      border: none;
      border-radius: 0.4rem;
      color: #0D0D0D;
      font: inherit;
      font-size: 12px;
      font-weight: 600;
      padding: 0.25rem 0.5rem;

      &:hover {
        background-color: #0D0D0D;
        color: #FFF;
      }
    }
  }
}

/* Basic editor styles */
.ProseMirror:focus {
  outline: none;
}

.ProseMirror {

  > * + * {
    margin-top: 0.75em;
  }

  ul,
  ol {
    padding: 0 1rem;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }

  pre {
    background: #0D0D0D;
    border-radius: 0.5rem;
    color: #FFF;
    font-family: 'JetBrainsMono', monospace;
    padding: 0.75rem 1rem;

    code {
      background: none;
      color: inherit;
      font-size: 0.8rem;
      padding: 0;
    }
  }

  mark {
    background-color: #FAF594;
  }

  img {
    height: auto;
    max-width: 100%;
  }

  hr {
    margin: 1rem 0;
  }

  blockquote {
    border-left: 2px solid rgba(#0D0D0D, 0.1);
    padding-left: 1rem;
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0D0D0D, 0.1);
    margin: 2rem 0;
  }

  ul[data-type="taskList"] {
    list-style: none;
    padding: 0;

    li {
      align-items: center;
      display: flex;

      > label {
        flex: 0 0 auto;
        margin-right: 0.5rem;
        user-select: none;
      }

      > div {
        flex: 1 1 auto;
      }
    }
  }
}
</style>