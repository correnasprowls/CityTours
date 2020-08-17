<template>
  <div>
    <div id='printoutPanel'></div>
  <div id="myMap" ref="myMap"></div>
  </div>
</template>
<script>
export default {
  name: "our-map",
  props: ['coordinates'],
  data: () => ({
    myMap: ""
  }),
mounted: function() {


  // Add a global function for the callback from Bing Maps api
  window.OnLoadBingMapsApi = () => this.InitMap();

  // Add programmaticaly the external Bing maps api script
  var scriptTag = document.createElement("script");
  scriptTag.src = "https://www.bing.com/api/maps/mapcontrol?callback=OnLoadBingMapsApi&key=AqEpmsX-XcVaTAwtF9iIZWLn3yolAoP-D7EFP1NpkA2ERFbH-cRb4LxyNEXgMB9H";
  scriptTag.id = "scriptBingMaps";

  // Inject the dynamic script in the DOM
  document.head.appendChild(scriptTag);
},
methods: {
  InitMap: function() {
    var mapElement = this.$refs.myMap;
    var map = new Microsoft.Maps.Map(mapElement, {
      mapTypeId: Microsoft.Maps.MapTypeId.road,
      zoom: 15,
      maxZoom: 21,
      //minZoom: 15,
      center: new Microsoft.Maps.Location(this.coordinates.y, this.coordinates.x),
      maxNetworkLinkDepth: 3
    });
    var pushpin = new Microsoft.Maps.Pushpin(map.getCenter(), {
    icon: 'https://www.bingmapsportal.com/Content/images/poi_custom.png',
    anchor: new Microsoft.Maps.Point(12, 39),
    text: 'A',
    textOffset: new Microsoft.Maps.Point(0, 5)
});
map.entities.push(pushpin);

  }
}
  
};
</script>

<style scoped>
#myMap {
  width: 100%;
  padding-top: 100%;
}
</style>