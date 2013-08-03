package cn.clxy.ssm.common.data;

import java.io.Serializable;
import java.util.HashMap;

import cn.clxy.ssm.common.AppConfig;

/**
 * ページング条件データ。
 * @author clxy
 */
public class PaginationCondition extends HashMap<String, Object> implements Serializable {

	/**
	 * 総件数。
	 */
	protected int count;

	/**
	 * ページ番号。1から開始。
	 */
	protected int page = 1;

	/**
	 * 1ページ件数。
	 */
	protected int limit = defaultLimit;

	/**
	 * 最初のページか否か。
	 * @return
	 */
	public boolean isFirst() {
		return page == 1;
	}

	/**
	 * 最後のページか否か。
	 * @return
	 */
	public boolean isLast() {
		return page == getPageCount();
	}

	/**
	 * 検索時使うスタート位置。
	 * @return
	 */
	public int getOffset() {
		return (page - 1) * limit;
	}

	/**
	 * ページ数を返す。
	 * @return
	 */
	public int getPageCount() {
		int pageCount = count / limit;
		return (count % limit == 0) ? pageCount : pageCount + 1;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	private static final long serialVersionUID = 1L;
	private static final int defaultLimit = AppConfig.getDefaultLimit();
}
