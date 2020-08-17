<template>
  <v-app id="inspire">
     <itinerary-select v-if="$store.state.showItineraryDialog" />
    <v-navigation-drawer
      v-if="$route.name !== 'login' && $route.name !== 'register'"
      v-model="drawer"
      app
    >
      <v-list dense>
        <v-list-item link>
          <v-list-item-action>
            <v-icon>mdi-home</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>
              <router-link style="color: inherit" :to="{ name: 'home' }">Home</router-link>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link>
          <v-list-item-action>
            <v-icon>mdi-account</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>
              <router-link style="color: inherit" :to="{ name: 'login' }">Login</router-link>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link>
          <v-list-item-action>
            <v-icon>mdi-account-arrow-right</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>
              <router-link style="color: inherit" :to="{ name: 'logout' }">Logout</router-link>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link>
          <v-list-item-action>
            <v-icon>mdi-office-building</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>
              <router-link style="color: inherit" :to="{ name: 'landmarkList' }">View Landmarks</router-link>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link>
          <v-list-item-action>
            <v-icon>mdi-plus</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>
              <router-link style="color: inherit" :to="{ name: 'addLandmark' }">Add Landmark</router-link>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="itineraryFormShow = !itineraryFormShow" link>
          <v-list-item-action>
            <v-icon v-if="!itineraryFormShow">mdi-plus</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title v-if="!itineraryFormShow">Add Itinerary</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-card outlined v-if="itineraryFormShow">
              <v-form class="pl-3 pr-3">
                <v-text-field
                  label="Itinerary Name"
                  type="text"
                  id="name"
                  class="form-control"
                  v-model="itinerary.itineraryName"
                  required
                  autofocus
                />
                <v-icon>mdi-palette</v-icon>
                <v-color-picker v-model="itinerary.color" class="ma-2" show-swatches  :hide-inputs="hideColorStuff" :hide-canvas="hideColorStuff" swatches-max-height="100px"></v-color-picker>
                <v-card-actions>
                <v-icon @click="createItinerary"  color="indigo accent-1">mdi-book-check</v-icon>
                <v-spacer></v-spacer>
                <v-icon @click="resetItinerary" color="red darken-4">mdi-alpha-x-box</v-icon>
                </v-card-actions>
              </v-form>
            </v-card>
        <v-list-item link>
          <v-list-item-action>
            <v-icon>mdi-calendar-blank-multiple</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title @click="showItineraries = !showItineraries">Itineraries</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <itinerary-list v-show="showItineraries"/>
    </v-navigation-drawer>

    <v-app-bar app color="indigo" dark >
      <v-app-bar-nav-icon
        v-if="$route.name !== 'login' && $route.name !== 'register'"
        @click.stop="drawer = !drawer"
      ></v-app-bar-nav-icon>
      <v-toolbar-title class="text-h2">City Tours</v-toolbar-title>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
    <v-footer color="indigo" app>
      <span class="white--text">&copy;>City Tours {{ new Date().getFullYear() }}</span>
    </v-footer>
  </v-app>
</template>

<script>
import ItineraryList from "@/components/ItineraryList";
import ItineraryService from "@/services/ItineraryService";
import ItinerarySelect from "@/components/ItinerarySelect";

export default {
  name: "App",
  props: {
    source: String
  },
  components: {
    ItineraryList,
    ItinerarySelect
  },
  computed: {
  },
  methods: {
    resetItinerary() {
       this.itineraryFormShow = !this.itineraryFormShow;
        this.itinerary.itineraryName = '';
    },
    createItinerary() {
      ItineraryService.createItineraries(this.itinerary)
      .then( response => {
        if (response.status === 201) {
          response.data.landmarks = [];
          this.$store.state.itineraries.unshift(response.data);
         this.itineraryFormShow = !this.itineraryFormShow;
        this.itinerary.itineraryName = '';
        }
        
      })
    },
    
  },
  data: () => ({
    drawer: null,
    show: false,
    showItineraries: false,
    itineraryFormShow: false,
    hideColorStuff: true,
    itinerary: {
      itineraryName: '',
      color: '',
      landmarks: []
    }
  })
};
</script>
<style scoped>
h1 {
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  font-size: 40px;
}

a {
  text-decoration: none;
  color: black;
}
</style>