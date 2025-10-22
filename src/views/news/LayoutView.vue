<script setup lang="ts">
import { useNewsStore } from '@/stores/News'
import { storeToRefs } from 'pinia'

const store = useNewsStore()
const { news } = storeToRefs(store)
</script>

<template>
  <div v-if="news">
    <h1
      class="relative text-3xl sm:text-4xl font-extrabold text-white text-center p-4 rounded-lg mt-0 mb-0 overflow-hidden"
      :class="{
        'bg-gradient-to-r from-green-500 via-green-400 to-teal-400':
          news.trueVotes > news.falseVotes,
        'bg-gradient-to-r from-red-600 via-orange-500 to-red-400': news.falseVotes > news.trueVotes,
        'bg-gradient-to-r from-yellow-400 via-orange-300 to-yellow-200':
          news.trueVotes === news.falseVotes,
      }"
    >
      <span class="relative z-10">{{ news.topic }}</span>
      <span class="absolute inset-0 bg-black opacity-20 rounded-lg"></span>
    </h1>

    <!-- Navigation -->
   <nav class="mb-6 flex flex-wrap justify-center gap-4 bg-gradient-to-r from-[rgb(28,28,30)] to-[rgb(38,38,40)] shadow-md rounded-xl p-3">
  <RouterLink
    class="px-4 py-1.5 text-sm md:text-base rounded-lg border transition-all duration-200 shadow-sm bg-[rgb(28,28,30)]"
    :to="{ name: 'news-detail-view' }"
    :class="{
      // Active
      'text-emerald-400 border-emerald-400': news.trueVotes > news.falseVotes && $route.name === 'news-detail-view',
      'text-rose-400 border-rose-400': news.falseVotes > news.trueVotes && $route.name === 'news-detail-view',
      'text-amber-400 border-amber-400': news.trueVotes === news.falseVotes && $route.name === 'news-detail-view',
      // Inactive default
      'text-gray-300 border-gray-600': $route.name !== 'news-detail-view',
      // Hover for inactive
      'hover:text-emerald-400 hover:border-emerald-400 hover:drop-shadow-[0_0_3px_rgba(16,185,129,0.4)]': news.trueVotes > news.falseVotes && $route.name !== 'news-detail-view',
      'hover:text-rose-400 hover:border-rose-400 hover:drop-shadow-[0_0_3px_rgba(251,191,190,0.4)]': news.falseVotes > news.trueVotes && $route.name !== 'news-detail-view',
      'hover:text-amber-400 hover:border-amber-400 hover:drop-shadow-[0_0_3px_rgba(251,191,36,0.4)]': news.trueVotes === news.falseVotes && $route.name !== 'news-detail-view'
    }"
  >
    Details
  </RouterLink>

  <RouterLink
    class="px-4 py-1.5 text-sm md:text-base rounded-lg border transition-all duration-200 shadow-sm bg-[rgb(28,28,30)]"
    :to="{ name: 'news-vote-view' }"
    :class="{
      'text-emerald-400 border-emerald-400': news.trueVotes > news.falseVotes && $route.name === 'news-vote-view',
      'text-rose-400 border-rose-400': news.falseVotes > news.trueVotes && $route.name === 'news-vote-view',
      'text-amber-400 border-amber-400': news.trueVotes === news.falseVotes && $route.name === 'news-vote-view',
      'text-gray-300 border-gray-600': $route.name !== 'news-vote-view',
      'hover:text-emerald-400 hover:border-emerald-400 hover:drop-shadow-[0_0_3px_rgba(16,185,129,0.4)]': news.trueVotes > news.falseVotes && $route.name !== 'news-vote-view',
      'hover:text-rose-400 hover:border-rose-400 hover:drop-shadow-[0_0_3px_rgba(251,191,190,0.4)]': news.falseVotes > news.trueVotes && $route.name !== 'news-vote-view',
      'hover:text-amber-400 hover:border-amber-400 hover:drop-shadow-[0_0_3px_rgba(251,191,36,0.4)]': news.trueVotes === news.falseVotes && $route.name !== 'news-vote-view'
    }"
  >
    Vote
  </RouterLink>

  <RouterLink
    class="px-4 py-1.5 text-sm md:text-base rounded-lg border transition-all duration-200 shadow-sm bg-[rgb(28,28,30)]"
    :to="{ name: 'news-comment-view' }"
    :class="{
      'text-emerald-400 border-emerald-400': news.trueVotes > news.falseVotes && $route.name === 'news-comment-view',
      'text-rose-400 border-rose-400': news.falseVotes > news.trueVotes && $route.name === 'news-comment-view',
      'text-amber-400 border-amber-400': news.trueVotes === news.falseVotes && $route.name === 'news-comment-view',
      'text-gray-300 border-gray-600': $route.name !== 'news-comment-view',
      'hover:text-emerald-400 hover:border-emerald-400 hover:drop-shadow-[0_0_3px_rgba(16,185,129,0.4)]': news.trueVotes > news.falseVotes && $route.name !== 'news-comment-view',
      'hover:text-rose-400 hover:border-rose-400 hover:drop-shadow-[0_0_3px_rgba(251,191,190,0.4)]': news.falseVotes > news.trueVotes && $route.name !== 'news-comment-view',
      'hover:text-amber-400 hover:border-amber-400 hover:drop-shadow-[0_0_3px_rgba(251,191,36,0.4)]': news.trueVotes === news.falseVotes && $route.name !== 'news-comment-view'
    }"
  >
    Comments
  </RouterLink>
</nav>




    <RouterView :news="news" />
  </div>
</template>
