var wowButton = function(options) {

	$("#wow").on("click", options.selector, function(e) {

		var obj = $(e.target);
		if (ssm.isSubmiting(obj)) {
			return;
		}

		var text = obj.text();
		var active = obj.hasClass('active');
		options.callback(active, obj);
		if (active) {
			obj.removeClass(options.clazz);
			obj.text(options.activeText || text);
		} else {
			obj.addClass(options.clazz);
			obj.text(options.notActiveText || text);
		}
		ssm.endSubmiting(obj);
	});
};

$(document).ready(function() {

	// 编辑按钮。
	wowButton({
		selector : ".editBtn",
		activeText : "开始编辑",
		notActiveText : "取消编辑",
		clazz : "btn-danger",
		callback : function(active, obj) {

			var parent = obj.parents("tr");
			if (active) {
				parent.removeClass("error");
				$(".equipBtn, .bossBtn", parent).attr('disabled', 'disabled');
			} else {
				parent.addClass("error");
				$(".equipBtn, .bossBtn", parent).removeAttr('disabled');
			}
		}
	});

	// Boss按钮。
	wowButton({
		selector : ".bossBtn",
		clazz : "btn-info",
		callback : function(active, obj) {
			var ary = obj.attr("data-data").split(",");
			ssm.ajax({
				url : "/wow/updateWeek",
				async : false,
				data : {
					roleId : ary[0],
					boss : ary[1],
					killWeek : week,
					isDelete : active
				}
			});
			setTimeout(function() {
				setBossCell(obj.parents("td"));
			}, 5);
		}
	});

	// 装备按钮。
	wowButton({
		selector : ".equipBtn",
		clazz : "btn-success",
		callback : function(active, obj) {
			var ary = obj.attr("data-data").split(",");
			ssm.ajax({
				url : "/wow/updateEquip",
				async : false,
				data : {
					rid : ary[0],
					eid : ary[1],
					isDelete : active
				}
			});
			setTimeout(function() {
				setBossCell(obj.parents("td").prev());
			}, 5);
		}
	});

	// 所有装备都已获得时，标记boss按钮。
	setBossCell();
});

function setBossCell(tds) {

	tds = tds || $(".bossBtn").parents("td");
	$.each(tds, function(index, value) {
		var btd = $(value);
		var etd = btd.next();
		var td2 = btd.add(etd);
		if (btd.children(".bossBtn.active").size() > 0
				|| (etd.children(".equipBtn").size() == etd.children(".equipBtn.active").size())) {
			td2.addClass("info");
		} else {
			td2.removeClass("info");
		}
	});
}
