function HashMap() {
	/** Map大小* */
	var size = 0;
	/** 对象* */
	var entry = new Object();
	/** Map的存put方法* */
	this.put = function(key, value) {
		if (!this.containsKey(key)) {
			size++;
			entry[key] = value;
		}
	}
	/** Map取get方法* */
	this.get = function(key) {
		return this.containsKey(key) ? entry[key] : null;
	}
	/** Map删除remove方法* */
	this.remove = function(key) {
		if (this.containsKey(key) && (delete entry[key])) {
			size--;
		}
	}
	/** 是否包含Key* */
	this.containsKey = function(key) {
		return (key in entry);
	}
	/** 是否包含Value* */
	this.containsValue = function(value) {
		for (var prop in entry) {
			if (entry[prop] == value) {
				return true;
			}
		}
		return false;
	}
	/** 所有的Value* */
	this.values = function() {
		var values = new Array();
		for (var prop in entry) {
			values.push(entry[prop]);
		}
		return values;
	}
	/** 所有的 Key* */
	this.keys = function() {
		var keys = new Array();
		for (var prop in entry) {
			keys.push(prop);
		}
		return keys;
	}
	this.tojson = function(){
		var args = "{";
		var ay = new Array();
		for(var prop in entry){
			var a1 = entry[prop];
			if(a1 instanceof HashMap){
				ay.push('"'+prop+'":'+a1.tojson());
			}else{
				a1=a1?a1:"";
				ay.push('"'+prop+'":"'+a1+'"');
			}
		}
		args += ay.join(",")+"}";
		return args;
	}
	/** Map size* */
	this.size = function() {
		return size;
	}
	/** 清空Map* */
	this.clear = function() {
		size = 0;
		entry = new Object();
	}

}