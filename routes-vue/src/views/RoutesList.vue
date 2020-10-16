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
          Query routes
          <div class="button-transparent" id="popover-target-1">
            <b-icon class="icon-default" icon="question-circle"></b-icon>
            <b-icon class="icon-hover" icon="question-circle-fill"></b-icon>
          </div>
          <b-popover target="popover-target-1" triggers="hover" placement="rightbottom">
            <template v-slot:title>Query expressions help</template>
            <strong>Fields:</strong>
            <p>name, id, distance, created, coordX, coordY, fromX, fromY, fromZ, toX, toY, toZ</p>
            <strong>Filter operations:</strong>
            <p>(, ), [, ], =, !=, &gt;, &lt;, &gt;=, &lt;=, not, and, or, contains,  -, +, *, /</p>
            <strong>Sorting:</strong>
            <p>comma-separated fields, leading '~' to invert sorting</p>
            <strong>Example:</strong>
            <p>filter: fromX > toX - 10 and name contains "route"</p>
            <p>sort: id, ~distance</p>
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
      <br/>
      <b-form-group label="Special actions">
        <b-form inline>
          <input id="filter-expr" type="text" class="form-control mb-2 mr-sm-2 mb-sm-0" style="flex: 1;" placeholder="Distance" v-model="distance"/>
          <button class="m-3 btn btn-sm btn-outline-danger" type="button" @click="delOneByDst">Delete one by distance</button>
        </b-form>
        <b-form inline>
          <input id="filter-expr" type="text" class="form-control mb-2 mr-sm-2 mb-sm-0" style="flex: 1;" placeholder="Name" v-model="exactName"/>
          <button class="m-3 btn btn-sm btn-outline-dark" type="button" @click="getByName">Find by name</button>
        </b-form>
      </b-form-group>
    </div>
    <div class="col-md-6">
      <h4>Routes List</h4>
      <p id="p-search-result" hidden>Search result for filter: " {{this.lastSearchFilter}} " and sort: "{{this.lastSearchSort}} ".</p>
      <nav aria-label="Page navigation example">
          <ul class="pagination">
              <li class="page-item">
                <button type="button" class="page-link" v-if="!routes.isFirstPage" @click="paginate(0);"> {{1}} </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" v-if="routes.pageNumber > 2" > ... </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" v-if="routes.pageNumber > 1" @click="paginate(routes.pageNumber - 1);"> {{routes.pageNumber}} </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link current" @click="paginate(routes.pageNumber);"> {{routes.pageNumber + 1}} </button>
              </li>

              <!-- <li class="page-item">
                <button type="button" class="page-link" v-for="pageNumber in Array(routes.pageCount).map((n) => ))" :key="pageNumber" @click="paginate(pageNumber)"> {{pageNumber}} </button>
              </li> -->
              <li class="page-item">
                <button type="button" class="page-link" v-if="routes.pageNumber < routes.pagesCount - 2" @click="paginate(routes.pageNumber + 1);" > {{routes.pageNumber + 2}} </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" v-if="routes.pageNumber < routes.pagesCount - 3" > ... </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" v-if="!routes.isLastPage" @click="paginate(routes.pagesCount - 1);" > {{routes.pagesCount}} </button>
              </li>
          </ul>
        </nav>
      <ul class="list-group">
        <li class="list-group-item" :class="{ active: index == currentIndex }" v-for="(route, index) in routes.routes" :key="index" @click="setActiveRoute(route, index)">{{ route.name }}</li>
      </ul>
      <div>
        <nav aria-label="Page navigation example">
          <ul class="pagination">
              <li class="page-item">
                <button type="button" class="page-link" :disabled="routes.isFirstPage" @click="paginate(0);"> |&lt; </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" :disabled="routes.isFirstPage" @click="paginate(routes.pageNumber - 1);"> &lt; </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" @click="paginate(routes.pageNumber);"> {{routes.pageNumber + 1}} </button>
              </li>

              <!-- <li class="page-item">
                <button type="button" class="page-link" v-for="pageNumber in Array(routes.pageCount).map((n) => ))" :key="pageNumber" @click="paginate(pageNumber)"> {{pageNumber}} </button>
              </li> -->
              <li class="page-item">
                <button type="button" class="page-link" :disabled="routes.isLastPage" @click="paginate(routes.pageNumber + 1);" > &gt; </button>
              </li>
              <li class="page-item">
                <button type="button" class="page-link" :disabled="routes.isLastPage" @click="paginate(routes.pagesCount - 1);" > &gt;| </button>
              </li>
          </ul>
        </nav>
      </div>
    </div>
    <div>
      <button class="btn btn-link" id="btn-get-summ-dst" @click="getSummaryDst"> Calc summary distance</button>
      <label id="label-summ-dst" hidden=true @click="getSummaryDst">Summary distance: </label><b id="summ-dst" @click="getSummaryDst"/>
      <hr />

      <div v-if="currentRoute">
        <h4>Route</h4>
        <div>
          <label><strong>Id:</strong></label>
          {{ currentRoute.id }}
        </div>
        <div>
          <label><strong>Name:</strong></label>
          {{ currentRoute.name }}
        </div>
        <div>
          <label><strong>Creation date:</strong></label>
          {{ currentRoute.creationDateString }}
        </div>
        <div>
          <label><strong>Distance:</strong></label>
          {{ currentRoute.distance }}
        </div>
        <div>
          <label><strong>Coordinates X:</strong></label>
          {{ currentRoute.coordinates.x }}
        </div>
        <div>
          <label><strong>Coordinates Y:</strong></label>
          {{ currentRoute.coordinates.y }}
        </div>
        <div>
          <label><strong>Source location X:</strong></label>
          {{ currentRoute.from.x }}
        </div>
        <div>
          <label><strong>Source location Y:</strong></label>
          {{ currentRoute.from.y }}
        </div>
        <div>
          <label><strong>Source location Z:</strong></label>
          {{ currentRoute.from.z }}
        </div>
        <div>
          <label><strong>Destination location X:</strong></label>
          {{ currentRoute.to.x }}
        </div>
        <div>
          <label><strong>Destination location Y:</strong></label>
          {{ currentRoute.to.y }}
        </div>
        <div>
          <label><strong>Destination location Z:</strong></label>
          {{ currentRoute.to.z }}
        </div>
        <div>
          <a class="badge badge-warning mb-2 mr-sm-2 mb-sm-0" :href="'/routes/' + currentRoute.id">Edit</a>
        </div>
      </div>
      <div v-else>
        <br />
        <p>Please click on a Route...</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import RouteDataService, { RouteInfo, RoutesInfo } from "../services/RouteDataService";
