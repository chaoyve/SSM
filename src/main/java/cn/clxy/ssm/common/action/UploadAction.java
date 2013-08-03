package cn.clxy.ssm.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@NoAuth
@Layout(noNeed = true)
public class UploadAction {

	private static final int OK = HttpServletResponse.SC_OK;
	private static final int BAD = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

	private static final String KEY_FILE = "file";
	private static final String KEY_FILE_NAME = "fileName";
	private static final String PART_COUNT = "partCount";

	@ResponseBody
	@RequestMapping(value = "upload*")
	public void upload(HttpServletRequest request, HttpServletResponse response) {

		int statusCode = OK;
		String fileName = request.getParameter(KEY_FILE_NAME);
		if (StringUtils.isEmpty(fileName)) {
			statusCode = save(request, response);
		} else {
			statusCode = combine(fileName, request);
		}

		response.setStatus(statusCode);
	}

	private int combine(String fileName, HttpServletRequest request) {

		Long count = Long.parseLong(request.getParameter(PART_COUNT));
		String destFileName = getPath(request) + File.separator + fileName;
		File destFile = new File(destFileName);

		int statusCode = BAD;// Pessimism
		try (FileOutputStream dest = new FileOutputStream(destFile, true)) {

			FileChannel dc = dest.getChannel();// the final big file.
			for (long i = 0; i < count; i++) {
				File partFile = new File(destFileName + "." + i);// every small parts.
				if (!partFile.exists()) {
					break;
				}
				try (FileInputStream part = new FileInputStream(partFile)) {
					FileChannel pc = part.getChannel();
					pc.transferTo(0, pc.size(), dc);// combine.
				}
				partFile.delete();
			}
			statusCode = OK;// set ok at last.
		} catch (Exception e) {
			log.error("combine failed.", e);
		}

		if (destFile.length() == 0) {
			destFile.delete();
		}

		return statusCode;
	}

	private int save(HttpServletRequest request, HttpServletResponse response) {

		if (!(request instanceof MultipartHttpServletRequest)) {
			return BAD;
		}

		int statusCode = OK;

		MultipartFile file = ((MultipartHttpServletRequest) request).getFile(KEY_FILE);
		String dest = getPath(request) + file.getOriginalFilename();
		log.debug(dest + " -- " + file.getSize() / 1000 + "K.");
		try {
			file.transferTo(new File(dest));
		} catch (Exception e) {
			log.error("save failed.", e);
			statusCode = BAD;
		}

		return statusCode;
	}

	private String getPath(HttpServletRequest request) {
		return request.getServletContext().getRealPath("WEB-INF")
				+ File.separatorChar + "work" + File.separatorChar;
	}

	private static final Logger log = LoggerFactory.getLogger(UploadAction.class);
}
