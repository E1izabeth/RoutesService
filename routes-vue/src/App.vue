<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <a class="navbar-brand">RoutesService</a>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <router-link class="nav-link" to="/routes">Routes</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/routes/create">Create Route</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/locations">Locations</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/locations/create">Create Location</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/navigator">Navigator</router-link>
        </li>
      </div>
      <div class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="../web">RawApi</a>
        </li>
      </div>
    </nav>

    <div class="container mt-3">
      <router-view />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class App extends Vue {}
</script>