import RouteItem from './RouteItem.vue'

@Component({
  components: { RouteItem }
})
export default class RoutesList extends Vue {
  public routes = new RoutesInfo([], 0, 0, 0, 0);
  public title = '';
  public currentRoute: RouteInfo|null = null;
  public currentIndex: number = -1;
  public filter: string = "";
  public sort: string = "";
  public summDst: number = 0;
  public distance:number = 0;
  public exactName = '';
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
    RouteDataService.findByParams(this.filter, this.sort, this.pageSize, pageNumber)
      .then((response) => {
        this.routes = response;
        console.log(response);
      })
      .catch((e) => {
        this.handleError(e);
      });
  }

  refreshList() {
    this.paginate(this.routes.pageNumber);
    this.currentRoute = null;
    this.currentIndex = -1;
    document.getElementById("summ-dst")!.innerText="";
  }

  setActiveRoute(route: RouteInfo, index: number) {
    this.currentRoute = route;
    this.currentIndex = index;
  }

  delOneByDst() {
    RouteDataService.delOneByDst(this.distance)
      .then((response) => {
        document.getElementById("summ-dst")!.innerText = "";
        document.getElementById("label-summ-dst")!.hidden = true;
        document.getElementById("btn-get-summ-dst")!.hidden = false;
        this.statusInfoTitle = response.status;
        this.statusInfoMessage = response.message;
        this.$bvModal.show('modalInfoPopover')
        this.refreshList();
      })
      .catch((e) => {
        this.handleError(e);
      });
  }

  getSummaryDst() {
    RouteDataService.getSummaryDst()
      .then((response) => {
        this.summDst = response;
        document.getElementById("summ-dst")!.innerText = this.summDst.toString();
        document.getElementById("btn-get-summ-dst")!.hidden = true;
        document.getElementById("label-summ-dst")!.hidden = false;
      }).catch((e) => {
        this.handleError(e);
      });
  }

  findByParams() {
    RouteDataService.findByParams(this.filter, this.sort, this.pageSize, 0)
      .then((response) => {
        this.routes = response;
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

  getByName() {
    RouteDataService.getByName(this.exactName)
    .then((response) => {
      this.routes = response;
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