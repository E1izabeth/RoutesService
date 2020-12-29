(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-cc0ced12"],{"0e0d":function(t,e,o){"use strict";var a=o("d4ec"),n=o("262e"),i=o("2caf"),r=o("0414"),s=o("d733"),c=o("d909"),l=r["a"].locations,u=function(t){Object(n["a"])(o,t);var e=Object(i["a"])(o);function o(){return Object(a["a"])(this,o),e.call(this,l,"/locations",c["a"].parse,c["b"].parseLocations)}return o}(s["a"]);e["a"]=new u},"5ce4":function(t,e,o){"use strict";o.r(e);var a=function(){var t=this,e=t.$createElement,o=t._self._c||e;return t.location?o("div",{staticClass:"edit-form"},[o("b-modal",{attrs:{id:"modalPopover",size:"xl",title:"Error occured","ok-only":""}},[o("p",[o("label",[o("strong",[t._v(t._s(t.errorTitle))])]),o("pre",[t._v(t._s(t.errorMessage))]),t.errorDescription?o("b-button",{directives:[{name:"b-toggle",rawName:"v-b-toggle.collapse-3",modifiers:{"collapse-3":!0}}],staticClass:"page-link button-small"},[t._v("Details")]):t._e(),o("b-collapse",{attrs:{id:"collapse-3"}},[o("b-card",[o("pre",[t._v(t._s(t.errorDescription))])])],1)],1)]),o("h4",[t._v("Location")]),this.message?o("p",{attrs:{id:"message"}},[t._v(t._s(this.message))]):t._e(),t.submitted?t._e():o("div",[o("div",{staticClass:"form-group"},[o("label",{attrs:{for:"Id"}},[t._v("Id")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.location.id,expression:"location.id"}],staticClass:"form-control",attrs:{id:"id",required:"",name:"id",disabled:""},domProps:{value:t.location.id},on:{input:function(e){e.target.composing||t.$set(t.location,"id",e.target.value)}}})]),o("div",{staticClass:"form-group"},[o("label",{attrs:{for:"X"}},[t._v("X")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.location.x,expression:"location.x"}],staticClass:"form-control",attrs:{id:"X",required:"",name:"X"},domProps:{value:t.location.x},on:{input:function(e){e.target.composing||t.$set(t.location,"x",e.target.value)}}})]),o("div",{staticClass:"form-group"},[o("label",{attrs:{for:"Y"}},[t._v("Y")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.location.y,expression:"location.y"}],staticClass:"form-control",attrs:{id:"Y",required:"",name:"Y"},domProps:{value:t.location.y},on:{input:function(e){e.target.composing||t.$set(t.location,"y",e.target.value)}}})]),o("div",{staticClass:"form-group"},[o("label",{attrs:{for:"Z"}},[t._v("Z")]),o("input",{directives:[{name:"model",rawName:"v-model",value:t.location.z,expression:"location.z"}],staticClass:"form-control",attrs:{id:"Z",required:"",name:"Z"},domProps:{value:t.location.z},on:{input:function(e){e.target.composing||t.$set(t.location,"z",e.target.value)}}})]),o("button",{staticClass:"btn btn-danger mr-2",on:{click:t.deleteLocation}},[t._v(" Delete ")]),o("button",{staticClass:"btn btn-success",on:{click:t.updateLocation}},[t._v(" Update ")])]),o("p",{attrs:{id:"message"}},[t._v(t._s(t.message))])],1):o("div",[o("br"),o("p",[t._v("Please click on a Location...")])])},n=[],i=(o("99af"),o("a9e3"),o("25eb"),o("d4ec")),r=o("bee2"),s=o("262e"),c=o("2caf"),l=o("9ab4"),u=o("d909"),d=o("0e0d"),p=o("60a3"),m=function(t){Object(s["a"])(o,t);var e=Object(c["a"])(o);function o(){var t;return Object(i["a"])(this,o),t=e.apply(this,arguments),t.location=new u["a"](0,0,0,0),t.message="",t.submitted=!1,t.errorTitle="",t.errorMessage="",t.errorDescription="",t}return Object(r["a"])(o,[{key:"getLocation",value:function(t){var e=this;d["a"].get(t).then((function(t){e.location=t,console.log(t)})).catch((function(t){e.handleError(t)}))}},{key:"updateLocation",value:function(){var t=this;d["a"].update(this.location).then((function(e){console.log(e.data),t.message="The Location was updated successfully!"})).catch((function(e){t.handleError(e)}))}},{key:"deleteLocation",value:function(){var t=this;d["a"].delete(this.location.id).then((function(e){console.log(e.data),t.$router.push({name:"location"})})).catch((function(e){t.handleError(e)}))}},{key:"mounted",value:function(){this.message="",this.getLocation(Number.parseInt(this.$router.currentRoute.params.id))}},{key:"handleError",value:function(t){console.log(t),t.remote?(this.errorTitle="".concat(t.response.status," ").concat(t.response.statusText),this.errorMessage=t.remote.message,this.errorDescription=t.remote.stackTrace):(this.errorTitle="Unexpected error",this.errorMessage=t.message,this.errorDescription=t.stack),this.$bvModal.show("modalPopover")}}]),o}(p["b"]);m=Object(l["a"])([Object(p["a"])({})],m);var v=m,h=v,f=(o("fdbd"),o("2877")),g=Object(f["a"])(h,a,n,!1,null,"10460ff9",null);e["default"]=g.exports},"6ef8":function(t,e,o){},d733:function(t,e,o){"use strict";o.d(e,"a",(function(){return r}));o("99af");var a=o("d4ec"),n=o("bee2"),i=o("56f6"),r=function(){function t(e,o,n,i){Object(a["a"])(this,t),this._http=e,this._url=o,this._entityCtor=n,this._collectionCtor=i}return Object(n["a"])(t,[{key:"getAll",value:function(){return this._http.get(this._url,this._collectionCtor)}},{key:"get",value:function(t){return this._http.get(this._url+"/".concat(t),this._entityCtor)}},{key:"create",value:function(t){return this._http.post(this._url+"?action=add",i["a"].create(t.toXmlSpecElement.bind(t)),this._entityCtor)}},{key:"update",value:function(t){return this._http.put(this._url+"/".concat(t.id),t)}},{key:"delete",value:function(t){return this._http.delete(this._url+"/".concat(t))}},{key:"findByParams",value:function(t,e,o,a){return this._http.get(this._url+"?filter=".concat(t?encodeURIComponent(t):"","&sort=").concat(e?encodeURIComponent(e):"")+"&page-size=".concat(o?encodeURIComponent(o):"","&page-num=").concat(a?encodeURIComponent(a):""),this._collectionCtor)}}]),t}()},fdbd:function(t,e,o){"use strict";var a=o("6ef8"),n=o.n(a);n.a}}]);
//# sourceMappingURL=chunk-cc0ced12.d01fe8f6.js.map