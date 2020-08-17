<template>
<div>
    <div class="loader" v-if="isLoading">
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
  <div class="block"></div>
</div>
  
 
  <v-sheet width="90%" class="mx-auto" v-if="!isLoading">
    <v-card-title>
      <h1>Landmarks</h1>

      <v-spacer></v-spacer>
      <v-card-actions>
        <v-btn @click="showForm = !showForm" v-if="!showForm">Filter By Distance</v-btn>
      </v-card-actions>
    </v-card-title>

    <v-card v-if="showForm">
      <v-sheet width="80%" class="mx-auto mtb-5">
        <v-form class="add-landmark-name">
          <v-card-text id="add-landmark-name" class="text-center">
            <h1 class="h3 mb-3 font-weight-normal">Enter Current Address</h1>

            <v-text-field
              label="Address Line One"
              type="text"
              id="address-line-one"
              class="form-control"
              v-model="location.addressLineOne"
              required
            />

            <v-select
              label="State"
              :items="Object.keys(this.$store.state.states)"
              id="state-code"
              class="form-control"
              v-model="location.stateCode"
              required
            />
            <v-text-field
              label="City"
              type="text"
              id="city-name"
              class="form-control"
              v-model="location.cityName"
              required
            />
            <v-text-field
              label="Zip Code"
              type="text"
              id="zipcode"
              class="form-control"
              v-model="location.zipCode"
              required
            />

            <v-select
              item-text="text"
              item-value="value"
              label="Distance (miles)"
              v-model="distance"
              :items="distances"
              required
            />
          </v-card-text>
          <v-divider></v-divider>
          <v-card-actions>
            <v-btn color="success" type="submit" @click.prevent="updateLandmarks">Apply</v-btn>
            <v-spacer></v-spacer>
            <v-btn color="indigo accent-1" type="submit" @click.prevent="cancel">Cancel</v-btn>
          </v-card-actions>
        </v-form>
      </v-sheet>
    </v-card>
    <v-card-title v-if="distance != 1000000">{{distanceFilter}}</v-card-title>
    <v-banner v-if="landmarks.length == 0">No landmarks to show</v-banner>
    <landmark-card v-for="landmark in landmarks" :key="landmark.id" :landmark="landmark" />
  </v-sheet>
</div>
</template>

<script>
import LandmarkCard from "@/components/LandmarkCard";
import LandmarkService from "@/services/LandmarkService";

export default {
  name: "landmark-list",
  data: () => ({
    showForm: false,
    landmarks: [],
    distance: 1000000,
    isLoading: false,
    defaultLocation: {
      cityName: "Columbus",
      stateCode: "OH",
      addressLineOne: "1 W Nationwide Blvd",
      zipCode: "43215",
    },
    location: {
      coordinates: {},
    },
    distances: [
      {
        text: "",
        value: 1000000,
      },
      {
        text: "1",
        value: 1,
      },
      {
        text: "5",
        value: 5,
      },
      {
        text: "10",
        value: 10,
      },
      {
        text: "15",
        value: 15,
      },
      {
        text: "20",
        value: 20,
      },
    ],
  }),
  components: {
    LandmarkCard
  },
  computed: {
    distanceFilter() {
      this.updateLandmarks;
      return `Landmarks within ${this.distance} miles`;
    },
  },
  methods: {
    updateLandmarks() {
      this.$store.commit("SET_LOCATION", this.location);
      LandmarkService.getLandmarksInRange(this.location, this.distance).then(
        (response) => {
          this.showForm = false;
          this.landmarks = response.data;
        }
      );
    },
    cancel() {
      this.location = { coordinates: {} };
      this.showForm = false;
    },
    flipDialog() {
      this.$store.commit("FLIP_ITINERARY_DIALOG");
    },
  },
  created() {
    this.isLoading = true;    
    this.location = this.$store.state.userLocation;
    LandmarkService.getLandmarksInRange(this.defaultLocation, 1000000).then(
      (response) => {
        this.landmarks = response.data;
        this.isLoading = false;
      }
    );
  },
};
</script>

<style>
.loader {
  width: 80px;
  height: 100px;
  margin: 100px auto;
  margin-top: calc(100vh / 2 - 50px);
}

.block {
  position: relative;
  box-sizing: border-box;
  float: left;
  margin: 0 10px 10px 0;
  width: 12px;
  height: 12px;
  border-radius: 3px;
  background: rgb(104, 4, 185);
}

.block:nth-child(4n+1) { animation: wave 2s ease .0s infinite; }
.block:nth-child(4n+2) { animation: wave 2s ease .2s infinite; }
.block:nth-child(4n+3) { animation: wave 2s ease .4s infinite; }
.block:nth-child(4n+4) { animation: wave 2s ease .6s infinite; margin-right: 0; }

@keyframes wave {
  0%   { top: 0;     opacity: 1; }
  50%  { top: 30px;  opacity: .2; }
  100% { top: 0;     opacity: 1; }
}
</style>