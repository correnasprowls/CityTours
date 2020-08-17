import axios from 'axios';


const http = axios.create({
    baseURL: process.env.VUE_APP_BING_MAP_API
})
export default {
    getMapPerLandmark(location) {
        return http.get(`/${location.coordinates.x},${location.coordinates.y}/13?pushpin=${location.coordinates.x},${location.coordinates.y};5&format=jpeg&mapMetadata=0&key=AqEpmsX-XcVaTAwtF9iIZWLn3yolAoP-D7EFP1NpkA2ERFbH-cRb4LxyNEXgMB9H`);
    }
}