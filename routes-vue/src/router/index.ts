import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/routes/create",
    name: "create",
    component: () => import("../views/AddRoute.vue")
  },
  {
    path: "/routes",
    alias: "/",
    name: "routes",
    component: () => import("../views/RoutesList.vue")
  },
  {
    path: "/routes/:id",
    name: "route-details",
    component: () => import("../views/RouteItem.vue")
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
