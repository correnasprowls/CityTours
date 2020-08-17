import axios from 'axios';

const http = axios.create({
    baseURL: process.env.VUE_APP_GEO_COORDINATE_API
})

export default {
    getCoordinates(location) {
        return http.get(`/address?street=${location.addressLineOne}&city=${location.cityName}&state=${location.stateCode}&zip=${location.zipCode}&benchmark=Public_AR_Census2010&format=json`,
        );
    }
    
}