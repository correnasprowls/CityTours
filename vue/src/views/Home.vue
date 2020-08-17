<template>
  <v-sheet class="home">
    <v-card-title ><h1>Welcome to Columbus!</h1></v-card-title>
    <v-card-subtitle><h4>You'll be captivated by everything Columbus has to offer</h4></v-card-subtitle>
    <v-sheet height="15vh"></v-sheet>
    <v-container class="fill-height pt-20" fluid>
      <v-carousel cycle height="400" hide-delimiter-background show-arrows-on-hover>
        <v-carousel-item v-for="(slide, i) in $store.state.slides" :key="i">
          <v-sheet height="100%">
            <v-row class="fill-height" align="center" justify="center">
              <div class="display-3">
                <v-img :src="slide" height="400" alt></v-img>
              </div>
            </v-row>
          </v-sheet>
        </v-carousel-item>
      </v-carousel>
    </v-container>
  </v-sheet>
</template>

<script>
export default {
  name: "home",
  components: {},
};
</script>
