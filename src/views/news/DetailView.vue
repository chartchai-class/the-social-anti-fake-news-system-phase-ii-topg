<script setup lang="ts">
import { defineProps } from 'vue'
import { type News } from '@/types'

const { news } = defineProps<{ news: News }>()
</script>

<template>
  <!-- Background -->
  <div class="min-h-screen bg-[rgb(38,38,40)] flex justify-center items-start pt-6 pb-6">
<!-- Card -->
<div 
  class="w-[95%] sm:w-[90%] lg:w-[80%] max-w-5xl 
         bg-gray-900 shadow-md rounded-2xl p-4 sm:p-6 
         border border-gray-800 mx-auto flex flex-col lg:flex-row gap-4 sm:gap-6"
>
  <!-- Left side: Image -->
  <div class="w-full lg:w-2/5">
    <img
      v-if="news.imageUrl"
      :src="news.imageUrl"
      alt="News Image"
      class="w-full h-48 sm:h-60 lg:h-full rounded-xl shadow-md object-cover object-center"
    />
  </div>

  <!-- Right side: Content -->
  <div class="w-full lg:w-3/5 flex flex-col overflow-y-auto">
    <!-- Title -->
    <h2 class="text-xl sm:text-2xl font-bold text-white mb-2">
      {{ news.topic }}
    </h2>

    <!-- Reporter & Date -->
    <div class="text-xs sm:text-sm text-gray-400 mb-2">
      Reported by <span class="font-medium text-gray-200">{{ news.reporter }}</span> <br />
      {{ news.date }} @ {{ news.time }}
    </div>

    <!-- Long detail -->
    <div class="flex-1 flex flex-col justify-center mb-4">
      <p class="text-gray-300 leading-relaxed sm:leading-loose text-justify text-sm sm:text-base">
        {{ news.long_detail }}
      </p>
    </div>

    <!-- Tally -->
    <div class="bg-gray-800 rounded-lg p-2 sm:p-3 text-xs sm:text-sm mt-auto">
      <p class="text-green-400">
        True Votes: <strong>{{ news.trueVotes }}</strong>
      </p>
      <p class="text-red-400">
        False Votes: <strong>{{ news.falseVotes }}</strong>
      </p>
      <p class="mt-2">
        <span class="text-white font-semibold">Verdict:</span>
        <span
          class="font-bold"
          :class="{
            'text-green-400': news.trueVotes > news.falseVotes,
            'text-red-400': news.falseVotes > news.trueVotes,
            'text-yellow-400': news.trueVotes === news.falseVotes
          }"
        >
          {{ news.trueVotes > news.falseVotes ? "Mostly True" : news.falseVotes > news.trueVotes ? "Mostly False" : "Pending" }}
        </span>
      </p>
    </div>
  </div>
</div>
  </div>
</template>
