<template>
  <div class="submit-form">
    <div>
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
    </div>

    <div v-if="!submitted">
      <div class="form-group">
        <label for="name">Name</label>
        <input type="text" minlength="1" class="form-control" placeholder="string" id="name" required v-model="route.name" name="name" />
      </div>

      <div class="form-group">
        <label for="distance">Distance</label>
        <input type="number" min="2" class="form-control" id="distance" placeholder="long" required v-model="route.distance" name="distance" />
      </div>

      <div class="form-group">
        <label for="coordX">Coordinate X</label>
        <input type="number" max="241" class="form-control" id="coordX" placeholder="integer <= 241" required v-model="route.coordinates.x" name="coordX" />
      </div>
      <div class="form-group">
        <label for="coordY">Coordinate Y</label>
        <input type="decimal" max="180" class="form-control" id="coordY" placeholder="double <= 180" required v-model="route.coordinates.y" name="coordY" />
      </div>

      <div class="form-group">
        <label for="fromLocX">Source location X</label>
        <input type="number" class="form-control" id="fromLocX" placeholder="integer" required v-model="route.from.x" name="fromLocX" />
      </div>
      <div class="form-group">
        <label for="fromLocY">Source location Y</label>
        <input type="number" class="form-control" id="fromLocY" placeholder="integer" v-model="route.from.y" name="fromLocY" />
      </div>
      <div class="form-group">
        <label for="fromLocZ">Source location Z</label>
        <input type="decimal" class="form-control" id="fromLocZ" placeholder="float" required v-model="route.from.z" name="fromLocZ" />
      </div>

      <div class="form-group">
        <label for="toLocX">Destination location X</label>
        <input type="number" class="form-control" id="toLocX" placeholder="integer" required v-model="route.to.x" name="toLocX" />
      </div>
      <div class="form-group">
        <label for="toLocY">Destination location Y</label>
        <input type="number" class="form-control" id="toLocY" placeholder="integer" v-model="route.to.y" name="toLocY" />
      </div>
      <div class="form-group">
        <label for="toLocZ">Destination location Z</label>
        <input type="decimal" class="form-control" id="toLocZ" placeholder="float" required v-model="route.to.z" name="toLocZ" />
      </div>

      <button @click="saveRoute" class="btn btn-success">Submit</button>
    </div>

    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" @click="newRoute">Add</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import RouteDataService, { Coordinates, Location, RouteInfo } from "../services/RouteDataService";

@Component
export default class AddRoute extends Vue {
  public route = RouteInfo.makeEmptyRoute();
  public errorTitle = '';
  public errorMessage = '';
  public errorDescription = '';

  private submitted: boolean = false;

  saveRoute() {
    this.route.id = 1;
    RouteDataService.create(this.route)
      .then((response) => {
        this.route = response;
        console.log(response);
        this.submitted = true;
      })
      .catch((e) => {
        this.handleError(e);
      });
  }

  newRoute() {
    this.submitted = false;
    this.route = RouteInfo.makeEmptyRoute();
  }

  mounted(){
    this.newRoute();
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
.submit-form {
  max-width: 300px;
  margin: auto;
}
</style>
