<template>
  <div v-if="route" class="edit-form">
    <b-modal id="modalPopover" size="xl" title="Error occured" ok-only>
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

    <h4>Route</h4>
    <p id="message" v-if="this.message">{{this.message}}</p>

    <div v-if="!submitted">
      <div class="form-group">
        <label for="name">Name</label>
        <input type="text" class="form-control" id="name" required v-model="route.name" name="name" />
      </div>

      <div class="form-group">
        <label for="distance">Distance</label>
        <input class="form-control" id="distance" required v-model="route.distance" name="distance" />
      </div>

      <div class="form-group">
        <label for="coordX">Coordinate X</label>
        <input class="form-control" id="coordX" required v-model="route.coordinates.x" name="coordX" />
      </div>

      <div class="form-group">
        <label for="coordY">Coordinate Y</label>
        <input class="form-control" id="coordY" required v-model="route.coordinates.y" name="coordY" />
      </div>
      <div class="form-group">
        <label for="fromLocID">Source location Id</label>
        <input class="form-control" id="fromLocID" required v-model="route.from.id" name="fromLocId"  />
      </div>
      <div class="form-group">
        <label for="fromLocX">Source location X</label>
        <input class="form-control" id="fromLocX" required v-model="route.from.x" name="fromLocX" />
      </div>
      <div class="form-group">
        <label for="fromLocY">Source location Y</label>
        <input class="form-control" id="fromLocY" required v-model="route.from.y" name="fromLocY" />
      </div>
      <div class="form-group">
        <label for="fromLocZ">Source location Z</label>
        <input class="form-control" id="fromLocZ" required v-model="route.from.z" name="fromLocZ" />
      </div>
      <div class="form-group">
        <label for="toLocID">Destination location Id</label>
        <input class="form-control" id="toLocID" required v-model="route.to.id" name="toLocId" />
      </div>
      <div class="form-group">
        <label for="toLocX">Destination location X</label>
        <input class="form-control" id="toLocX" required v-model="route.to.x" name="toLocX" />
      </div>
      <div class="form-group">
        <label for="toLocY">Destination location Y</label>
        <input class="form-control" id="toLocY" required v-model="route.to.y" name="toLocY" />
      </div>
      <div class="form-group">
        <label for="toLocZ">Destination location Z</label>
        <input class="form-control" id="toLocZ" required v-model="route.to.z" name="toLocZ" />
      </div>

      <button class="btn btn-danger mr-2" @click="deleteRoute">
        Delete
      </button>
      <button class="btn btn-success" @click="updateRoute">
        Update
      </button>
    </div>

    <p id="message">{{ message }}</p>
  </div>

  <div v-else>
    <br />
    <p>Please click on a Route...</p>
  </div>
</template>

<script lang="ts">
import { RouteInfo } from "@/services/DataEntities";
import { Component, Prop, Vue } from "vue-property-decorator";
import RouteDataService from "../services/RouteDataService";

@Component({
})
export default class RouteItem extends Vue {
  route = RouteInfo.makeEmptyRoute();
  message: string = "";
  submitted = false;
  public errorTitle = '';
  public errorMessage = '';
  public errorDescription = '';

  getRoute(id: number) {
    RouteDataService.get(id)
      .then((response) => {
        this.route = response;
        console.log(response);
      })
    .catch((e) => {
        this.handleError(e);
    });
  }

  updateRoute() {
    RouteDataService.update(this.route)
      .then((response) => {
        console.log(response.data);
        this.message = "The Route was updated successfully!";
      })
      .catch((e) => {
        this.handleError(e);
      });
  }

  deleteRoute() {
    RouteDataService.delete(this.route.id)
      .then((response) => {
        console.log(response.data);
        this.$router.push({ name: "routes" });
      })
      .catch((e) => {
        this.handleError(e);
      });
  }

  mounted() {
    this.message = "";
    this.getRoute(Number.parseInt(this.$router.currentRoute.params.id))
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
    this.$bvModal.show('modalPopover')
  }
}
</script>

<style scoped>
.edit-form {
  max-width: 300px;
  margin: auto;
}

#message{
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: darkgrey;
}
</style>
