<template>
<v-card class="pt-7 mt-2" width="100%" :style="{ 'background-color': itinerary.color }">
  <v-card tile flat  height="6rem" class=" mx-auto">
    <v-list-item>
      <v-list-item-content>
        <router-link
          :to="{ name: 'itineraryDetails', params: {id:itinerary.itineraryId} }"
          type="div"
          width="100%"
        >
          <v-list-item-title class="text-center">{{itinerary.itineraryName}}</v-list-item-title>
        </router-link>
        <v-list-item-subtitle>
          <v-card-actions>
            <v-icon >mdi-lead-pencil</v-icon>
            <v-spacer></v-spacer>
            <v-icon @click="deleteItinerary" color="red darken-4">mdi-delete</v-icon>
          </v-card-actions>
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
  </v-card>
</v-card>
</template>

<script>
import itineraryService from "@/services/ItineraryService";

export default {
  name: "itinerary-card",
  props: ["itinerary"],
  data: () => ({
    show: false,
    landmarks: [],
  }),
  methods: {
    deleteItinerary() {
      if (confirm("Are you sure you want to delete this itinerary?")) {
        itineraryService
          .deleteItinerary(this.itinerary.itineraryId)
          .then((response) => {
            if (response.status == 200) {
              alert("Itinerary successfully deleted!");
              this.$store.commit('DELETE_ITINERARY', this.itinerary.itineraryId);
              if(this.$route.name == "itineraryDetails" && this.$route.params.id == this.itinerary.itineraryId) {
                this.$router.push('/');
              }
            }
          });
      }
    },
  },
};
</script>

<style>
a {
  text-decoration: none;
}
</style>