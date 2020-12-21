<template>
  <div class="list row">
    <div>
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
      <b-modal id="modalInfoPopover" size="xl" title="Status Info" ok-only>
        <p>
          <label><strong>{{ statusInfoTitle }}</strong></label>
          <pre>{{ statusInfoMessage }}</pre>
        </p>
      </b-modal>
    </div>
    <div class="col-md-8">
      <b-form-group label="">
        <label>
          Query locations
          <div class="button-transparent" id="popover-target-1">
            <b-icon class="icon-default" icon="question-circle"></b-icon>
            <b-icon class="icon-hover" icon="question-circle-fill"></b-icon>
          </div>
          <b-popover target="popover-target-1" triggers="hover" placement="rightbottom">
            <template v-slot:title>Query expressions help</template>
            <strong>Fields:</strong>
            <p>id, X, Y, Z</p>
            <strong>Filter operations:</strong>
            <p>(, ), [, ], =, !=, &gt;, &lt;, &gt;=, &lt;=, not, and, or, contains,  -, +, *, /</p>
            <strong>Sorting:</strong>
            <p>comma-separated fields, leading '~' to invert sorting</p>
            <strong>Example:</strong>
            <p>filter: X > Y - 10 and id >= 3</p>
            <p>sort: id, ~X</p>
          </b-popover>
        </label>
        <input id="filter-expr" type="text" class="form-control" placeholder="Filter expression.." v-model="filter"/>
      </b-form-group>
      <b-form inline>
        <input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0" style="flex: 1;" placeholder="Order by.." v-model="sort"/>
        <div class="input-group-append">
          <button class="btn btn-outline-dark" type="button" @click="findByParams">Search</button>
        </div>
      </b-form>
    </div>
    <br/>
    <div class="col-md-6" style="margin-top:20px;">
      <h4>Locations List</h4>
      <p id="p-search-result" hidden>Search result for filter: " {{this.lastSearchFilter}} " and sort: "{{this.lastSearchSort}} ".</p>
      <nav aria-label="Page navigation example">
          <ul class="pagination">
              <li class="page-item">
                <button type="button" class="page-link" v-if="!locations.isFirstPage" @click="paginate(0);"> {{1}} </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" v-if="locations.pageNumber > 2" > ... </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" v-if="locations.pageNumber > 1" @click="paginate(locations.pageNumber - 1);"> {{locations.pageNumber}} </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link current" @click="paginate(locations.pageNumber);"> {{locations.pageNumber + 1}} </button>
              </li>

              <!-- <li class="page-item">
                <button type="button" class="page-link" v-for="pageNumber in Array(routes.pageCount).map((n) => ))" :key="pageNumber" @click="paginate(pageNumber)"> {{pageNumber}} </button>
              </li> -->
              <li class="page-item">
                <button type="button" class="page-link" v-if="locations.pageNumber < locations.pagesCount - 2" @click="paginate(locations.pageNumber + 1);" > {{locations.pageNumber + 2}} </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" v-if="locations.pageNumber < locations.pagesCount - 3" > ... </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" v-if="!locations.isLastPage" @click="paginate(locations.pagesCount - 1);" > {{locations.pagesCount}} </button>
              </li>
          </ul>
        </nav>
      <ul class="list-group">
        <li class="list-group-item" :class="{ active: index == currentIndex }" v-for="(location, index) in locations.entities" :key="index" @click="setActiveLocation(location, index)">{{ location.id }}</li>
      </ul>
      <div>
        <nav aria-label="Page navigation">
          <ul class="pagination">
              <li class="page-item">
                <button type="button" class="page-link" :disabled="locations.isFirstPage" @click="paginate(0);"> |&lt; </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" :disabled="locations.isFirstPage" @click="paginate(locations.pageNumber - 1);"> &lt; </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" @click="paginate(locations.pageNumber);"> {{locations.pageNumber + 1}} </button>
              </li>

              <!-- <li class="page-item">
                <button type="button" class="page-link" v-for="pageNumber in Array(routes.pageCount).map((n) => ))" :key="pageNumber" @click="paginate(pageNumber)"> {{pageNumber}} </button>
              </li> -->
              <li class="page-item">
                <button type="button" class="page-link" :disabled="locations.isLastPage" @click="paginate(locations.pageNumber + 1);" > &gt; </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" :disabled="locations.isLastPage" @click="paginate(locations.pagesCount - 1);" > &gt;| </button>
              </li>
          </ul>
        </nav>
      </div>
    </div>
    <div>
      <div v-if="currentLocation">
        <h4>Location</h4>
        <div>
          <label><strong>Id:</strong></label>
          {{ currentLocation.id }}
        </div>
        <div>
          <label><strong>X:</strong></label>
          {{ currentLocation.x }}
        </div>
        <div>
          <label><strong>Y:</strong></label>
          {{ currentLocation.y }}
        </div>
        <div>
          <label><strong>Z:</strong></label>
          {{ currentLocation.z }}
        </div>
        <div>
          <router-link class="badge badge-warning mb-2 mr-sm-2 mb-sm-0" :to="{name: 'location-details', params: {id: this.currentLocation.id}}">Edit</router-link>
        </div>
      </div>
      <div v-else>
        <br />
        <p>Please click on a Location...</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import LocationDataService from "@/services/LocationDataService";
