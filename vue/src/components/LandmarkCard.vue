<template>
  <v-sheet>
    <v-card max-width="80%" class="mt-6 mx-auto">
      <v-sheet @click="$router.push(`/landmarks/${landmark.landmarkId}`)">
        <v-list-item>
          <v-list-item-avatar color="grey"></v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title class="headline">{{landmark.landmarkName}}</v-list-item-title>
            <v-list-item-subtitle>{{landmark.location.addressLineOne}} {{landmark.location.cityName}}, {{landmark.location.stateCode}} {{landmark.location.zipCode}}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-img src="https://cdn.vuetifyjs.com/images/cards/mountain.jpg" height="194"></v-img>

        <v-card-text>{{landmark.landmarkDescription}}</v-card-text>
      </v-sheet>
      <v-card-actions>
        <v-btn
          v-if="$route.name !== 'itineraryDetails'"
          text
          color="deep-purple accent-4"
          @click="flipDialogSetLandmark"
        >ADD TO ITINERARY</v-btn>
        <v-spacer></v-spacer>
        <v-btn v-for="itinerary in filteredItineraries" :key="itinerary.itineraryId"  :title="itinerary.name" icon>
          <v-icon :style="{'color': itinerary.color }">mdi-heart</v-icon>
        </v-btn>
        <v-btn icon v-if="$route.name !== 'itineraryDetails'">
          <v-icon>mdi-share-variant</v-icon>
        </v-btn>
        <v-icon
          @click="deleteLandmarkFromItinerary"
          color="red darken-4"
          v-if="$route.name == 'itineraryDetails'"
        >mdi-delete</v-icon>
        <v-btn icon @click="show = !show">
          <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
        </v-btn>
      </v-card-actions>
      <v-expand-transition>
        <div v-show="show">
          <v-divider></v-divider>
          <v-card-actions id="landmarkDetailContainer">
            <v-card id="cardMap">
              
            </v-card>
            <v-spacer></v-spacer>
            <weather-report id="weatherDetails" :landmark="landmark" />
          </v-card-actions>
        </div>
      </v-expand-transition>
    </v-card>
  </v-sheet>
</template>

<script>
import WeatherReport from "@/components/WeatherReport";
import ItineraryService from "@/services/ItineraryService";
export default {
  name: "landmark-card",
  props: ["landmark"],
  data: () => ({
    show: false,
    itinerarySelected: false,
    forecast: {},
  }),
  computed: {
        filteredItineraries() {
      return this.$store.state.itineraries.filter((i) => {
        if (i.landmarks != null) {
          return i.landmarks.find((l) => {
            return l.landmarkId == this.landmark.landmarkId;
          });
        } else {
          return false;
        }
      });
    },
  },
  methods: {
    deleteLandmarkFromItinerary() {
      ItineraryService.deleteLandmarkFromItinerary(
        this.landmark.landmarkId,
        this.$route.params.id
      ).then((response) => {
        if (response.status === 200) {
          alert("Landmark removed.");
          this.$store.commit("DELETE_LANDMARK_FROM_ITINERARY", {
            landmarkId: this.landmark.landmarkId,
            itineraryId: this.$route.params.id,
          });
        }
      });
    },
    flipDialogSetLandmark() {
      this.$store.commit("SET_CURRENT_LANDMARK", this.landmark);
      this.$store.commit("FLIP_ITINERARY_DIALOG");
    },
  },

  components: {
    WeatherReport,
  },
};
</script>

<style>
#weatherDetails {
  width: 60%;
  margin: 1rem;
}

</style>