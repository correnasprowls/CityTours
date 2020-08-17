import axios from 'axios';

export default {

    getItineraries() {
        return axios.get('/itineraries');
    },

    createItineraries(itinerary) {
        return axios.post('/itineraries', itinerary);
    },
    deleteItinerary(id) {
        return axios.delete(`/itineraries/${id}`);
    },
    deleteLandmarkFromItinerary(landmarkId, itineraryId) {
        return axios.delete(`/landmarks/${landmarkId}/itineraries/${itineraryId}`);
    },
    addLandmarkToItinerary(landmarkId, itineraryId) {
        return axios.post(`/landmarks/${landmarkId}/itineraries/${itineraryId}`);
    },
    sendItineraryEmail(itineraryEmail) {
        return axios.post('/itineraries/email', itineraryEmail);
    }
    
}