import { Component, Vue } from "vue-property-decorator";
import { LocationInfo, LocationsInfo } from "../services/DataEntities";
import LocationItem from './LocationItem.vue'

@Component({
  components: { LocationItem }
})
export default class LocationsList extends Vue {
  public locations = new LocationsInfo();
  public title = '';
  public currentLocation: LocationInfo|null = null;
  public currentIndex: number = -1;
  public filter: string = "";
  public sort: string = "";
  public errorTitle = '';
  public errorMessage = '';
  public errorDescription = '';
  public statusInfoMessage = '';
  public statusInfoTitle = '';
  public lastSearchFilter ='';
  public lastSearchSort ='';
  public pageSize: number = 5;

  paginate(pageNumber: number) {
    this.currentIndex = -1;
    LocationDataService.findByParams(this.filter, this.sort, this.pageSize, pageNumber)
      .then((response) => {
        this.locations = response;
        console.log(response);
      })
      .catch((e) => {
        this.handleError(e);
      });
  }

  refreshList() {
    this.paginate(this.locations.pageNumber);
    this.currentLocation = null;
    this.currentIndex = -1;
    document.getElementById("summ-dst")!.innerText="";
  }

  setActiveLocation(route: LocationInfo, index: number) {
    this.currentLocation = route;
    this.currentIndex = index;
  }

  findByParams() {
    LocationDataService.findByParams(this.filter, this.sort, this.pageSize, 0)
      .then((response) => {
        this.locations = response;
        this.lastSearchFilter = this.filter;
        this.lastSearchSort = this.sort;
        if(this.filter == "" && this.sort == "")
          document.getElementById("p-search-result")!.hidden = true;
        else
          document.getElementById("p-search-result")!.hidden = false;
      })
      .catch((e) => {
        this.handleError(e);
      });
  }

  clearSearchMessage(){
    document.getElementById("p-search-result")!.hidden = true;
  }

  mounted() {
    this.paginate(0);
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

<style scoped>
.list {
  text-align: left;
  max-width: 770px;
  margin: auto;
}

#summ-dst {
  font-size: 14pt;
  margin: 5pt;
}

#p-search-result{
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: darkgrey;
}

button.page-link {
  display: inline-block;
  font-size: 20px;
  color: #29b3ed;
  font-weight: 500;
}
button.page-link:disabled,
button.page-link[disabled]{
  color: #ffffff;
  background-color: #eeeeee;
}
button.current {
  background-color: #eeeeee;
}
.offset{
  width: 500px !important;
  margin: 20px auto;
}

.button-transparent {
  margin: 0px;
  display: inline-block;
}
.button-transparent > .icon-default {
  display: block;
}
.button-transparent > .icon-hover {
  display: none;
}
.button-transparent:hover > .icon-default {
  display: none;
}
.button-transparent:hover > .icon-hover {
  display: block;
}
</style>