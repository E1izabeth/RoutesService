<template>
    <div class="col-md-8" style="margin-top:20px;">
        <b-modal id="modalErrPopover" size="xl" title="Error occured" ok-only>
            <p>
            <label><strong>{{ errorTitle }}</strong></label>
                <pre>{{ errorMessage }}</pre>
            <b-button v-if="errorDescription" v-b-toggle.collapse-3 class="page-link button-small">Details</b-button>
            <b-collapse id="collapse-3">
                <b-card>
                <pre>{{ errorDescription }}</pre>
                </b-card>
            </b-collapse>
            </p>
        </b-modal>

        <h4>Navigator</h4>
        <label for="LocationFrom" class="mb-2 mr-sm-2">From:</label>
        <input id="LocationFrom" type="text" class="form-control mb-2 mr-sm-2" style="width: 100px; display: inline-block;" placeholder="Location Id" v-model="LocationFromId"/>
        <label for="LocationTo" class="mb-2 mr-sm-2">To:</label>
        <input id="LocationTo" type="text" class="form-control mb-2 mr-sm-2" style="width: 100px; display: inline-block;" placeholder="Location Id" v-model="LocationToId"/>
        <button class="m-3 btn btn-sm btn-outline-danger" type="button" @click="findShortestRoute">Find shortest</button>
        <button class="m-3 btn btn-sm btn-outline-danger" type="button" @click="findLongestRoute">Find longest</button>
        <br />
        <button class="m-3 btn btn-sm btn-outline-dark" type="button" @click="findRoutes">Find all routes</button>
        <label for="sort-parameter" class="mb-2 mr-sm-2"> sorted by </label>
        <!-- <input type="text" class="form-control mb-2 mr-sm-2" placeholder="field" v-model="LocationTo"/> -->
        <b-form-select id="sort-parameter" style="width: 100px;" v-model="selectedSortKey" :options="sortKeys" class="mb-mb-2 mr-sm-2" value-field="key" text-field="title"></b-form-select>


        <b-table striped hover :items="routes.entities" :fields="fields"></b-table>

    </div>
</template>


<script lang="ts">

import { LocationInfo, RouteInfo, RoutesInfo } from "@/services/DataEntities";
import { Component, Vue } from "vue-property-decorator";
import NavigatorService from "../services/NavigatorService";

class SortKey {
    public constructor(
        public readonly title: string,
        public readonly key: string
    ) {
    }
};


@Component
export default class Navigator extends Vue {

    public sortKeys: SortKey[] = [
        new SortKey('id', 'id'),
        new SortKey('name', 'id'),
        new SortKey('distance', 'id'),
        new SortKey('creation date', 'id'),
        new SortKey('coordinates X', 'id'),
        new SortKey('coordinates Y', 'id'),
    ];

    public selectedSortKey: string = '';

    public LocationFromId: number = 0;
    public LocationToId: number = 0;

    public fields = ['id', 'name', 'distance', 'creationDateString', 'coordinates.x', 'coordinates.y', 'from.id', 'to.id'];
    public routes = new RoutesInfo();

    public errorTitle = '';
    public errorMessage = '';
    public errorDescription = '';


    findShortestRoute(){
        NavigatorService.findShortest(this.LocationFromId, this.LocationToId)
            .then((response) => {
                this.routes = response;
            }).catch((e) => {
                this.handleError(e);
            });
    }
    findLongestRoute(){
        NavigatorService.findLongest(this.LocationFromId, this.LocationToId)
            .then((response) => {
                this.routes = response;
            }).catch((e) => {
                this.handleError(e);
            });
    }
    findRoutes(){
        NavigatorService.findAllOrdered(this.LocationFromId, this.LocationToId, this.selectedSortKey)
            .then((response) => {
                this.routes = response;
            }).catch((e) => {
                this.handleError(e);
            });
    }

    handleError(e:any){
        console.log(e);
        if (e.remote) {
            this.errorTitle = `${e.response.status} ${e.response.statusText}`;
            this.errorMessage = e.remote.message;
            this.errorDescription = e.remote.stackTrace;
        } else {
            this.errorTitle = "Unexpected error";
            this.errorMessage = e.message;
            this.errorDescription = e.stack;
        }
        this.$bvModal.show('modalErrPopover')
    }
}
</script>