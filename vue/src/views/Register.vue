<template>

  <div id="register" class="text-center">
    <v-card width="400" class="mx-auto mt-5">
    <v-form class="form-register" @submit.prevent="register">
      <v-card-text id="register" class="text-center">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
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
      <v-text-field label="Confirm Password"
        :type="showConfirmPassword ? 'text' : 'password'"
        
        id="confirmPassword"
        class="form-control"
        prepend-icon="mdi-lock"
        :append-icon="showConfirmPassword ? 'mdi-eye' : 'mdi-eye-off'"
        @click:append="showConfirmPassword = !showConfirmPassword"
        v-model="user.confirmPassword"
        required
      />
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
      <v-btn color="success"><router-link :to="{ name: 'login' }">Have an account?</router-link></v-btn>
      <v-spacer></v-spacer>
      <v-btn color="info" type="submit">
        Create Account
      </v-btn>
      </v-card-actions>
    </v-form>
    </v-card>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      showPassword: false,
      showConfirmPassword: false,
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style></style>
