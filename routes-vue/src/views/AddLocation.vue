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
        <label for="X">Coordinate X</label>
        <input type="number" class="form-control" placeholder="integer" required v-model="location.x" name="X" />
      </div>
      <div class="form-group">
        <label for="Y">Coordinate Y</label>
        <input type="number" class="form-control" placeholder="integer" required v-model="location.y" name="Y" />
      </div>
      <div class="form-group">
        <label for="Z">Coordinate Z</label>
        <input type="number" class="form-control" placeholder="integer" required v-model="location.z" name="Z" />
      </div>
      <button @click="saveLocation" class="btn btn-success">Submit</button>
    </div>

    <div v-else>
      <h4>You submitted successfully!</h4>
      <button class="btn btn-success" @click="newLocation">Add</button>
    </div>
  </div>
</template>

<script lang="ts">
import { LocationInfo, RouteInfo } from "@/services/DataEntities";
import LocationDataService from "@/services/LocationDataService";
import { Component, Vue } from "vue-property-decorator";

@Component
export default class AddLocation extends Vue {
  public location = new LocationInfo(0, 0, 0, 0);
  public errorTitle = '';
  public errorMessage = '';
  public errorDescription = '';

  private submitted: boolean = false;

  saveLocation() {
    //this.location.id = 1;
    LocationDataService.create(this.location)
      .then((response) => {
        this.location = response;
        console.log(response);
        this.submitted = true;
      })
      .catch((e) => {
        this.handleError(e);
      });
  }

  newLocation() {
    this.submitted = false;
    this.location = new LocationInfo(0, 0, 0, 0);
  }

  mounted(){
    this.newLocation();
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
