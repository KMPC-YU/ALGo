<template>
  <div>
    <template v-for="(item, index) in items">
      <div class="divider" v-if="item.type === 'divider'" :key="`divider${index}`" />
      <menu-item v-else :key="index" v-bind="item" />
    </template>
  </div>
</template>

<script>
import MenuItem from './MenuItem.vue'

export default {
  components: {
    MenuItem,
  },

  props: {
    editor: {
      type: Object,
      required: true,
    },
  },

  setup(props) {
    const items = [
          {
            icon: 'type-bold',
            title: '굵게',
            action: () => props.editor.chain().focus().toggleBold().run(),
            isActive: () => props.editor.isActive('bold'),
          },
          {
            icon: 'type-italic',
            title: '기울이기',
            action: () => props.editor.chain().focus().toggleItalic().run(),
            isActive: () => props.editor.isActive('italic'),
          },
          {
            icon: 'type-strikethrough',
            title: '취소선',
            action: () => props.editor.chain().focus().toggleStrike().run(),
            isActive: () => props.editor.isActive('strike'),
          },
          {
            icon: 'code',
            title: '소스코드',
            action: () => props.editor.chain().focus().toggleCode().run(),
            isActive: () => props.editor.isActive('code'),
          },
          {
            icon: 'pen',
            title: '하이라이트',
            action: () => props.editor.chain().focus().toggleHighlight().run(),
            isActive: () => props.editor.isActive('highlight'),
          },
          {
            type: 'divider',
          },
          {
            icon: 'type-h1',
            title: '제목 1',
            action: () => props.editor.chain().focus().toggleHeading({level: 1}).run(),
            isActive: () => props.editor.isActive('heading', {level: 1}),
          },
          {
            icon: 'type-h2',
            title: '제목 2',
            action: () => props.editor.chain().focus().toggleHeading({level: 2}).run(),
            isActive: () => props.editor.isActive('heading', {level: 2}),
          },
          {
            icon: 'paragraph',
            title: '본문',
            action: () => props.editor.chain().focus().setParagraph().run(),
            isActive: () => props.editor.isActive('paragraph'),
          },
          {
            icon: 'list-ul',
            title: '글 머리글',
            action: () => props.editor.chain().focus().toggleBulletList().run(),
            isActive: () => props.editor.isActive('bulletList'),
          },
          {
            icon: 'list-ol',
            title: '번호 매기기',
            action: () => props.editor.chain().focus().toggleOrderedList().run(),
            isActive: () => props.editor.isActive('orderedList'),
          },
          {
            icon: 'list-task',
            title: '작업 목록',
            action: () => props.editor.chain().focus().toggleTaskList().run(),
            isActive: () => props.editor.isActive('taskList'),
          },
          {
            icon: 'code-square',
            title: '코드 블록',
            action: () => props.editor.chain().focus().toggleCodeBlock().run(),
            isActive: () => props.editor.isActive('codeBlock'),
          },
          {
            type: 'divider',
          },
          {
            icon: 'quote',
            title: '인용구',
            action: () => props.editor.chain().focus().toggleBlockquote().run(),
            isActive: () => props.editor.isActive('blockquote'),
          },
          {
            icon: 'hr',
            title: '구분선',
            action: () => props.editor.chain().focus().setHorizontalRule().run(),
          },
          {
            type: 'divider',
          },
          {
            icon: 'text-wrap',
            title: '강제 줄바꿈',
            action: () => props.editor.chain().focus().setHardBreak().run(),
          },
          {
            icon: 'trash3',
            title: '형식 지우기',
            action: () => props.editor.chain()
                .focus()
                .clearNodes()
                .unsetAllMarks()
                .run(),
          },
          {
            type: 'divider',
          },
          {
            icon: 'arrow-counterclockwise',
            title: '실행 취소',
            action: () => props.editor.chain().focus().undo().run(),
          },
          {
            icon: 'arrow-clockwise',
            title: '다시 실행',
            action: () => props.editor.chain().focus().redo().run(),
          },
        ]

    return {
      items,
    }
  }
}
</script>

<style lang="scss" scoped>
.divider {
  background-color: rgba(#fff, 0.25);
  height: 1.25rem;
  margin-left: 0.5rem;
  margin-right: 0.75rem;
  width: 1px;
}
</style>