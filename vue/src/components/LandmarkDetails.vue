<template>
  <div>
    <v-card width="80%" class="mx-auto mt-5">
      <v-card-title>
        <h1>{{landmark.landmarkName}}</h1>
      </v-card-title>
      <v-card-subtitle
        class="mt-5"
      >{{landmark.location.addressLineOne}} {{landmark.location.addressLineTwo}} {{landmark.location.cityName}}, {{landmark.location.stateCode}} {{landmark.location.zipCode}}</v-card-subtitle>
      <v-spacer></v-spacer>
      <v-card-title>
        <h4>Description</h4>
      </v-card-title>
      <v-spacer></v-spacer>
      <v-card width="85%" class="mx-auto mt-5">
        <v-card-text>{{landmark.landmarkDescription}}</v-card-text>
      </v-card>
      <v-card-actions>
        <v-btn
          v-if="$route.name !== 'itineraryDetails'"
          text
          color="deep-purple accent-4"
          @click="flipDialogSetLandmark"
        >ADD TO ITINERARY</v-btn>
        <v-spacer></v-spacer>
        <v-btn icon>
          <v-icon>mdi-heart</v-icon>
        </v-btn>
      </v-card-actions>
      <v-card-actions>
        <v-card id="landmarkDetailsMap">
          <our-map :coordinates="landmark.location.coordinates" />
        </v-card>
        <v-spacer></v-spacer>
        <v-sheet id="landmarkDetailsWeather">
        <weather-report :landmark="landmark" />
        </v-sheet>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import LandmarkService from "@/services/LandmarkService";
import WeatherReport from "@/components/WeatherReport";
import OurMap from "@/components/OurMap";

export default {
  name: "landmark-details",
  data() {
    return {
      landmark: {
        location: {
          coordinates: {},
        },
      },
      errorMsg: "",
    };
  },
  created() {
    LandmarkService.getLandmark(this.$route.params.id)
      .then((request) => {
        if (request.status == 200) {
          this.landmark = request.data;
        }
      })
      .catch((error) => {
        if (error.response) {
          this.errorMsg =
            "Error generating landmark details. Received " +
            error.response.statusText;
          alert(this.errorMsg);
        } else if (error.request) {
          this.errorMsg = "Server could not be reached.";
          alert(this.errorMsg);
        } else {
          this.errorMsg = "Request could not be created.";
          alert(this.errorMsg);
        }
      });
  },
  methods: {
    flipDialogSetLandmark() {
      this.$store.commit("SET_CURRENT_LANDMARK", this.landmark);
      this.$store.commit("FLIP_ITINERARY_DIALOG");
    },
  },
  components: {
    OurMap,
    WeatherReport,
  },
};
</script>
<style>
#landmarkDetailsWeather{
margin: 1rem;
}
#landmarkDetailsMap {
  width: 40%;
  margin: 1rem;
}
</style>