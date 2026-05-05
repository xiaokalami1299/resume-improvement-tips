<script setup lang="ts">
import { computed, ref } from 'vue'
import mammoth from 'mammoth'

const props = defineProps<{
  modelValue: string
  maxLength?: number
}>()

const emit = defineEmits<{
  'update:modelValue': [value: string]
}>()

const maxLength = computed(() => props.maxLength || 5000)
const remaining = computed(() => maxLength.value - props.modelValue.length)
const isOverLimit = computed(() => props.modelValue.length > maxLength.value)

const isDragging = ref(false)
const uploadedFileName = ref('')
const uploadError = ref('')
const fileInputRef = ref<HTMLInputElement | null>(null)

function handleInput(event: Event) {
  const target = event.target as HTMLTextAreaElement
  emit('update:modelValue', target.value.slice(0, maxLength.value))
  clearUploadState()
}

function clearUploadState() {
  uploadedFileName.value = ''
  uploadError.value = ''
}

function handleDragOver(event: DragEvent) {
  event.preventDefault()
  isDragging.value = true
}

function handleDragLeave() {
  isDragging.value = false
}

function handleDrop(event: DragEvent) {
  event.preventDefault()
  isDragging.value = false
  
  const files = event.dataTransfer?.files
  if (files && files.length > 0) {
    processFile(files[0])
  }
}

function handleClick() {
  fileInputRef.value?.click()
}

function handleFileSelect(event: Event) {
  const target = event.target as HTMLInputElement
  const files = target.files
  if (files && files.length > 0) {
    processFile(files[0])
  }
  // 重置 input 以便可以重复选择同一文件
  target.value = ''
}

async function processFile(file: File) {
  // 清除之前的状态
  uploadError.value = ''
  
  // 检查文件类型
  if (!file.name.toLowerCase().endsWith('.docx')) {
    uploadError.value = '请上传 .docx 格式的 Word 文档'
    uploadedFileName.value = ''
    return
  }
  
  uploadedFileName.value = file.name
  
  try {
    const arrayBuffer = await file.arrayBuffer()
    const result = await mammoth.extractRawText({ arrayBuffer })
    
    // 将解析结果填充到 textarea
    emit('update:modelValue', result.value.slice(0, maxLength.value))
    
  } catch (error) {
    uploadError.value = '文档解析失败，请确保文件是有效的 Word 文档'
    uploadedFileName.value = ''
  }
}
</script>

<template>
  <div class="space-y-4">
    <!-- 拖拽上传区域 -->
    <div
      class="relative border-2 border-dashed rounded-lg p-8 text-center cursor-pointer transition-all"
      :class="[
        isDragging 
          ? 'border-blue-500 bg-blue-50' 
          : 'border-gray-300 hover:border-blue-400 hover:bg-gray-50'
      ]"
      @dragover="handleDragOver"
      @dragleave="handleDragLeave"
      @drop="handleDrop"
      @click="handleClick"
    >
      <input
        ref="fileInputRef"
        type="file"
        accept=".docx"
        class="hidden"
        @change="handleFileSelect"
      />
      
      <div class="flex flex-col items-center gap-3">
        <svg
          class="w-12 h-12"
          :class="isDragging ? 'text-blue-500' : 'text-gray-400'"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"
          />
        </svg>
        
        <div>
          <p class="text-gray-700 font-medium">
            {{ uploadedFileName ? `已加载: ${uploadedFileName}` : '拖拽 Word 文档到这里，或点击上传' }}
          </p>
          <p v-if="!uploadedFileName" class="text-sm text-gray-500 mt-1">
            支持 .docx 格式文件
          </p>
        </div>
        
        <!-- 成功提示 -->
        <div
          v-if="uploadedFileName && !uploadError"
          class="inline-flex items-center gap-1 px-3 py-1 bg-green-100 text-green-700 text-sm rounded-full"
        >
          <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd" />
          </svg>
          已加载
        </div>
      </div>
      
      <!-- 错误提示 -->
      <div
        v-if="uploadError"
        class="absolute bottom-4 left-1/2 transform -translate-x-1/2 px-4 py-2 bg-red-100 text-red-700 text-sm rounded-lg"
      >
        {{ uploadError }}
      </div>
    </div>
    
    <!-- 文本输入区域 -->
    <div class="space-y-2">
      <div class="flex justify-between items-center">
        <label class="block text-sm font-medium text-gray-700">简历内容</label>
        <span 
          class="text-sm"
          :class="isOverLimit ? 'text-red-500' : remaining <= 100 ? 'text-yellow-500' : 'text-gray-500'"
        >
          {{ modelValue.length }} / {{ maxLength }}
        </span>
      </div>
      <textarea
        :value="modelValue"
        @input="handleInput"
        class="w-full h-64 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent resize-none transition-all"
        placeholder="请粘贴您的简历文本内容..."
      ></textarea>
    </div>
  </div>
</template>