<template>
<div>
    <v-sheet width="98%" class="mx-auto mt-0">
        
        <v-card-title>
            <itinerary-card v-for="itinerary in itineraries" :key="itinerary.itineraryId" :itinerary="itinerary" />
            
        </v-card-title>
        
        
    </v-sheet>    
</div>
</template>
<script>


import itineraryService from '@/services/ItineraryService';
import ItineraryCard from '@/components/ItineraryCard';
export default {

    name: "itinerary-list",
    data() {
        return {
            
        }
    },
    created() {
        this.retrieveItineraries();
    },
    computed: {
        itineraries() {
            return this.$store.state.itineraries;
        }
    },
    methods: {
        retrieveItineraries() {
            itineraryService.getItineraries().then( response => {
             this.$store.commit('SET_ITINERARIES', response.data)
             
            })
        }
    },
    components: {
        ItineraryCard
    }  
}
</script>
<style>
</style>