import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/routes/create",
    name: "createRoute",
    component: () => import("../views/AddRoute.vue")
  },
  {
    path: "/locations/create",
    name: "createLocation",
    component: () => import("../views/AddLocation.vue")
  },
  {
    path: "/routes",
    alias: "/",
    name: "routes",
    component: () => import("../views/RoutesList.vue")
  },
  {
    path: "/locations",
    name: "locations",
    component: () => import("../views/LocationsList.vue")
  },
  {
    path: "/routes/:id",
    name: "route-details",
    component: () => import("../views/RouteItem.vue")
  },
  {
    path: "/locations/:id",
    name: "location-details",
    component: () => import("../views/LocationItem.vue")
  },
  {
    path: "/navigator",
    name: "navigator",
    component: () => import("../views/Navigator.vue")
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
