package cn.clxy.ssm.common.web;

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.AbstractView;

/**
 * JXLSでExcelを生成する。<br>
 * データとテンプレートに入れる。
 * @author clxy
 */
public class JxlsExcelView extends AbstractView {

	/**
	 * テンプレート場所。
	 */
	protected String location;

	public JxlsExcelView() {
		setContentType(contentType);
	}

	public void setTemplateLocation(String templateLocation) {
		this.location = templateLocation;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String template = (String) model.get(EXCEL_TEMPLATE);
		Resource file = getApplicationContext().getResource(location + template + extension);

		XLSTransformer transformer = new XLSTransformer();
		Workbook workbook = transformer.transformXLS(file.getInputStream(), model);

		response.setContentType(getContentType());
		// ダウンロードなら下記が必要。
		// response.setHeader("Content-Disposition", "attachment; filename=\"" + template + "\"");

		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}

	private static final String contentType = "application/vnd.ms-excel";
	private static final String extension = ".xlsx";
	public static final String EXCEL_TEMPLATE = "ssm.excel.template";
}
