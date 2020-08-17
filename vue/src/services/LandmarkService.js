import axios from 'axios'

export default {
    getLandmark(id) {
        return axios.get(`/landmarks/${id}`);
    },
    addLandmark(landmark) {
        return axios.post('/landmarks', landmark);
    },
    getLandmarksInRange(location, distance) {
        return axios.post(`/landmarks/distanceLTE${distance}`, location);
    },
    getLocationWithCoordinates(location){
        return axios.post(`/coordinates`, location);
    }, 
    getForecast(location) {
        return axios.post('landmarks/forecasts', location);
    } 
}