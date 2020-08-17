import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if (currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    selectedItinerary: '',
    selectedLandmark: {},
    showItineraryDialog: false,
    isLoading: false,
    states:
    {
      'AK': 'Alaska',
      'AL': 'Alabama',
      'AZ': 'Arizona',
      'AR': 'Arkansas',
      'CA': 'California',
      'CO': 'Colorado',
      'CT': 'Connecticut',
      'DE': 'Delaware',
      'DC': 'District of Columbia',
      'FL': 'Florida',
      'GA': 'Georgia',
      'HI': 'Hawaii',
      'ID': 'Idaho',
      'IL': 'Illinois',
      'IN': 'Indiana',
      'IA': 'Iowa',
      'KS': 'Kansas',
      'KY': 'Kentucky',
      'LA': 'Louisiana',
      'ME': 'Maine',
      'MD': 'Maryland',
      'MA': 'Massachusetts',
      'MI': 'Michigan',
      'MN': 'Minnesota',
      'MS': 'Mississippi',
      'MO': 'Missouri',
      'MT': 'Montana',
      'NE': 'Nebraska',
      'NV': 'Nevada',
      'NH': 'New Hampshire',
      'NJ': 'New Jersey',
      'NM': 'New Mexico',
      'NY': 'New York',
      'NC': 'North Carolina',
      'ND': 'North Dakota',
      'OH': 'Ohio',
      'OK': 'Oklahoma',
      'OR': 'Oregon',
      'PA': 'Pennsylvania',
      'PR': 'Puerto Rico',
      'RI': 'Rhode Island',
      'SC': 'South Carolina',
      'SD': 'South Dakota',
      'TN': 'Tennessee',
      'TX': 'Texas',
      'UT': 'Utah',
      'VT': 'Vermont',
      'VA': 'Virginia',
      'WA': 'Washington',
      'WV': 'West Virginia',
      'WI': 'Wisconsin',
      'WY': 'Wyoming'
    },
    slides: [
      'https://cdn.pixabay.com/photo/2016/08/24/22/57/columbus-1618317_960_720.jpg',
      'https://cdn.pixabay.com/photo/2016/12/28/13/33/columbus-1936113__340.jpg',
      'https://cdn.pixabay.com/photo/2016/05/30/16/55/climbing-wall-1425211__340.jpg'

    ],
    userLocation: {
      coordinates: {}
    },
    itineraries: []

  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_LOCATION(state, location) {
      state.location = location;
    },
    SET_ITINERARIES(state, itineraries) {
      state.itineraries = itineraries;
    },
    DELETE_ITINERARY(state, id) {
      state.itineraries = state.itineraries.filter((i) => {
        return i.itineraryId != id;
      });
    },
    ADD_LANDMARK_TO_ITINERARY(state, payload) {
      state.itineraries.forEach(i => {
        if (i.itineraryId == payload.itineraryId) {
          i.landmarks.push(payload.landmark);
        }
      })
    },
    DELETE_LANDMARK_FROM_ITINERARY(state, payload) {
      state.itineraries.forEach((i) => {
        if (i.itineraryId == payload.itineraryId) {
          i.landmarks = i.landmarks.filter((l) => {
            return l.landmarkId != payload.landmarkId;
          })
        }
      })
    },
    SET_SELECTED_ITINERARY(state, itineraryId) {
      state.selectedItinerary = itineraryId;
    },
    FLIP_ITINERARY_DIALOG(state) {
      state.showItineraryDialog = !state.showItineraryDialog;
    },
    SET_CURRENT_LANDMARK(state, landmark) {
      state.selectedLandmark = landmark;
    },
    FLIP_IS_LOADING(state) {
      state.isLoading = !state.isLoading;
    }
  }
})
