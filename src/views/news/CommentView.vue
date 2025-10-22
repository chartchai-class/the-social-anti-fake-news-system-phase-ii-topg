<script setup lang="ts">
import { ref, computed, toRefs, onMounted } from 'vue'
import { type News } from '@/types'

const props = defineProps<{ news: News; id: string }>()
const { news } = toRefs(props)

const filter = ref<'all' | 'true' | 'false'>('all')
const votedKey = computed(() => `voted:${news.value?.id}`)
const hasVoted = ref(false)

onMounted(() => {
  hasVoted.value = localStorage.getItem(votedKey.value) === '1'
  if (!news.value?.comments) news.value!.comments = []
})

const filteredComments = computed(() => {
  if (!news.value?.comments) return []
  if (filter.value === 'all') return news.value.comments
  return news.value.comments.filter(c => c.vote === filter.value)
})
</script>

<template>
  <div class="min-h-screen bg-[rgb(38,38,40)] flex justify-center items-start pt-6 pb-6">
    <div class="w-[80%] max-w-4xl bg-gray-900 shadow-md rounded-2xl p-6 border border-gray-800 mx-auto">
      
      <!-- Header -->
      <h2 class="text-xl font-bold text-white mb-4">Comments</h2>

      <!-- Filter buttons -->
      <div class="flex gap-2 mb-4">
        <button
          class="px-3 py-1 border rounded-lg"
          :class="{ 'font-semibold bg-gray-700 text-white': filter==='all' }"
          @click="filter='all'"
        >
          All
        </button>
        <button
          class="px-3 py-1 border rounded-lg"
          :class="{ 'font-semibold bg-gray-700 text-white': filter==='true' }"
          @click="filter='true'"
        >
          True
        </button>
        <button
          class="px-3 py-1 border rounded-lg"
          :class="{ 'font-semibold bg-gray-700 text-white': filter==='false' }"
          @click="filter='false'"
        >
          False
        </button>
      </div>

      <!-- Comment list -->
      <ul class="space-y-3">
        <li v-for="c in filteredComments" :key="c.id" class="flex items-start gap-3 border-b border-gray-700 pb-3">
          <!-- Vote badge -->
          <span
            :class="c.vote==='true' ? 'bg-green-700 text-green-100' : 'bg-red-700 text-red-100'"
            class="font-bold px-2 py-1 rounded-full text-sm flex-shrink-0"
          >
            {{ c.vote === 'true' ? 'True' : 'False' }}
          </span>

          <!-- Comment content -->
          <div class="flex-1">
            <p class="text-gray-200">{{ c.text }}</p>
            <img
              v-if="c.imageUrl"
              :src="c.imageUrl"
              alt="Comment image"
              class="mt-2 max-h-40 rounded-lg object-cover border border-gray-700"
            />
            <span class="text-xs text-gray-400 mt-1 block">{{ new Date(c.createdAt).toLocaleString() }}</span>
          </div>
        </li>
      </ul>

    </div>
  </div>
</template>
