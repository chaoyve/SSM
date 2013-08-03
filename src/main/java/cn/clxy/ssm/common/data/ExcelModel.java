package cn.clxy.ssm.common.data;

import org.springframework.web.servlet.ModelAndView;

import cn.clxy.ssm.common.AppConfig;
import cn.clxy.ssm.common.web.JxlsExcelView;

/**
 * Excel用Model。
 * @author clxy
 */
public class ExcelModel extends ModelAndView {

	public ExcelModel() {
		setViewName(AppConfig.get("excelExt"));
	}

	public ExcelModel(String template) {
		this();
		addObject(JxlsExcelView.EXCEL_TEMPLATE, template);
	}
}
