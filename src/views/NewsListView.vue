<script setup lang="ts">
import NewsCard from '@/components/NewsCard.vue'
import { type News } from '@/types'
import { ref, computed, watchEffect } from 'vue'
import { useRouter } from 'vue-router'
import NewsService from '@/services/NewsService'

const newsList = ref<News[] | null>(null)

const props = defineProps({
  page: {
    type: Number,
    required: true,
  },
  size: {
    type: Number,
    required: true,
  },
})

const page = computed(() => props.page)
const selectedSize = ref(props.size)

const router = useRouter()

function changeSize() {
  router.push({
    name: 'news-list-view',
    query: {
      page: 1,
      size: selectedSize.value,
      status: selectedType.value,
    },
  })
}

//News type selector
const selectedType = ref((router.currentRoute.value.query.status as string) || 'all')

//Fetch ALL news once (big limit)
watchEffect(() => {
  NewsService.getNews(9999, 1)
    .then((response) => {
      newsList.value = response.data
    })
    .catch((error) => {
      console.error('There was an error fetching news!', error)
    })
})

//Filter based on votes
const filteredNews = computed(() => {
  if (!newsList.value) return []

  switch (selectedType.value) {
    case 'True':
      return newsList.value.filter((n) => n.trueVotes > n.falseVotes)
    case 'False':
      return newsList.value.filter((n) => n.falseVotes > n.trueVotes)
    case 'Pending':
      return newsList.value.filter((n) => n.trueVotes === n.falseVotes)
    default:
      return newsList.value
  }
})

//Paginate after filtering
const paginatedNews = computed(() => {
  if (!filteredNews.value) return []
  const start = (page.value - 1) * selectedSize.value
  const end = start + selectedSize.value
  return filteredNews.value.slice(start, end)
})

//Has next page
const hasNextPage = computed(() => {
  const totalPages = Math.ceil(filteredNews.value.length / selectedSize.value)
  return page.value < totalPages
})
</script>

<template>
  <div class="flex flex-col min-h-screen bg-black text-white">
    <!-- Top controls bar -->
    <div
      class="p-4 bg-gradient-to-r from-[rgb(28,28,30)] to-[rgb(38,38,40)] shadow-md rounded-b-xl"
    >
      <div class="flex flex-wrap justify-center gap-6 md:gap-8 items-center">
        <!-- Add News button -->
        <button
          class="px-4 py-1.5 text-sm md:text-base bg-[rgb(28,28,30)] text-white rounded-lg hover:border-gray-400 border border-gray-600 focus:outline-none focus:ring-2 focus:ring-blue-400 transition shadow-sm"
        >
          Add News
        </button>

        <!-- Divider -->
        <div class="border-l border-gray-600 h-6 hidden md:block"></div>

        <!-- News type selector -->
        <div class="flex items-center gap-2">
          <label for="status" class="text-sm md:text-base font-semibold text-gray-300"
            >News type:</label
          >
          <select
            id="status"
            v-model="selectedType"
            @change="
              router.push({
                name: 'news-list-view',
                query: { page: 1, size: selectedSize, status: selectedType },
              })
            "
            class="border border-gray-600 rounded-lg px-3 py-1.5 text-sm md:text-base bg-[rgb(28,28,30)] text-white hover:border-gray-400 transition shadow-sm"
          >
            <option value="all">All news</option>
            <option value="True">True news</option>
            <option value="False">Fake news</option>
            <option value="Pending">Pending news</option>
          </select>
        </div>

        <!-- Divider -->
        <div class="border-l border-gray-600 h-6 hidden md:block"></div>

        <!-- Page size selector -->
        <div class="flex items-center gap-2">
          <label for="size" class="text-sm md:text-base font-semibold text-gray-300"
            >News per page:</label
          >
          <select
            id="size"
            v-model.number="selectedSize"
            @change="changeSize"
            class="border border-gray-600 rounded-lg px-3 py-1.5 text-sm md:text-base bg-[rgb(28,28,30)] text-white hover:border-gray-400 transition shadow-sm"
          >
            <option :value="3">3</option>
            <option :value="6">6</option>
            <option :value="12">12</option>
            <option :value="24">24</option>
          </select>
        </div>
      </div>
    </div>

    <!-- Main content area -->
    <div class="flex-1 p-6">
      <!-- News cards -->
      <div class="flex flex-wrap justify-center gap-4 mb-6">
        <NewsCard v-for="news in paginatedNews" :key="news.id" :news="news" />
      </div>

      <!-- Pagination at bottom -->
      <div class="flex justify-center gap-8 text-base">
        <RouterLink
          id="page-prev"
          :to="{
            name: 'news-list-view',
            query: { page: page - 1, size: selectedSize, status: selectedType },
          }"
          rel="prev"
          class="px-4 py-2 bg-gray-800 text-white rounded-full hover:bg-gray-600 hover:-translate-x-2 transition-all duration-200"
          v-if="page != 1"
        >
          &#60; Prev Page
        </RouterLink>

        <RouterLink
          id="page-next"
          :to="{
            name: 'news-list-view',
            query: { page: page + 1, size: selectedSize, status: selectedType },
          }"
          rel="next"
          class="px-4 py-2 bg-gray-800 text-white rounded-full hover:bg-gray-600 hover:translate-x-2 transition-all duration-200"
          v-if="hasNextPage"
        >
          Next Page &#62;
        </RouterLink>
      </div>
    </div>
  </div>
</template>
