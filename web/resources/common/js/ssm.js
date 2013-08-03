/**
 * =================== Bootstrap batches.
 */
!function($) {
	$.fn.tooltip.Constructor.prototype.applyPlacement = function(offset, placement) {

		var $tip = this.tip();
		var width = $tip[0].offsetWidth, height = $tip[0].offsetHeight;
		var actualWidth, actualHeight, delta, replace;

		$tip.offset(offset).addClass(placement).addClass('in');
		// Add css to tooltip.@see http://stackoverflow.com/questions/12170357/
		if (this.options.cssClass) {
			$tip.addClass(this.options.cssClass);
		}

		actualWidth = $tip[0].offsetWidth;
		actualHeight = $tip[0].offsetHeight;

		if (placement == 'top' && actualHeight != height) {
			offset.top = offset.top + height - actualHeight;
			replace = true;
		}

		if (placement == 'bottom' || placement == 'top') {
			delta = 0;

			if (offset.left < 0) {
				delta = offset.left * -2;
				offset.left = 0;
				$tip.offset(offset);
				actualWidth = $tip[0].offsetWidth;
				actualHeight = $tip[0].offsetHeight;
			}

			this.replaceArrow(delta - width + actualWidth, actualWidth, 'left');
		} else {
			this.replaceArrow(actualHeight - height, actualHeight, 'top');
		}

		if (replace)
			$tip.offset(offset);
	};
}(window.jQuery);

/**
 * namespace。
 */
var ssm = {};

/**
 * =================== static methods
 */

/**
 * 是否处理中。
 * @param obj
 * @returns {Boolean}
 */
ssm.isSubmiting = function(obj) {
	var key = "submiting";
	if (obj.attr(key)) {
		return true;
	}
	obj.attr(key, true);
	return false;
};

/**
 * 终了处理。
 * @param obj
 */
ssm.endSubmiting = function(obj) {
	obj.removeAttr("submiting");
};

/**
 * 共通Ajax。ダブルサブミッション防止が各自でやる。
 * @param settings
 */
ssm.ajax = function(settings) {
	var options = $.extend(settings, {
		type : "POST",
		url : ssm.getAbsUrl(settings["url"])
	});
	$.ajax(options);
};

/**
 * フォームAjax化する。
 * @param form
 * @param settings
 * @returns {ssm.ajaxForm}
 */
ssm.ajaxForm = function(form, settings) {

	form = $(form);
	settings = settings || {};
	var url = settings.url || form.attr2('action');

	// デフォルト設定。
	var options = $.extend({
		beforeSubmit : function(arr, $form, options) {
			// ダブルサブミッション防止。
			if (ssm.isSubmiting($form)) {
				return false;
			}
		},
		complete : function() {
			ssm.endSubmiting(form);
		}
	}, settings, {
		// 路径处理需要吗？
		url : url
	});

	form.ajaxForm(options);
};

ssm.errorFields = $([]);
ssm.popover = false;

/**
 * ===------------------------------ エラーとメッセージ。
 */
ssm.showMessage = function(text, isError) {

	// クリア。
	ssm.errorFields.tooltip('destroy').parents(".control-group").removeClass("error");
	ssm.errorFields = $([]);
	var box = $("#messageBox").removeClass("alert-error alert-success").addClass("hide");
	if (ssm.popover) {// popover
		ssm.popover.popover('destroy');
	}

	// データ収集。
	var map = {};
	$.each($("<div>").html(text).find("li"), function(index, value) {

		var li = $(value);
		var msg = li.text();
		var name = li.attr("data-field");
		// if (!name) {// メッセージを直接に表示する。
		box.text(msg);
		box.removeClass("hide").addClass("alert-" + (isError ? "error" : "success"));
		ssm.popover = box.parent().popover({
			title : "エラーが発生しました。",
			content : box.html(),
			html : true,
			placement : "bottom"
		}).popover('show');
		var data = box.parent().data();
		var template = data.popover.options.template;
		template = template.replace('class="popover"', 'class="popover error"');
		data.popover.options.template = template;
		// return;
		// }

		var n = map[name];
		if (!n) {
			n = new Array();
			map[name] = n;
		}
		n.push(msg);
	}),

	// エラー表示する。
	$.each(map, function(key, value) {
		var field = $(key);
		ssm.errorFields = ssm.errorFields.add(field);
		field.parents(".control-group").addClass("error");
		field.tooltip({
			html : true,
			placement : 'right',
			trigger : 'focus',
			cssClass : 'error',
			title : value.join("<br>")
		}).tooltip('show');
	});
};

/**
 * 遷移する。
 * @param to
 */
ssm.go = function(to) {
	document.location.href = ssm.getAbsUrl(to);
};

/**
 * 絶対パスを取得する。
 * @param url
 * @returns
 */
ssm.getAbsUrl = function(url) {

	if (url.substr(0, 4) == "http") {
		return url;
	}

	var root = CONSTANTS.rootUrl;
	if (url.substr(0, root.length) == root) {
		return url;
	}

	if (url.charAt(0) == "/") {
		url = url.substr(1);
	}

	return root + url;
};

/**
 * Confirm dialog.Do not use bootstrap orign .modal() directly!<br>
 * @see http://confirmmodal.codeplex.com/
 */
ssm.dialog = function(settings) {

	var options = $.extend({
		selector : '#modalDiv',// 对话框模板选择器
		header : 'Please confirm',// 标题内容
		headerSelector : '.modal-header h3',// 标题选择器
		body : 'Body contents',// 主体内容
		bodySelector : '.modal-body p',// 主体选择器
		handleSelector : '.modal-header',// 拖动条选择器
		// 简化的OK按钮动作。
		callback : false
	}, settings);

	var modal = $(options.selector);
	$(options.headerSelector).html(options.header);
	$(options.bodySelector).html(options.body);
	modal.draggable({
		handle : options.handleSelector
	});
	var buttons = options.buttons;
	if (!buttons) {// 默认OK动作
		buttons = [ {
			selector : '#confirmYesBtn',
			click : function() {
				if (options.callback) {
					options.callback();
				}
				modal.modal('hide');
			}
		} ];
	}
	$.each(buttons, function(i, b) {
		$(b.selector, modal).off('click').click(b.click);
	});

	modal.modal('show');
	return modal;
};

/**
 * 共通エラー処理。
 */
$(document).ajaxError(function(event, xhr, settings, error) {

	// 共通エラー処理。settingsのエラー処理を上書きしている。
	console.log(xhr);
	var ct = xhr.getResponseHeader('content-type');
	// HTMLなら表示する。
	if (ct.indexOf("html") > -1) {
		ssm.showMessage(xhr.responseText, true);
		return;
	}
	// JSONなら例外とする。
	if (ct.indexOf("json") > -1) {
		var ex = $.parseJSON(xhr.responseText);
		if (ex && ex.view) {
			ssm.go(ex.view);
			return;
		}
	}
	// その他の不明例外がすべてシステム例外へ。
	ssm.go(CONSTANTS.systemError);
});

/**
 * 初始化。
 */
$(document).ready(function() {

	// 所有form变成ajax。
	$("form").each(function(index, form) {
		ssm.ajaxForm($(form));
	});

	// 显示信息。
	ssm.showMessage($("#messagesDataList").html(), true);
});
