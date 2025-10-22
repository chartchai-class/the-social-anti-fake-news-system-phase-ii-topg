<script setup lang="ts">
import { type News } from '@/types'
import { computed } from 'vue'

const props = defineProps<{
  news: News
}>()

// Compute status from votes
const status = computed(() => {
  if (props.news.trueVotes > props.news.falseVotes) return 'True'
  if (props.news.falseVotes > props.news.trueVotes) return 'False'
  return 'Pending'
})

// Compute total votes and percentages
const totalVotes = computed(() => props.news.trueVotes + props.news.falseVotes)
const truePercentage = computed(() =>
  totalVotes.value ? (props.news.trueVotes / totalVotes.value) * 100 : 0,
)
const falsePercentage = computed(() =>
  totalVotes.value ? (props.news.falseVotes / totalVotes.value) * 100 : 0,
)
</script>

<template>
  <RouterLink class="no-underline" :to="{ name: 'news-detail-view', params: { id: news.id } }">
    <div
      class="cursor-pointer w-[360px] h-[480px] rounded-2xl hover:scale-[1.03] hover:shadow-xl transition-all duration-400 flex flex-col justify-between"
      :class="{
        'bg-gradient-to-br from-[rgba(10,10,10,0.95)] to-emerald-900 hover:to-emerald-700':
          status === 'True',
        'bg-gradient-to-br from-[rgba(10,10,10,0.95)] to-rose-900 hover:to-rose-700':
          status === 'False',
        'bg-gradient-to-br from-[rgba(10,10,10,0.95)] to-yellow-700 hover:to-yellow-500':
          status === 'Pending',
      }"
    >
      <!-- News Image -->
      <div v-if="props.news.imageUrl" class="w-full h-[180px] overflow-hidden rounded-t-2xl">
        <img :src="props.news.imageUrl" alt="news image" class="w-full h-full object-cover" />
      </div>

      <div class="p-4 flex flex-col justify-between flex-1">
        <!-- Title -->
        <div class="flex-none mb-1">
          <h2 class="text-xl font-bold break-words text-white min-h-[40px]">
            {{ news.topic }}
          </h2>
        </div>

        <!-- Description -->
        <div class="flex-none mt-2 overflow-auto min-h-[60px] text-left">
          <p class="text-base text-white/90 break-words">
            {{ news.short_detail }}
          </p>
        </div>

        <!-- Status -->
        <div class="flex-none mb-2 mt-2">
          <span
            class="inline-block px-4 py-1 rounded-full font-semibold text-sm text-white/90"
            :class="{
              'bg-emerald-600': status === 'True',
              'bg-rose-600': status === 'False',
              'bg-amber-500 text-gray-900': status === 'Pending',
            }"
          >
            Status: {{ status }}
          </span>
        </div>

        <!-- Vote Ratio Bar -->
        <div class="flex flex-col gap-1 mt-2">
          <div class="w-full h-4 bg-white/20 rounded-full overflow-hidden flex">
            <div
              class="h-4 rounded-l-full"
              :style="{
                width: truePercentage + '%',
                backgroundColor: '#22C55E', // green for True
              }"
            ></div>
            <div
              class="h-4 rounded-r-full"
              :style="{
                width: falsePercentage + '%',
                backgroundColor: '#EF4444', // red for False
              }"
            ></div>
          </div>
          <div class="flex justify-between text-sm text-white drop-shadow-sm mt-1">
            <span>True: {{ news.trueVotes }} ({{ truePercentage.toFixed(0) }}%)</span>
            <span>False: {{ news.falseVotes }} ({{ falsePercentage.toFixed(0) }}%)</span>
          </div>
        </div>

        <!-- Reporter and Date/Time -->
        <div class="flex justify-between items-center mt-3">
          <div class="text-lg font-semibold text-white">
            {{ news.reporter }}
          </div>
          <div class="text-sm text-white/70">{{ news.date }} @ {{ news.time }}</div>
        </div>
      </div>
    </div>
  </RouterLink>
</template>
