webpackJsonp([14],{"5Ihq":function(e,t,c){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=c("Dd8w"),s=c.n(n),a=c("NYxO"),i={name:"fcservice",methods:{minusVIP:function(){this.VIPnum--},addVIP:function(){this.VIPnum++},changeUrl:function(){window.location.href="/wechat/view/ruleDeclaration"}},computed:s()({},Object(a.mapState)(["carType","sysAreaConfigDto"]),{VIPnum:{get:function(){return this.$store.state.VIPnum},set:function(e){if(2!==this.carType.modelNum&&e>4)return void this.$createToast({time:1e3,type:"warn",mask:!0,txt:"赠送服务人数已超过该车型(4人)限制"}).show();this.$store.commit("SET_TOPSERVICE",{name:"num",val:e})}},checked:{get:function(){return this.$store.state.topService},set:function(e){this.$store.commit("SET_TOPSERVICE",{name:"checked",val:e})}}})},r={render:function(){var e=this,t=e.$createElement,c=e._self._c||t;return c("div",{staticClass:"fcservice",class:{active:e.checked}},[c("div",{staticClass:"checkBtn"},[c("div",{staticClass:"ui-checkbox-label"},[c("label",[c("input",{directives:[{name:"model",rawName:"v-model",value:e.checked,expression:"checked"}],attrs:{type:"checkbox",name:"coupon"},domProps:{checked:Array.isArray(e.checked)?e._i(e.checked,null)>-1:e.checked},on:{change:function(t){var c=e.checked,n=t.target,s=!!n.checked;if(Array.isArray(c)){var a=e._i(c,null);n.checked?a<0&&(e.checked=c.concat([null])):a>-1&&(e.checked=c.slice(0,a).concat(c.slice(a+1)))}else e.checked=s}}}),e._v(" "),e._m(0),e._v(" "),c("span",{staticClass:"text"},[e._v("赠送头等舱服务")]),e._v(" "),c("span",{staticClass:"href",on:{click:function(t){e.changeUrl()}}},[e._v("?")])])])]),e._v(" "),c("div",{staticClass:"addInput"},[c("span",{staticClass:"minus",class:{disable:e.VIPnum<=1},on:{click:function(t){e.checked&&e.VIPnum>1&&e.minusVIP()}}},[e._v("−")]),e._v(" "),c("input",{directives:[{name:"model",rawName:"v-model",value:e.VIPnum,expression:"VIPnum"}],attrs:{type:"text",readonly:"readonly",placeholder:"1",disabled:"true"},domProps:{value:e.VIPnum},on:{input:function(t){t.target.composing||(e.VIPnum=t.target.value)}}}),e._v(" "),c("span",{staticClass:"add",class:{disable:e.VIPnum>=6},on:{click:function(t){e.checked&&e.VIPnum<6&&e.addVIP()}}},[e._v("+")]),e._v("位\n    ")])])},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("span",{staticClass:"ui-checkbox-span"},[t("span",{staticClass:"ui-checkbox-inner"})])}]};var o=c("VU/8")(i,r,!1,function(e){c("M2Xt")},null,null);t.default=o.exports},M2Xt:function(e,t){}});
//# sourceMappingURL=14.47717490f1f689e5f7b6.js.map