import axios from 'axios'
import type { News, Comment } from '@/types'

const apiClient = axios.create({
  baseURL: 'http://localhost:3000',
  withCredentials: false,
  headers: { Accept: 'application/json', 'Content-Type': 'application/json' }
})

export default {
  getNews(perPage: number, page: number, status: string = 'all') {
    let url = `/news?_limit=${perPage}&_page=${page}`
    if (status !== 'all') url += `&status=${status}`
    return apiClient.get<News[]>(url)
  },

  // ⬇️ embed related comments in a single request
  getNewsItem(id: number) {
    return apiClient.get<News>(`/news/${id}`, { params: { _embed: 'comments' } })
  },

  // keep your existing vote update
  voteNews(id: number, trueVotes: number, falseVotes: number) {
    return apiClient.patch<News>(`/news/${id}`, { trueVotes, falseVotes })
  },

  // comments APIs
  addComment(payload: Omit<Comment, 'id' | 'createdAt'>) {
    return apiClient.post<Comment>('/comments', {
      ...payload,
      createdAt: new Date().toISOString()
    })
  },
  getCommentsByNews(newsId: number) {
    return apiClient.get<Comment[]>('/comments', {
      params: { newsId, _sort: 'id', _order: 'desc' }
    })
  }
}
