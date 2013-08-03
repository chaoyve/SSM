package cn.clxy.ssm.wow.action;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import cn.clxy.ssm.common.action.NoAuth;
import cn.clxy.ssm.wow.data.WowRoleWeek;
import cn.clxy.ssm.wow.service.WowWeekService;

@NoAuth
@SessionAttributes({ WowAction.KEY_DATE })
public class WowAction {

	@Resource
	private WowWeekService service;

	@RequestMapping(value = "/")
	public ModelAndView index() {
		return createModel(new Date());
	}

	@RequestMapping(value = "/{date}")
	public ModelAndView go(@PathVariable @DateTimeFormat(pattern = "yyyyMMdd") Date date) {
		return createModel(date);
	}

	@RequestMapping(value = "/back")
	public ModelAndView back(@ModelAttribute(KEY_DATE) Date date) {

		Date back = addWeek(date, -1);
		return createModel(back);
	}

	@RequestMapping(value = "/fore")
	public ModelAndView fore(@ModelAttribute(KEY_DATE) Date date) {

		Date fore = addWeek(date, 1);
		return createModel(fore);
	}

	@RequestMapping(value = "/updateEquip")
	public @ResponseBody
	void updateEquip(
			@RequestParam("rid") int rid,
			@RequestParam("eid") int eid,
			@RequestParam("isDelete") boolean isDelete) {
		service.updateEquip(rid, eid, isDelete);
	}

	@RequestMapping(value = "/updateWeek")
	public @ResponseBody
	void updateWeek(
			WowRoleWeek week,
			@RequestParam("isDelete") boolean isDelete) {
		service.updateWeek(week, isDelete);
	}

	private ModelAndView createModel(Date date) {

		int week = getWeek(date);
		ModelAndView result = new ModelAndView(input);
		result.addObject("bosses", service.getAllBoss());
		result.addObject("rows", service.getWeekRow(week));
		result.addObject(KEY_DATE, date);
		result.addObject("wowWeek", getWeek(date));
		return result;
	}

	private Date addWeek(Date date, int offset) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.WEEK_OF_YEAR, offset);
		return c.getTime();
	}

	private static int getWeek(Date date) {

		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	@ModelAttribute(KEY_DATE)
	public Date populateWowDate() {
		return new Date();
	}

	private static final String input = "index";
	public static final String KEY_DATE = "wowDate";
}
