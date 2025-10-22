<script setup lang="ts">
import { ref, onMounted, defineProps } from 'vue'
import { type News } from '@/types'
import NewsService from '@/services/NewsService'

const news = ref<News | null>(null)

const props = defineProps({
  id: {
    type: String,
    required: true,
  },
})

onMounted(() => {
  NewsService.getNewsItem(parseInt(props.id))
    .then((response) => {
      news.value = response.data
    })
    .catch((error) => {
      console.error('There was an error!', error)
    })
})

async function vote(isTrue: boolean) {
  if (!news.value) return

  if (isTrue) {
    news.value.trueVotes++
  } else {
    news.value.falseVotes++
  }

  try {
    await NewsService.voteNews(
      news.value.id,
      news.value.trueVotes,
      news.value.falseVotes
    )
  } catch (error) {
    console.error('Vote failed', error)
  }
}
</script>

<template>
  <div v-if="news">
    <h1>{{ news.topic }}</h1>
    <p>Reported by {{ news.reporter }}</p>
    <p>{{ news.date }} @ {{ news.time }}</p>
    <p>{{ news.short_detail }}</p>

    <!-- Voting -->
    <div class="mt-4">
      <button @click="vote(true)" class="px-4 py-2 bg-green-500 text-white rounded mr-2">
        Vote True
      </button>
      <button @click="vote(false)" class="px-4 py-2 bg-red-500 text-white rounded">
        Vote False
      </button>
    </div>

    <!-- Tally -->
    <p class="mt-4">True Votes: {{ news.trueVotes }}</p>
    <p>False Votes: {{ news.falseVotes }}</p>
    <p>
      Verdict:
      <strong>
        {{ news.trueVotes > news.falseVotes ? "Mostly True" : "Mostly False" }}
      </strong>
    </p>
  </div>
</template>
