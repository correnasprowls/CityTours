<template>
  <div>
    <v-card width="400" class="mx-auto mt-5">
  
  
    <v-form class="form-signin" @submit.prevent="login">
      <v-card-text id="login" class="text-center">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <v-text-field label="Username"
        type="text"
        id="username"
        class="form-control"
        prepend-icon="mdi-account-circle"
        v-model="user.username"
        required
        autofocus
      />
      <v-text-field label="Password"
        :type="showPassword ? 'text' : 'password'"
        id="password"
        class="form-control"
        prepend-icon="mdi-lock"
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        @click:append="showPassword = !showPassword"
        v-model="user.password"
        required
      />
      </v-card-text>
      <v-divider></v-divider>
      
      <v-card-actions>
        <v-btn color="success"><router-link :to="{ name: 'register' }">Need an account?</router-link></v-btn>
        <v-spacer></v-spacer>
        <v-btn color="info" type="submit">Sign in</v-btn>
      </v-card-actions>
    </v-form>
  
  </v-card>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      showForm: false,
      showPassword: false,
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>
