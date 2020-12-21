<template>
  <div v-if="location" class="edit-form">
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

    <h4>Location</h4>
    <p id="message" v-if="this.message">{{this.message}}</p>

    <div v-if="!submitted">
      <div class="form-group">
        <label for="Id">Id</label>
        <input class="form-control" id="id" required v-model="location.id" name="id" disabled/>
      </div>
      <div class="form-group">
        <label for="X">X</label>
        <input class="form-control" id="X" required v-model="location.x" name="X" />
      </div>
      <div class="form-group">
        <label for="Y">Y</label>
        <input class="form-control" id="Y" required v-model="location.y" name="Y" />
      </div>
      <div class="form-group">
        <label for="Z">Z</label>
        <input class="form-control" id="Z" required v-model="location.z" name="Z" />
      </div>

      <button class="btn btn-danger mr-2" @click="deleteLocation">
        Delete
      </button>
      <button class="btn btn-success" @click="updateLocation">
        Update
      </button>
    </div>

    <p id="message">{{ message }}</p>
  </div>

  <div v-else>
    <br />
    <p>Please click on a Location...</p>
  </div>
</template>

<script lang="ts">
import { LocationInfo } from "@/services/DataEntities";
import LocationDataService from "@/services/LocationDataService";
import { Component, Prop, Vue } from "vue-property-decorator";

@Component({
})
export default class LocationItem extends Vue {
  location = new LocationInfo(0, 0, 0, 0);
  message: string = "";
  submitted = false;
  public errorTitle = '';
  public errorMessage = '';
  public errorDescription = '';

  getLocation(id: number) {
    LocationDataService.get(id)
      .then((response) => {
        this.location = response;
        console.log(response);
      })
    .catch((e) => {
        this.handleError(e);
    });
  }

  updateLocation() {
    LocationDataService.update(this.location)
      .then((response) => {
        console.log(response.data);
        this.message = "The Location was updated successfully!";
      })
      .catch((e) => {
        this.handleError(e);
      });
  }

  deleteLocation() {
    LocationDataService.delete(this.location.id)
      .then((response) => {
        console.log(response.data);
        this.$router.push({ name: "location" });
      })
      .catch((e) => {
        this.handleError(e);
      });
  }

  mounted() {
    this.message = "";
    this.getLocation(Number.parseInt(this.$router.currentRoute.params.id))
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
