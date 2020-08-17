<template>
  <v-sheet>
    <v-sheet v-if="filteredItineraries.landmarks.length == 0">
      <v-card :style="{'background-color': filteredItineraries.color }">
        <v-card-title class="mx-auto text-h3">{{filteredItineraries.itineraryName}}</v-card-title>
      </v-card>

      <v-card-text class="text-h4">
        You have no landmarks in this itinerary yet! Add some
        <router-link :to="{ name: 'landmarkList' }">here</router-link>!
      </v-card-text>
    </v-sheet>
    <v-sheet v-if="showEmailForm">
      <v-card>
        <v-form>
          <v-text-field
            label="Email"
            type="text"
            id="email"
            class="form-control"
            v-model="itineraryEmail.email"
            required
          />
          <v-btn color="success" type="submit" @click.prevent="sendEmail">Send</v-btn>
        </v-form>
      </v-card>
    </v-sheet>
    <v-sheet v-if="filteredItineraries.landmarks.length != 0">
      <v-card v-if="showForm">
        <v-sheet width="80%" class="mx-auto mtb-5">
          <v-form v-if="showForm" class="add-landmark-name">
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
            </v-card-text>
            <v-divider></v-divider>
            <v-card></v-card>
            <v-card-actions>
              <v-btn color="success" type="submit" @click.prevent="getLocationWithCoordinates">Apply</v-btn>
              <v-spacer></v-spacer>
              <v-btn
                color="indigo accent-1"
                type="submit"
                @click.prevent="this.locationSelected = false"
              >Cancel</v-btn>
            </v-card-actions>
          </v-form>
        </v-sheet>
      </v-card>
      <v-card :style="{'background-color': filteredItineraries.color }">
        <v-card-actions>
          <v-card-title class="mx-auto text-h3">{{filteredItineraries.itineraryName}}</v-card-title>

          <v-spacer></v-spacer>
          <v-btn v-if="!showEmailForm" @click="showEmailForm = !showEmailForm">Send itinerary</v-btn>
          <v-btn v-if="!showForm" @click="showForm = !showForm">Find a route</v-btn>
        </v-card-actions>
      </v-card>
      <route-details
        v-if="locationSelected"
        :coordinates="coordinates"
        :landmarks="filteredItineraries.landmarks"
      />
      <landmark-cards
        v-for="landmark in filteredItineraries.landmarks"
        :key="landmark.landmarkId"
        :landmark="landmark"
      />

      <v-sheet width="100%"></v-sheet>
    </v-sheet>
  </v-sheet>
</template>
<script>
import LandmarkCards from "@/components/LandmarkCard";
import RouteDetails from "@/components/RouteDetails";
import LandmarkService from "@/services/LandmarkService";
import ItineraryService from "@/services/ItineraryService";
export default {
  components: {
    LandmarkCards,
    RouteDetails
  },
  data() {
    return {
      activeItinerary: {
        landmarks: []
      },
      itineraryEmail: {
        email: "",
        itinerary: this.filteredItineraries
      },
      location: {},
      coordinates: {},
      locationSelected: false,
      showForm: false,
      showEmailForm: false
    };
  },
  methods: {
    getLocationWithCoordinates() {
      LandmarkService.getLocationWithCoordinates(this.location).then(
        response => {
          this.coordinates = response.data.coordinates;

          this.locationSelected = true;
          this.showForm = false;
        }
      );
    },
    sendEmail() {
      this.itineraryEmail.itinerary = this.filteredItineraries;
      console.log(this.itineraryEmail);
      ItineraryService.sendItineraryEmail(this.itineraryEmail).then(
        response => {
          if (response.status === 201) {
            alert("You should receive your itinerary via email shortly.");
            this.itineraryEmail.email = "";
            this.showEmailForm = !this.showEmailForm;
          }
        }
      );
    }
  },
  computed: {
    filteredItineraries() {
      return this.$store.state.itineraries.find(itinerary => {
        return itinerary.itineraryId == this.$route.params.id;
      });
    }
  }
};
</script>
<style>
</style>


