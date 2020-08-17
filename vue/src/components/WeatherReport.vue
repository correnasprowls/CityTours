<template>
    <div >
      <v-card id="weather">
        <v-card-title style="color: indigo;">{{landmark.landmarkName + ' Weather'}}</v-card-title>
        <v-spacer></v-spacer>
        <div class="temperature">
          <v-card-actions>
          <div>
         <v-card-subtitle>Today's Temperature</v-card-subtitle>
          <v-card-text> High {{forecast.maxTemp}}° </v-card-text>
          <v-card-text> Low {{forecast.minTemp}}° </v-card-text>
          </div>
          <v-spacer></v-spacer>
         <v-card id="iconContainer">
           <v-card-text>{{forecast.description}}</v-card-text>
          <v-img width="100px" :src="'http://openweathermap.org/img/wn/' + forecast.icon + '@2x.png'"></v-img>
         </v-card>
         </v-card-actions>
         
        </div>
      </v-card>
    </div>
</template>

<script>

import LandmarkService from "@/services/LandmarkService";
export default {
  name: "weather-report",
  props: ["landmark"],
  data() {
    return {
      forecast: {},
    };
  },
  methods: {
    getForecast() {
      LandmarkService.getForecast(this.landmark.location)
        .then((response) => {
          if (response.status == 201) {
            this.forecast = response.data;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  created() {
    this.getForecast();
  },
};
</script>

<style>
#iconContainer {
  display: flex;
  background-color: rgba(105, 105, 105, 0.267);
  margin: 1rem;
  
}
#weather {
  background-color: rgba(137, 43, 226, 0.055);
}
</style>
