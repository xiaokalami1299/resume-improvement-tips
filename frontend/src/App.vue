<script setup lang="ts">
import { ref, computed } from 'vue'
import ResumeInput from './components/ResumeInput.vue'
import AnalysisButton from './components/AnalysisButton.vue'
import SuggestionList from './components/SuggestionList.vue'
import { analyzeResume, type Suggestion } from './services/resumeService'

const resumeText = ref('')
const suggestions = ref<Suggestion[]>([])
const loading = ref(false)
const error = ref('')

const canAnalyze = computed(() => {
  return resumeText.value.trim().length > 0 && !loading.value
})

async function handleAnalyze() {
  if (!canAnalyze.value) return
  
  loading.value = true
  error.value = ''
  suggestions.value = []
  
  try {
    const response = await analyzeResume(resumeText.value)
    
    if (response.success && response.suggestions) {
      suggestions.value = response.suggestions
    } else {
      error.value = response.message || '分析失败，请重试'
    }
  } catch (err) {
    error.value = err instanceof Error ? err.message : '服务器连接失败，请检查后端服务是否启动'
  } finally {
    loading.value = false
  }
}

function handleClear() {
  resumeText.value = ''
  suggestions.value = []
  error.value = ''
}
</script>

<template>
  <div class="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50">
    <!-- 头部 -->
    <header class="bg-white shadow-sm">
      <div class="max-w-4xl mx-auto px-4 py-6">
        <div class="flex items-center justify-between">
          <div class="flex items-center gap-3">
            <div class="w-10 h-10 bg-primary-600 rounded-lg flex items-center justify-center">
              <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
              </svg>
            </div>
            <div>
              <h1 class="text-xl font-bold text-gray-800">简历优化建议</h1>
              <p class="text-sm text-gray-500">AI驱动的专业简历分析</p>
            </div>
          </div>
          <button
            v-if="resumeText || suggestions.length"
            @click="handleClear"
            class="px-4 py-2 text-sm text-gray-600 hover:text-gray-800 hover:bg-gray-100 rounded-lg transition-colors"
          >
            清空
          </button>
        </div>
      </div>
    </header>
    
    <!-- 主内容 -->
    <main class="max-w-4xl mx-auto px-4 py-8 space-y-8">
      <!-- 输入区域 -->
      <section class="bg-white rounded-xl shadow-lg p-6">
        <ResumeInput v-model="resumeText" :max-length="10000" />
      </section>
      
      <!-- 分析按钮 -->
      <section>
        <AnalysisButton 
          :loading="loading" 
          :disabled="!canAnalyze"
          @click="handleAnalyze"
        />
      </section>
      
      <!-- 错误提示 -->
      <section v-if="error" class="bg-red-50 border border-red-200 rounded-lg p-4">
        <div class="flex items-center gap-2 text-red-700">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <span>{{ error }}</span>
        </div>
      </section>
      
      <!-- 建议列表 -->
      <section v-if="suggestions.length > 0" class="bg-white rounded-xl shadow-lg p-6">
        <SuggestionList :suggestions="suggestions" />
      </section>
      
      <!-- 空状态 -->
      <section v-if="!loading && !error && !suggestions.length && resumeText" class="bg-gray-50 border border-gray-200 rounded-lg p-8 text-center">
        <svg class="w-16 h-16 text-gray-300 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
        </svg>
        <p class="text-gray-500">点击上方按钮开始分析您的简历</p>
      </section>
    </main>
    
    <!-- 底部 -->
    <footer class="bg-white border-t border-gray-200 mt-auto">
      <div class="max-w-4xl mx-auto px-4 py-4 text-center text-sm text-gray-500">
        基于 AI 技术提供简历优化建议 | 仅供参考
      </div>
    </footer>
  </div>
</template>
