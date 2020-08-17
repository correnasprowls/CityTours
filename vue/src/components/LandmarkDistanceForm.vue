<template>
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

        <v-select label="Distance (miles)" :items="distances" />
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-btn color="success" type="submit" @click.prevent="saveLocation">Apply</v-btn>
        <v-spacer></v-spacer>
        <v-btn
          color="indigo accent-1"
          type="submit"
          @click.prevent="location = {coordinates: {}}"
        >Cancel</v-btn>
      </v-card-actions>
    </v-form>
  </v-sheet>
</template>

<script>
export default {
  name: "landmark-distance-form",
  data() {
    return {
      location: {
        cityName: "",
        stateCode: "",
        addressLineOne: "",
        zipCode: "",
        coordinates: {
          x: 0.0,
          y: 0.0,
        },
      },
      distances: [1, 5, 10, 15, 20],
      errorMsg: "",
    };
  },
  methods: {
      saveLocation() {
        this.$store.commit('SET_LOCATION', this.location);
      }
  }
};
</script>

<style>
</style>