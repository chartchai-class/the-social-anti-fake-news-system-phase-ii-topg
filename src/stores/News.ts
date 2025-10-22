import type { NewsState, News } from '@/types'
import { defineStore } from 'pinia'

export const useNewsStore = defineStore('news', {
  state: (): NewsState => ({
    news: null,
  }),
  actions: {
    setNews(news: News): void {
      this.news = news
    },
  },
})
