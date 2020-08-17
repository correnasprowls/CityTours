<template>
  <v-card>
    <v-card-actions>
      <span></span>
      <v-spacer></v-spacer>
      <div id="myMap" ref="myMap"></div>
      <v-spacer></v-spacer>
    <v-btn v-if="!showDirections" @click="showDirections = !showDirections">Show Directions</v-btn>
    <v-btn v-if="showDirections" @click="showDirections = !showDirections">Hide Directions</v-btn>
    <v-spacer></v-spacer>
    <span></span>
    </v-card-actions>
    <div id="printoutPanel" v-show="showDirections"></div>
  </v-card>
</template>
<script>
export default {
  name: "route-details",
  props: ["coordinates", "landmarks"],
  data: () => ({
    showDirections: false,
    myMap: "",
  }),
  mounted: function () {
    // Add a global function for the callback from Bing Maps api
    window.OnLoadBingMapsApi = () => this.InitMap();

    // Add programmaticaly the external Bing maps api script
    var scriptTag = document.createElement("script");
    scriptTag.src =
      "https://www.bing.com/api/maps/mapcontrol?callback=OnLoadBingMapsApi&key=AqEpmsX-XcVaTAwtF9iIZWLn3yolAoP-D7EFP1NpkA2ERFbH-cRb4LxyNEXgMB9H";
    scriptTag.id = "scriptBingMaps";

    // Inject the dynamic script in the DOM
    document.head.appendChild(scriptTag);
  },
  methods: {
    InitMap: function () {
      var mapElement = this.$refs.myMap;
      var coordinates1 = this.coordinates;
      const lms = this.landmarks;

      var map = new Microsoft.Maps.Map(mapElement, {
        /* No need to set credentials if already passed in URL */
        center: new Microsoft.Maps.Location(
          this.coordinates.y - 0.0001,
          this.coordinates.x - 0.0001
        ),
        zoom: 12,
      });
      Microsoft.Maps.loadModule("Microsoft.Maps.Directions", function () {

        const directionsManager = new Microsoft.Maps.Directions.DirectionsManager(
          map
        );
        
        // Set Route Mode to driving
        directionsManager.setRequestOptions({
          routeMode: Microsoft.Maps.Directions.RouteMode.driving,
        });
        var waypoint1 = new Microsoft.Maps.Directions.Waypoint({
          address: "You are here",
          location: new Microsoft.Maps.Location(coordinates1.y, coordinates1.x),
        });

        directionsManager.addWaypoint(waypoint1);
        lms.forEach(landmark => {
          let lmCoordinates = landmark.location.coordinates;
          var waypoint2 = new Microsoft.Maps.Directions.Waypoint({
            address: landmark.landmarkName,
            location: new Microsoft.Maps.Location(
              lmCoordinates.y,
              lmCoordinates.x
            ),
          });
          directionsManager.addWaypoint(waypoint2);
          
        });
        // Set the element in which the itinerary will be rendered
        directionsManager.setRenderOptions({
          itineraryContainer: document.getElementById("printoutPanel"),
        });
        directionsManager.calculateDirections();
      });
    },
  },
};
</script>

<style scoped>

#myMap {
  width: 50%;
  padding-top: 50%;
  border: 3px solid indigo;
}
#printoutPanel {
  width: 100%;
  height: 50%;
  border: 3px solid indigo;
}
</style>