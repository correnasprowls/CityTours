<template>
  <div>
    <v-card width="700" class="mx-auto mt-5">
      <v-form class="add-landmark-name">
        <v-card-text id="add-landmark-name" class="text-center">
          <h1 class="h3 mb-3 font-weight-normal">Add New Landmark</h1>
          <v-text-field
            label="Landmark Name"
            type="text"
            id="name"
            class="form-control"
            v-model="newLandmark.landmarkName"
            required
            autofocus
          />

          <v-text-field
            label="Address Line One"
            type="text"
            id="address-line-one"
            class="form-control"
            v-model="newLandmark.location.addressLineOne"
            required
          />

          <v-select
            label="State"
            :items="Object.keys(this.$store.state.states)"
            id="state-code"
            class="form-control"
            v-model="newLandmark.location.stateCode"
            required
          />
          <v-text-field
            label="City"
            type="text"
            id="city-name"
            class="form-control"
            v-model="newLandmark.location.cityName"
            required
          />
          <v-text-field
            label="Zip Code"
            type="text"
            id="zipcode"
            class="form-control"
            v-model="newLandmark.location.zipCode"
            required
          />
          <v-textarea
            label="Please add a description"
            type="text"
            id="description"
            class="form-control"
            v-model="newLandmark.landmarkDescription"
            required
          />
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-btn color="success" type="submit" @click.prevent="saveNewLandmark">Submit Landmark</v-btn>
          <v-spacer></v-spacer>
          <v-btn
            color="indigo accent-1"
            type="submit"
            @click.prevent="newLandmark = {location: {}}"
          >Cancel</v-btn>
        </v-card-actions>
      </v-form>
    </v-card>
  </div>
</template>
<script>
import landmarkService from "@/services/LandmarkService";

export default {
  data() {
    return {
      newLandmark: {
        landmarkName: "",
        landmarkDescription: "",
        location: {
          cityName: "",
          stateCode: "",
          addressLineOne: "",
          zipCode: "",
          coordinates: {
            x: 0.0,
            y: 0.0
          }
        },
      },
      errorMsg: "",
    };
  },
  methods: {
    saveNewLandmark() {
      landmarkService
        .addLandmark(this.newLandmark)
        .then((response) => {
          if (response.status === 201) {
            this.$router.push(`/landmarks/${response.data.landmarkId}`);
          }
        })
        .catch((error) => {
          if (error.response) {
            this.errorMsg =
              "Error submitting new landmark. Received " + error.response.statusText;
            alert(this.errorMsg);
          }else if (error.request) {
            this.errorMsg = "Server could not be reached";
            alert(this.errorMsg);
          } else {
            this.errorMsg = "Request could not be created.";
            alert(this.errorMsg);
          }
        });
    },
  },
};
</script>
<style>
</style>