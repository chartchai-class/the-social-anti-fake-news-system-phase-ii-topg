<script setup lang="ts">
import { ref, computed, toRefs, onMounted } from 'vue'
import { type News } from '@/types'
import NewsService from '@/services/NewsService'

const props = defineProps<{ news: News; id: string }>()
const { news } = toRefs(props)

const commentText = ref('')
const filter = ref<'all' | 'true' | 'false'>('all')

const votedKey = computed(() => `voted:${news.value?.id}`)
const hasVoted = ref(false)
const selectedFile = ref<File | null>(null)

onMounted(() => {
  hasVoted.value = localStorage.getItem(votedKey.value) === '1'
  if (!news.value?.comments) news.value!.comments = []
})

const filteredComments = computed(() => {
  if (!news.value?.comments) return []
  if (filter.value === 'all') return news.value.comments
  return news.value.comments.filter(c => c.vote === filter.value)
})

async function uploadImage(file: File): Promise<string> {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.onload = () => resolve(reader.result as string)
    reader.onerror = reject
    reader.readAsDataURL(file)
  })
}

async function submitComment(vote: 'true' | 'false') {
  if (!news.value || hasVoted.value || !commentText.value.trim()) return

  let imageUrl: string | undefined
  if (selectedFile.value) {
    imageUrl = await uploadImage(selectedFile.value)
  }

  const res = await NewsService.addComment({
    newsId: news.value.id,
    text: commentText.value.trim(),
    vote,
    imageUrl
  })

  news.value.comments!.unshift(res.data)

  if (vote === 'true') news.value.trueVotes++
  else news.value.falseVotes++
  await NewsService.voteNews(news.value.id, news.value.trueVotes, news.value.falseVotes)

  localStorage.setItem(votedKey.value, '1')
  hasVoted.value = true
  commentText.value = ''
  selectedFile.value = null
}

function onFileSelected(event: Event) {
  const target = event.target as HTMLInputElement
  if (target.files && target.files[0]) {
    selectedFile.value = target.files[0]
  }
}
</script>

<template>
  <div class="min-h-screen bg-[rgb(38,38,40)] flex justify-center items-start py-6">
    <div class="w-[80%] max-w-4xl bg-gray-900 shadow-md rounded-2xl p-6 border border-gray-800">
      
      <!-- Header -->
      <h2 class="text-xl font-bold text-white mb-4">Comment & Vote</h2>

      <!-- Comment input -->
      <textarea
        v-model="commentText"
        placeholder="Add your comment..."
        rows="3"
        class="w-full border border-gray-700 rounded-lg p-3 mb-2 bg-gray-800 text-gray-200 focus:ring-2 focus:ring-blue-400 focus:outline-none"
      />

      <input
        type="file"
        accept="image/*"
        @change="onFileSelected"
        class="mb-4 text-gray-200"
      />

      <!-- Submit buttons -->
      <div class="flex flex-col sm:flex-row items-start sm:items-center gap-3 mb-4">
        <button
          @click="submitComment('true')"
          :disabled="hasVoted || !commentText.trim()"
          class="px-4 py-2 bg-green-600 text-white rounded-lg shadow hover:bg-green-700 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          Submit as True
        </button>
        <button
          @click="submitComment('false')"
          :disabled="hasVoted || !commentText.trim()"
          class="px-4 py-2 bg-red-600 text-white rounded-lg shadow hover:bg-red-700 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          Submit as False
        </button>
        <span v-if="hasVoted" class="text-sm text-gray-400 mt-2 sm:mt-0">You’ve already voted on this news.</span>
      </div>

      <!-- Vote tally -->
<div class="bg-gray-800 rounded-lg p-4 text-sm mb-6">
  <p class="text-green-400 font-semibold">True Votes: {{ news.trueVotes }}</p>
  <p class="text-red-400 font-semibold">False Votes: {{ news.falseVotes }}</p>
  <p class="mt-2">
    <span class="text-white font-semibold">Verdict:</span>
    <span
      class="font-bold"
      :class="news.trueVotes > news.falseVotes ? 'text-green-400' : 'text-red-400'"
    >
      {{ news.trueVotes > news.falseVotes ? 'Mostly True' : 'Mostly False' }}
    </span>
  </p>
</div>


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
