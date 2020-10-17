(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-019f0c5a","chunk-7e93d47e"],{1292:function(t,e,r){},"4de4":function(t,e,r){"use strict";var o=r("23e7"),a=r("b727").filter,s=r("1dde"),n=r("ae40"),i=s("filter"),c=n("filter");o({target:"Array",proto:!0,forced:!i||!c},{filter:function(t){return a(this,t,arguments.length>1?arguments[1]:void 0)}})},"66b3":function(t,e,r){"use strict";var o=r("9f0c"),a=r.n(o);a.a},"9f0c":function(t,e,r){},adb8:function(t,e,r){"use strict";r.r(e);var o=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"list row"},[r("div",[r("b-modal",{attrs:{id:"modalErrPopover",size:"xl",title:"Error occured","ok-only":""}},[r("p",[r("label",[r("strong",[t._v(t._s(t.errorTitle))])]),r("pre",[t._v(t._s(t.errorMessage))]),t.errorDescription?r("b-button",{directives:[{name:"b-toggle",rawName:"v-b-toggle.collapse-3",modifiers:{"collapse-3":!0}}],staticClass:"page-link button-small"},[t._v("Details")]):t._e(),r("b-collapse",{attrs:{id:"collapse-3"}},[r("b-card",[r("pre",[t._v(t._s(t.errorDescription))])])],1)],1)]),r("b-modal",{attrs:{id:"modalInfoPopover",size:"xl",title:"Status Info","ok-only":""}},[r("p",[r("label",[r("strong",[t._v(t._s(t.statusInfoTitle))])]),r("pre",[t._v(t._s(t.statusInfoMessage))])])])],1),r("div",{staticClass:"col-md-8"},[r("b-form-group",{attrs:{label:""}},[r("label",[t._v(" Query routes "),r("div",{staticClass:"button-transparent",attrs:{id:"popover-target-1"}},[r("b-icon",{staticClass:"icon-default",attrs:{icon:"question-circle"}}),r("b-icon",{staticClass:"icon-hover",attrs:{icon:"question-circle-fill"}})],1),r("b-popover",{attrs:{target:"popover-target-1",triggers:"hover",placement:"rightbottom"},scopedSlots:t._u([{key:"title",fn:function(){return[t._v("Query expressions help")]},proxy:!0}])},[r("strong",[t._v("Fields:")]),r("p",[t._v("name, id, distance, created, coordX, coordY, fromX, fromY, fromZ, toX, toY, toZ")]),r("strong",[t._v("Filter operations:")]),r("p",[t._v("(, ), [, ], =, !=, >, <, >=, <=, not, and, or, contains, -, +, *, /")]),r("strong",[t._v("Sorting:")]),r("p",[t._v("comma-separated fields, leading '~' to invert sorting")]),r("strong",[t._v("Example:")]),r("p",[t._v('filter: fromX > toX - 10 and name contains "route"')]),r("p",[t._v("sort: id, ~distance")])])],1),r("input",{directives:[{name:"model",rawName:"v-model",value:t.filter,expression:"filter"}],staticClass:"form-control",attrs:{id:"filter-expr",type:"text",placeholder:"Filter expression.."},domProps:{value:t.filter},on:{input:function(e){e.target.composing||(t.filter=e.target.value)}}})]),r("b-form",{attrs:{inline:""}},[r("input",{directives:[{name:"model",rawName:"v-model",value:t.sort,expression:"sort"}],staticClass:"form-control mb-2 mr-sm-2 mb-sm-0",staticStyle:{flex:"1"},attrs:{type:"text",placeholder:"Order by.."},domProps:{value:t.sort},on:{input:function(e){e.target.composing||(t.sort=e.target.value)}}}),r("div",{staticClass:"input-group-append"},[r("button",{staticClass:"btn btn-outline-dark",attrs:{type:"button"},on:{click:t.findByParams}},[t._v("Search")])])]),r("br"),r("b-form-group",{attrs:{label:"Special actions"}},[r("b-form",{attrs:{inline:""}},[r("input",{directives:[{name:"model",rawName:"v-model",value:t.distance,expression:"distance"}],staticClass:"form-control mb-2 mr-sm-2 mb-sm-0",staticStyle:{flex:"1"},attrs:{id:"filter-expr",type:"text",placeholder:"Distance"},domProps:{value:t.distance},on:{input:function(e){e.target.composing||(t.distance=e.target.value)}}}),r("button",{staticClass:"m-3 btn btn-sm btn-outline-danger",attrs:{type:"button"},on:{click:t.delOneByDst}},[t._v("Delete one by distance")])]),r("b-form",{attrs:{inline:""}},[r("input",{directives:[{name:"model",rawName:"v-model",value:t.exactName,expression:"exactName"}],staticClass:"form-control mb-2 mr-sm-2 mb-sm-0",staticStyle:{flex:"1"},attrs:{id:"filter-expr",type:"text",placeholder:"Name"},domProps:{value:t.exactName},on:{input:function(e){e.target.composing||(t.exactName=e.target.value)}}}),r("button",{staticClass:"m-3 btn btn-sm btn-outline-dark",attrs:{type:"button"},on:{click:t.getByName}},[t._v("Find by name")])])],1)],1),r("div",{staticClass:"col-md-6"},[r("h4",[t._v("Routes List")]),r("p",{attrs:{id:"p-search-result",hidden:""}},[t._v('Search result for filter: " '+t._s(this.lastSearchFilter)+' " and sort: "'+t._s(this.lastSearchSort)+' ".')]),r("nav",{attrs:{"aria-label":"Page navigation example"}},[r("ul",{staticClass:"pagination"},[r("li",{staticClass:"page-item"},[t.routes.isFirstPage?t._e():r("button",{staticClass:"page-link",attrs:{type:"button"},on:{click:function(e){return t.paginate(0)}}},[t._v(" "+t._s(1)+" ")])]),r("li",{staticClass:"page-item"},[t.routes.pageNumber>2?r("button",{staticClass:"page-link",attrs:{type:"button"}},[t._v(" ... ")]):t._e()]),r("li",{staticClass:"page-item"},[t.routes.pageNumber>1?r("button",{staticClass:"page-link",attrs:{type:"button"},on:{click:function(e){return t.paginate(t.routes.pageNumber-1)}}},[t._v(" "+t._s(t.routes.pageNumber)+" ")]):t._e()]),r("li",{staticClass:"page-item"},[r("button",{staticClass:"page-link current",attrs:{type:"button"},on:{click:function(e){return t.paginate(t.routes.pageNumber)}}},[t._v(" "+t._s(t.routes.pageNumber+1)+" ")])]),r("li",{staticClass:"page-item"},[t.routes.pageNumber<t.routes.pagesCount-2?r("button",{staticClass:"page-link",attrs:{type:"button"},on:{click:function(e){return t.paginate(t.routes.pageNumber+1)}}},[t._v(" "+t._s(t.routes.pageNumber+2)+" ")]):t._e()]),r("li",{staticClass:"page-item"},[t.routes.pageNumber<t.routes.pagesCount-3?r("button",{staticClass:"page-link",attrs:{type:"button"}},[t._v(" ... ")]):t._e()]),r("li",{staticClass:"page-item"},[t.routes.isLastPage?t._e():r("button",{staticClass:"page-link",attrs:{type:"button"},on:{click:function(e){return t.paginate(t.routes.pagesCount-1)}}},[t._v(" "+t._s(t.routes.pagesCount)+" ")])])])]),r("ul",{staticClass:"list-group"},t._l(t.routes.routes,(function(e,o){return r("li",{key:o,staticClass:"list-group-item",class:{active:o==t.currentIndex},on:{click:function(r){return t.setActiveRoute(e,o)}}},[t._v(t._s(e.name))])})),0),r("div",[r("nav",{attrs:{"aria-label":"Page navigation example"}},[r("ul",{staticClass:"pagination"},[r("li",{staticClass:"page-item"},[r("button",{staticClass:"page-link",attrs:{type:"button",disabled:t.routes.isFirstPage},on:{click:function(e){return t.paginate(0)}}},[t._v(" |< ")])]),r("li",{staticClass:"page-item"},[r("button",{staticClass:"page-link",attrs:{type:"button",disabled:t.routes.isFirstPage},on:{click:function(e){return t.paginate(t.routes.pageNumber-1)}}},[t._v(" < ")])]),r("li",{staticClass:"page-item"},[r("button",{staticClass:"page-link",attrs:{type:"button"},on:{click:function(e){return t.paginate(t.routes.pageNumber)}}},[t._v(" "+t._s(t.routes.pageNumber+1)+" ")])]),r("li",{staticClass:"page-item"},[r("button",{staticClass:"page-link",attrs:{type:"button",disabled:t.routes.isLastPage},on:{click:function(e){return t.paginate(t.routes.pageNumber+1)}}},[t._v(" > ")])]),r("li",{staticClass:"page-item"},[r("button",{staticClass:"page-link",attrs:{type:"button",disabled:t.routes.isLastPage},on:{click:function(e){return t.paginate(t.routes.pagesCount-1)}}},[t._v(" >| ")])])])])])]),r("div",[r("button",{staticClass:"btn btn-link",attrs:{id:"btn-get-summ-dst"},on:{click:t.getSummaryDst}},[t._v(" Calc summary distance")]),r("label",{attrs:{id:"label-summ-dst",hidden:"true"},on:{click:t.getSummaryDst}},[t._v("Summary distance: ")]),r("b",{attrs:{id:"summ-dst"},on:{click:t.getSummaryDst}}),r("hr"),t.currentRoute?r("div",[r("h4",[t._v("Route")]),r("div",[t._m(0),t._v(" "+t._s(t.currentRoute.id)+" ")]),r("div",[t._m(1),t._v(" "+t._s(t.currentRoute.name)+" ")]),r("div",[t._m(2),t._v(" "+t._s(t.currentRoute.creationDateString)+" ")]),r("div",[t._m(3),t._v(" "+t._s(t.currentRoute.distance)+" ")]),r("div",[t._m(4),t._v(" "+t._s(t.currentRoute.coordinates.x)+" ")]),r("div",[t._m(5),t._v(" "+t._s(t.currentRoute.coordinates.y)+" ")]),r("div",[t._m(6),t._v(" "+t._s(t.currentRoute.from.x)+" ")]),r("div",[t._m(7),t._v(" "+t._s(t.currentRoute.from.y)+" ")]),r("div",[t._m(8),t._v(" "+t._s(t.currentRoute.from.z)+" ")]),r("div",[t._m(9),t._v(" "+t._s(t.currentRoute.to.x)+" ")]),r("div",[t._m(10),t._v(" "+t._s(t.currentRoute.to.y)+" ")]),r("div",[t._m(11),t._v(" "+t._s(t.currentRoute.to.z)+" ")]),r("div",[r("router-link",{staticClass:"badge badge-warning mb-2 mr-sm-2 mb-sm-0",attrs:{to:"'/routes/' + currentRoute.id"}},[t._v("Edit")])],1)]):r("div",[r("br"),r("p",[t._v("Please click on a Route...")])])])])},a=[function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("label",[r("strong",[t._v("Id:")])])},function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("label",[r("strong",[t._v("Name:")])])},function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("label",[r("strong",[t._v("Creation date:")])])},function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("label",[r("strong",[t._v("Distance:")])])},function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("label",[r("strong",[t._v("Coordinates X:")])])},function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("label",[r("strong",[t._v("Coordinates Y:")])])},function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("label",[r("strong",[t._v("Source location X:")])])},function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("label",[r("strong",[t._v("Source location Y:")])])},function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("label",[r("strong",[t._v("Source location Z:")])])},function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("label",[r("strong",[t._v("Destination location X:")])])},function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("label",[r("strong",[t._v("Destination location Y:")])])},function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("label",[r("strong",[t._v("Destination location Z:")])])}],s=(r("99af"),r("4de4"),r("d3b7"),r("25f0"),r("d4ec")),n=r("bee2"),i=r("262e"),c=r("2caf"),l=r("9ab4"),u=r("60a3"),m=r("41f3"),d=r("c5dd"),p=function(t){Object(i["a"])(r,t);var e=Object(c["a"])(r);function r(){var t;return Object(s["a"])(this,r),t=e.apply(this,arguments),t.routes=new m["b"]([],0,0,0,0),t.title="",t.currentRoute=null,t.currentIndex=-1,t.filter="",t.sort="",t.summDst=0,t.distance=0,t.exactName="",t.errorTitle="",t.errorMessage="",t.errorDescription="",t.statusInfoMessage="",t.statusInfoTitle="",t.lastSearchFilter="",t.lastSearchSort="",t.pageSize=5,t}return Object(n["a"])(r,[{key:"paginate",value:function(t){var e=this;this.currentIndex=-1,m["c"].findByParams(this.filter,this.sort,this.pageSize,t).then((function(t){e.routes=t,console.log(t)})).catch((function(t){e.handleError(t)}))}},{key:"refreshList",value:function(){this.paginate(this.routes.pageNumber),this.currentRoute=null,this.currentIndex=-1,document.getElementById("summ-dst").innerText=""}},{key:"setActiveRoute",value:function(t,e){this.currentRoute=t,this.currentIndex=e}},{key:"delOneByDst",value:function(){var t=this;m["c"].delOneByDst(this.distance).then((function(e){document.getElementById("summ-dst").innerText="",document.getElementById("label-summ-dst").hidden=!0,document.getElementById("btn-get-summ-dst").hidden=!1,t.statusInfoTitle=e.status,t.statusInfoMessage=e.message,t.$bvModal.show("modalInfoPopover"),t.refreshList()})).catch((function(e){t.handleError(e)}))}},{key:"getSummaryDst",value:function(){var t=this;m["c"].getSummaryDst().then((function(e){t.summDst=e,document.getElementById("summ-dst").innerText=t.summDst.toString(),document.getElementById("btn-get-summ-dst").hidden=!0,document.getElementById("label-summ-dst").hidden=!1})).catch((function(e){t.handleError(e)}))}},{key:"findByParams",value:function(){var t=this;m["c"].findByParams(this.filter,this.sort,this.pageSize,0).then((function(e){t.routes=e,t.lastSearchFilter=t.filter,t.lastSearchSort=t.sort,""==t.filter&&""==t.sort?document.getElementById("p-search-result").hidden=!0:document.getElementById("p-search-result").hidden=!1})).catch((function(e){t.handleError(e)}))}},{key:"getByName",value:function(){var t=this;m["c"].getByName(this.exactName).then((function(e){t.routes=e})).catch((function(e){t.handleError(e)}))}},{key:"clearSearchMessage",value:function(){document.getElementById("p-search-result").hidden=!0}},{key:"mounted",value:function(){this.paginate(0)}},{key:"handleError",value:function(t){console.log(t),t.remote?(this.errorTitle="".concat(t.response.status," ").concat(t.response.statusText),this.errorMessage=t.remote.message,this.errorDescription=t.remote.stackTrace):(this.errorTitle="Unexpected error",this.errorMessage=t.message,this.errorDescription=t.stack),this.$bvModal.show("modalErrPopover")}}]),r}(u["b"]);p=Object(l["a"])([Object(u["a"])({components:{RouteItem:d["default"]}})],p);var v=p,g=v,f=(r("66b3"),r("2877")),b=Object(f["a"])(g,o,a,!1,null,"3040c5c7",null);e["default"]=b.exports},b335:function(t,e,r){"use strict";var o=r("1292"),a=r.n(o);a.a},c5dd:function(t,e,r){"use strict";r.r(e);var o=function(){var t=this,e=t.$createElement,r=t._self._c||e;return t.route?r("div",{staticClass:"edit-form"},[r("b-modal",{attrs:{id:"modalPopover",size:"xl",title:"Error occured","ok-only":""}},[r("p",[r("label",[r("strong",[t._v(t._s(t.errorTitle))])]),r("pre",[t._v(t._s(t.errorMessage))]),t.errorDescription?r("b-button",{directives:[{name:"b-toggle",rawName:"v-b-toggle.collapse-3",modifiers:{"collapse-3":!0}}],staticClass:"page-link button-small"},[t._v("Details")]):t._e(),r("b-collapse",{attrs:{id:"collapse-3"}},[r("b-card",[r("pre",[t._v(t._s(t.errorDescription))])])],1)],1)]),r("h4",[t._v("Route")]),this.message?r("p",{attrs:{id:"message"}},[t._v(t._s(this.message))]):t._e(),t.submitted?t._e():r("div",[r("div",{staticClass:"form-group"},[r("label",{attrs:{for:"name"}},[t._v("Name")]),r("input",{directives:[{name:"model",rawName:"v-model",value:t.route.name,expression:"route.name"}],staticClass:"form-control",attrs:{type:"text",id:"name",required:"",name:"name"},domProps:{value:t.route.name},on:{input:function(e){e.target.composing||t.$set(t.route,"name",e.target.value)}}})]),r("div",{staticClass:"form-group"},[r("label",{attrs:{for:"distance"}},[t._v("Distance")]),r("input",{directives:[{name:"model",rawName:"v-model",value:t.route.distance,expression:"route.distance"}],staticClass:"form-control",attrs:{id:"distance",required:"",name:"distance"},domProps:{value:t.route.distance},on:{input:function(e){e.target.composing||t.$set(t.route,"distance",e.target.value)}}})]),r("div",{staticClass:"form-group"},[r("label",{attrs:{for:"coordX"}},[t._v("Coordinate X")]),r("input",{directives:[{name:"model",rawName:"v-model",value:t.route.coordinates.x,expression:"route.coordinates.x"}],staticClass:"form-control",attrs:{id:"coordX",required:"",name:"coordX"},domProps:{value:t.route.coordinates.x},on:{input:function(e){e.target.composing||t.$set(t.route.coordinates,"x",e.target.value)}}})]),r("div",{staticClass:"form-group"},[r("label",{attrs:{for:"coordY"}},[t._v("Coordinate Y")]),r("input",{directives:[{name:"model",rawName:"v-model",value:t.route.coordinates.y,expression:"route.coordinates.y"}],staticClass:"form-control",attrs:{id:"coordY",required:"",name:"coordY"},domProps:{value:t.route.coordinates.y},on:{input:function(e){e.target.composing||t.$set(t.route.coordinates,"y",e.target.value)}}})]),r("div",{staticClass:"form-group"},[r("label",{attrs:{for:"fromLocX"}},[t._v("Source location X")]),r("input",{directives:[{name:"model",rawName:"v-model",value:t.route.from.x,expression:"route.from.x"}],staticClass:"form-control",attrs:{id:"fromLocX",required:"",name:"fromLocX"},domProps:{value:t.route.from.x},on:{input:function(e){e.target.composing||t.$set(t.route.from,"x",e.target.value)}}})]),r("div",{staticClass:"form-group"},[r("label",{attrs:{for:"fromLocY"}},[t._v("Source location Y")]),r("input",{directives:[{name:"model",rawName:"v-model",value:t.route.from.y,expression:"route.from.y"}],staticClass:"form-control",attrs:{id:"fromLocY",required:"",name:"fromLocY"},domProps:{value:t.route.from.y},on:{input:function(e){e.target.composing||t.$set(t.route.from,"y",e.target.value)}}})]),r("div",{staticClass:"form-group"},[r("label",{attrs:{for:"fromLocZ"}},[t._v("Source location Z")]),r("input",{directives:[{name:"model",rawName:"v-model",value:t.route.from.z,expression:"route.from.z"}],staticClass:"form-control",attrs:{id:"fromLocZ",required:"",name:"fromLocZ"},domProps:{value:t.route.from.z},on:{input:function(e){e.target.composing||t.$set(t.route.from,"z",e.target.value)}}})]),r("div",{staticClass:"form-group"},[r("label",{attrs:{for:"toLocX"}},[t._v("Destination location X")]),r("input",{directives:[{name:"model",rawName:"v-model",value:t.route.to.x,expression:"route.to.x"}],staticClass:"form-control",attrs:{id:"toLocX",required:"",name:"toLocX"},domProps:{value:t.route.to.x},on:{input:function(e){e.target.composing||t.$set(t.route.to,"x",e.target.value)}}})]),r("div",{staticClass:"form-group"},[r("label",{attrs:{for:"toLocY"}},[t._v("Destination location Y")]),r("input",{directives:[{name:"model",rawName:"v-model",value:t.route.to.y,expression:"route.to.y"}],staticClass:"form-control",attrs:{id:"toLocY",required:"",name:"toLocY"},domProps:{value:t.route.to.y},on:{input:function(e){e.target.composing||t.$set(t.route.to,"y",e.target.value)}}})]),r("div",{staticClass:"form-group"},[r("label",{attrs:{for:"toLocZ"}},[t._v("Destination location Z")]),r("input",{directives:[{name:"model",rawName:"v-model",value:t.route.to.z,expression:"route.to.z"}],staticClass:"form-control",attrs:{id:"toLocZ",required:"",name:"toLocZ"},domProps:{value:t.route.to.z},on:{input:function(e){e.target.composing||t.$set(t.route.to,"z",e.target.value)}}})]),r("button",{staticClass:"btn btn-danger mr-2",on:{click:t.deleteRoute}},[t._v(" Delete ")]),r("button",{staticClass:"btn btn-success",on:{click:t.updateRoute}},[t._v(" Update ")])]),r("p",{attrs:{id:"message"}},[t._v(t._s(t.message))])],1):r("div",[r("br"),r("p",[t._v("Please click on a Route...")])])},a=[],s=(r("99af"),r("a9e3"),r("25eb"),r("d4ec")),n=r("bee2"),i=r("262e"),c=r("2caf"),l=r("9ab4"),u=r("60a3"),m=r("41f3"),d=function(t){Object(i["a"])(r,t);var e=Object(c["a"])(r);function r(){var t;return Object(s["a"])(this,r),t=e.apply(this,arguments),t.route=m["a"].makeEmptyRoute(),t.message="",t.submitted=!1,t.errorTitle="",t.errorMessage="",t.errorDescription="",t}return Object(n["a"])(r,[{key:"getRoute",value:function(t){var e=this;m["c"].get(t).then((function(t){e.route=t,console.log(t)})).catch((function(t){e.handleError(t)}))}},{key:"updateRoute",value:function(){var t=this;m["c"].update(this.route).then((function(e){console.log(e.data),t.message="The Route was updated successfully!"})).catch((function(e){t.handleError(e)}))}},{key:"deleteRoute",value:function(){var t=this;m["c"].delete(this.route.id).then((function(e){console.log(e.data),t.$router.push({name:"routes"})})).catch((function(e){t.handleError(e)}))}},{key:"mounted",value:function(){this.message="",this.getRoute(Number.parseInt(this.$router.currentRoute.params.id))}},{key:"handleError",value:function(t){console.log(t),t.remote?(this.errorTitle="".concat(t.response.status," ").concat(t.response.statusText),this.errorMessage=t.remote.message,this.errorDescription=t.remote.stackTrace):(this.errorTitle="Unexpected error",this.errorMessage=t.message,this.errorDescription=t.stack),this.$bvModal.show("modalPopover")}}]),r}(u["b"]);d=Object(l["a"])([Object(u["a"])({})],d);var p=d,v=p,g=(r("b335"),r("2877")),f=Object(g["a"])(v,o,a,!1,null,"3fa51478",null);e["default"]=f.exports}}]);
//# sourceMappingURL=chunk-019f0c5a.f21180c5.js.map