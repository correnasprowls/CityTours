<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" max-width="290">
      <v-card class="pr-3 pl-3">
        <v-card-title class="headline">Select Itinerary</v-card-title>
        <v-card
          v-for="itinerary in filteredItineraries"
          :key="itinerary.itineraryId"
          @click="selectItinerary(itinerary.itineraryId)"
          class="mt-3"
          :style="{'background-color': itinerary.color}"
        >
          <v-card-text style="font-weight: bold;">{{itinerary.itineraryName}}</v-card-text>
        </v-card>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            v-if="selectedItenerary"
            color="green darken-1"
            text
            @click="addLandmarkToItinerary"
          >Add</v-btn>

          <v-btn color="red darken-1" text @click="flipDialog">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import ItineraryService from "@/services/ItineraryService";
export default {
  name: "itinerary-select",
  data() {
    return {
      dialog: true,
      selectedItenerary: false,
    };
  },
  computed: {
    filteredItineraries() {
      const activeLandmark = this.$store.state.selectedLandmark.landmarkId;
      return this.$store.state.itineraries.filter((i) => {
        if (i.landmarks != null) {
          return !i.landmarks.find((l) => {
            return l.landmarkId == activeLandmark;
          });
        } else {
          return false;
        }
      });
    },
  },
  methods: {
    flipDialog() {
      this.$store.commit("FLIP_ITINERARY_DIALOG");
    },
    selectItinerary(itineraryId) {
      this.selectedItenerary = true;
      this.$store.commit("SET_SELECTED_ITINERARY", itineraryId);
    },
    addLandmarkToItinerary() {
      ItineraryService.addLandmarkToItinerary(
        this.$store.state.selectedLandmark.landmarkId,
        this.$store.state.selectedItinerary
      ).then((response) => {
        if (response.status == 201) {
          this.$store.commit("ADD_LANDMARK_TO_ITINERARY", {
            landmark: this.$store.state.selectedLandmark,
            itineraryId: this.$store.state.selectedItinerary,
          });
          this.$store.commit("SET_SELECTED_ITINERARY", "");
          this.$store.commit("FLIP_ITINERARY_DIALOG");
        }
      });
    },
  },
};
</script>