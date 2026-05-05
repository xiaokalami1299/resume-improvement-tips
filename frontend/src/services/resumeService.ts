export interface Suggestion {
  id: number
  category: string
  content: string
  originalText: string
  suggestedText: string
  priority: 'high' | 'medium' | 'low'
}

export interface ResumeAnalysisResponse {
  success: boolean
  suggestions: Suggestion[]
  message: string
}

export async function analyzeResume(resumeText: string): Promise<ResumeAnalysisResponse> {
  const response = await fetch('/api/resume/analyze', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ resumeText })
  })
  
  if (!response.ok) {
    throw new Error('分析请求失败，请稍后重试')
  }
  
  return response.json()
}
