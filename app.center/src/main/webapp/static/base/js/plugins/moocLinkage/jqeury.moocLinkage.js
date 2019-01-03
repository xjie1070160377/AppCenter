//多级联动

(function ($) {
    var container;

    function getData(url,level,key) {
        var data;
        $.ajax({
            url: url,
            async: false,
            data: { level: level, key: key },
            type: "post",
            dataType: "json",
            success: function (returnData) {
                data = returnData;
            }
        });
        return data;
    }

    var selectors = [];

    var selectedItems = [];

    function clear(startIndex) {
        for (var i = startIndex; i < selectors.length; i++) {
            if (selectors[i]) {
                selectors[i].remove();
                selectors[i] = null;
            }
        }
    }

    function drawSelect(level, key) {
        var newLevel = level + 1;
        var selector = selectors[newLevel];
        if (key == $.fn.moocLinkage.options.defaultItemsValue[level])
            clear(newLevel);
        if (!selector) {
            selector = $('<select class="' + $.fn.moocLinkage.options.styleClass + '"></select>');
            selectors[newLevel] = selector;
            selector.appendTo(container);
        }
        else {
            clear(newLevel + 1);
        }

        selector.empty();
        var data = getData($.fn.moocLinkage.options.url, level, key);
        if (data.length > 0) {
            if ($.fn.moocLinkage.options.enableDefaultItem) {
                var text = '<option ';
                if ($.fn.moocLinkage.options.defaultItemsValue[newLevel])
                    text += ' value="' + $.fn.moocLinkage.options.defaultItemsValue[newLevel] + '"';
                text += '>' + $.fn.moocLinkage.options.defaultItemsText[newLevel] + '</option>';
                selector.append(text);
            }
            $.each(data, function (i, item) {
                selector.append('<option value="' + (item.key ? item.key : item.Key) + '">' + (item.value ? item.value : item.Value) + '</option>');
            });

            selector.unbind('change').change(function (item) {
                selectedItems[newLevel] = $(this).val();
                if (newLevel < $.fn.moocLinkage.options.level - 1)
                    drawSelect(newLevel, $(this).val());
                if ($.fn.moocLinkage.options.onChange)
                    $.fn.moocLinkage.options.onChange(newLevel, $(this).val(), $(this).text());
            });
        }
        else
            clear(newLevel);
    }


    function setDefaultItem() {
        if ($.fn.moocLinkage.options.enableDefaultItem) {
            if (!$.isArray($.fn.moocLinkage.options.defaultItemsValue)) {
                var arr = [];
                var defaultVallue = $.fn.moocLinkage.options.defaultItemsValue;
                if (defaultVallue == null)
                    defaultVallue = '';
                var i = $.fn.moocLinkage.options.level;
                while (i--) arr.push(defaultVallue);
                $.fn.moocLinkage.options.defaultItemsValue = arr;
            }
            else if ($.fn.moocLinkage.options.defaultItemsValue.length < $.fn.moocLinkage.options.level) {
                var less = $.fn.moocLinkage.options.level - $.fn.moocLinkage.options.defaultItemsValue.length;
                while (less--)
                    $.fn.moocLinkage.options.defaultItemsValue.push('');
            }

            if (!$.isArray($.fn.moocLinkage.options.defaultItemsText)) {
                var arr = [];
                var defaultText = $.fn.moocLinkage.options.defaultItemsText;
                if (defaultText == null)
                    defaultText = '请选择';
                var i = $.fn.moocLinkage.options.level;
                while (i--) arr.push(defaultText);
                $.fn.moocLinkage.options.defaultItemsText = arr;
            }
            else {
                var itemLength = $.fn.moocLinkage.options.defaultItemsText.length;
                if (itemLength < $.fn.moocLinkage.options.level) {
                    var less = $.fn.moocLinkage.options.level - itemLength;
                    while (less--)
                        $.fn.moocLinkage.options.defaultItemsText.push('请选择');
                }
            }
        }
    }

    $.fn.moocLinkage = function (options, params) {
        /// <param name="params" type="object">$.fn.moocLinkage.options</param>

        if (typeof options == "string") {
            return $.fn.moocLinkage.methods[options](this, params);
        }

        container = $(this);
        $.fn.moocLinkage.options = $.extend({}, $.fn.moocLinkage.options, options);
        setDefaultItem();
        drawSelect(-1);
        return $;
    }

    $.fn.moocLinkage.options = {
        level: 1,//级数
        url: null,//调用地址
        selectorWidth: 120,//select框宽度
        styleClass: '',//select框样式
        enableDefaultItem: false,//是否显示默认项（即未选中时的项）
        defaultItemsText: [],//默认文本，可以是数组，也可以是统一的值
        defaultItemsValue: [],//默认值，可以是数组，也可以是统一的值
        onChange:null//select 的change事件
    };


    $.fn.moocLinkage.methods = {
        value: function (jquery,level) {
            return selectedItems[level];
        }
    }

})(jQuery);