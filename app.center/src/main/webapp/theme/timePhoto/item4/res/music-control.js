/**eventemitter3**/
"use strict";function Events(){}function EE(a,b,c){this.fn=a,this.context=b,this.once=c||!1}function EventEmitter(){this._events=new Events,this._eventsCount=0}var has=Object.prototype.hasOwnProperty,prefix="~";Object.create&&(Events.prototype=Object.create(null),(new Events).__proto__||(prefix=!1)),EventEmitter.prototype.eventNames=function(){var b,c,a=[];if(0===this._eventsCount)return a;for(c in b=this._events)has.call(b,c)&&a.push(prefix?c.slice(1):c);return Object.getOwnPropertySymbols?a.concat(Object.getOwnPropertySymbols(b)):a},EventEmitter.prototype.listeners=function(a,b){var c=prefix?prefix+a:a,d=this._events[c];if(b)return!!d;if(!d)return[];if(d.fn)return[d.fn];for(var e=0,f=d.length,g=new Array(f);f>e;e++)g[e]=d[e].fn;return g},EventEmitter.prototype.emit=function(a,b,c,d,e,f){var g=prefix?prefix+a:a;if(!this._events[g])return!1;var j,k,h=this._events[g],i=arguments.length;if(h.fn){switch(h.once&&this.removeListener(a,h.fn,void 0,!0),i){case 1:return h.fn.call(h.context),!0;case 2:return h.fn.call(h.context,b),!0;case 3:return h.fn.call(h.context,b,c),!0;case 4:return h.fn.call(h.context,b,c,d),!0;case 5:return h.fn.call(h.context,b,c,d,e),!0;case 6:return h.fn.call(h.context,b,c,d,e,f),!0}for(k=1,j=new Array(i-1);i>k;k++)j[k-1]=arguments[k];h.fn.apply(h.context,j)}else{var m,l=h.length;for(k=0;l>k;k++)switch(h[k].once&&this.removeListener(a,h[k].fn,void 0,!0),i){case 1:h[k].fn.call(h[k].context);break;case 2:h[k].fn.call(h[k].context,b);break;case 3:h[k].fn.call(h[k].context,b,c);break;case 4:h[k].fn.call(h[k].context,b,c,d);break;default:if(!j)for(m=1,j=new Array(i-1);i>m;m++)j[m-1]=arguments[m];h[k].fn.apply(h[k].context,j)}}return!0},EventEmitter.prototype.on=function(a,b,c){var d=new EE(b,c||this),e=prefix?prefix+a:a;return this._events[e]?this._events[e].fn?this._events[e]=[this._events[e],d]:this._events[e].push(d):(this._events[e]=d,this._eventsCount++),this},EventEmitter.prototype.once=function(a,b,c){var d=new EE(b,c||this,!0),e=prefix?prefix+a:a;return this._events[e]?this._events[e].fn?this._events[e]=[this._events[e],d]:this._events[e].push(d):(this._events[e]=d,this._eventsCount++),this},EventEmitter.prototype.removeListener=function(a,b,c,d){var e=prefix?prefix+a:a;if(!this._events[e])return this;if(!b)return 0===--this._eventsCount?this._events=new Events:delete this._events[e],this;var f=this._events[e];if(f.fn)f.fn!==b||d&&!f.once||c&&f.context!==c||(0===--this._eventsCount?this._events=new Events:delete this._events[e]);else{for(var g=0,h=[],i=f.length;i>g;g++)(f[g].fn!==b||d&&!f[g].once||c&&f[g].context!==c)&&h.push(f[g]);h.length?this._events[e]=1===h.length?h[0]:h:0===--this._eventsCount?this._events=new Events:delete this._events[e]}return this},EventEmitter.prototype.removeAllListeners=function(a){var b;return a?(b=prefix?prefix+a:a,this._events[b]&&(0===--this._eventsCount?this._events=new Events:delete this._events[b])):(this._events=new Events,this._eventsCount=0),this},EventEmitter.prototype.off=EventEmitter.prototype.removeListener,EventEmitter.prototype.addListener=EventEmitter.prototype.on,EventEmitter.prototype.setMaxListeners=function(){return this},EventEmitter.prefixed=prefix,"undefined"!=typeof module&&(module.exports=EventEmitter);

/**music control**/
var ee = new EventEmitter();
var behaviour = 'play';
ee.on('everphoto.music.loaded', function () {
  if (['play', 'pause'].indexOf(behaviour) > -1) {
    musicAction[behaviour]();
  }
});
function pausePlayMusic() {
  behaviour = 'pause'; // 这里是为了 everphoto.music.loaded 之后，以 ee 的形式来执行对应的 function

  if (!isSearchExists('epcallback=instantshare') && isApple() && isPreviewPage() && window._someStrangeStatus && window._someStrangeStatus !== 'backToNormal') {
    behaviour = null;
    window._someStrangeStatus = 'backToNormal';
    return;
  }

  window.musicAction.pause();
}
function startPlayMusic() {
  behaviour = 'play'; // 同 pausePlayMusic
  window.musicAction.play();
}
window.startPlayMusic = startPlayMusic
window.pausePlayMusic = pausePlayMusic

function isApple () {
  return navigator.userAgent.toLowerCase().indexOf('ios') > -1;
}

function isPreviewPage () {
  var loc = location.href;
  return loc.indexOf('media_id') > -1 ||
    loc.indexOf('resource_id') > -1;
}

function isSearchExists (search) {
  search = search.toLowerCase();
  return location.search.toLowerCase().substr(1).split('&').indexOf(search) > -1;
}
