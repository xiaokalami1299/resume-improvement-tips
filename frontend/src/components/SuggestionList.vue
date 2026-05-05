<script setup lang="ts">
import { ref } from 'vue'
import type { Suggestion } from '../services/resumeService'

defineProps<{
  suggestions: Suggestion[]
}>()

const expandedIds = ref<Set<number>>(new Set())

function toggleExpand(id: number) {
  if (expandedIds.value.has(id)) {
    expandedIds.value.delete(id)
  } else {
    expandedIds.value.add(id)
  }
}

function getPriorityClass(priority: string) {
  switch (priority) {
    case 'high':
      return 'bg-red-100 text-red-800'
    case 'medium':
      return 'bg-yellow-100 text-yellow-800'
    case 'low':
      return 'bg-green-100 text-green-800'
    default:
      return 'bg-gray-100 text-gray-800'
  }
}

function getPriorityText(priority: string) {
  switch (priority) {
    case 'high':
      return '高优先级'
    case 'medium':
      return '中优先级'
    case 'low':
      return '低优先级'
    default:
      return '未知'
  }
}
</script>

<template>
  <div class="w-full space-y-4">
    <h3 class="text-lg font-semibold text-gray-800 mb-4">
      优化建议（共 {{ suggestions.length }} 条）
    </h3>
    
    <div
      v-for="suggestion in suggestions"
      :key="suggestion.id"
      class="bg-white rounded-lg shadow-md overflow-hidden transition-all duration-200 hover:shadow-lg"
    >
      <!-- 建议头部 -->
      <div
        class="p-4 cursor-pointer flex items-center justify-between bg-gray-50 hover:bg-gray-100 transition-colors"
        @click="toggleExpand(suggestion.id)"
      >
        <div class="flex items-center gap-3">
          <span class="w-8 h-8 bg-primary-100 text-primary-600 rounded-full flex items-center justify-center font-medium text-sm">
            {{ suggestion.id }}
          </span>
          <div>
            <span class="font-medium text-gray-800">{{ suggestion.category }}</span>
            <span 
              class="ml-2 px-2 py-0.5 rounded-full text-xs font-medium"
              :class="getPriorityClass(suggestion.priority)"
            >
              {{ getPriorityText(suggestion.priority) }}
            </span>
          </div>
        </div>
        <svg
          class="w-5 h-5 text-gray-500 transition-transform duration-200"
          :class="{ 'rotate-180': expandedIds.has(suggestion.id) }"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
        >
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
        </svg>
      </div>
      
      <!-- 展开内容 -->
      <div
        class="overflow-hidden transition-all duration-200"
        :class="expandedIds.has(suggestion.id) ? 'max-h-96 opacity-100' : 'max-h-0 opacity-0'"
      >
        <div class="p-4 border-t border-gray-200 space-y-4">
          <div>
            <h4 class="text-sm font-medium text-gray-700 mb-2">建议内容</h4>
            <p class="text-gray-600 text-sm leading-relaxed">{{ suggestion.content }}</p>
          </div>
          
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <h4 class="text-sm font-medium text-gray-700 mb-2">原文</h4>
              <div class="p-3 bg-red-50 rounded-lg border border-red-200">
                <p class="text-gray-600 text-sm">{{ suggestion.originalText || '暂无原文片段' }}</p>
              </div>
            </div>
            <div>
              <h4 class="text-sm font-medium text-gray-700 mb-2">建议修改为</h4>
              <div class="p-3 bg-green-50 rounded-lg border border-green-200">
                <p class="text-gray-600 text-sm">{{ suggestion.suggestedText || '暂无建议内容' }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
