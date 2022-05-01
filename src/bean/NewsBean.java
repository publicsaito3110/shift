package bean;

import common.CommonUtil;

/**
 * @author saito
 *
 */
public class NewsBean {


	private String ymd;
	private String category;
	private String title;
	private String content;
	private String labelNew;


	//getter, setter
	public String getYmd() {
		return ymd;
	}
    /**
	 * String日付変換処理
	 *
	 * <p>String型の8文字の日付(YYYYMMDD)に変換する<p>
	 *
	 * @param void
	 * @return String 表示用の日付(YYYY/MM/DD)
     */
	public String getYmdFormatDisplayDate() {

		//表示用の日付(YYYY/MM/DD)に変換
		String displayYmd = ymd.substring(0, 4) + "/" + ymd.substring(4, 6) + "/" + ymd.substring(6, 8);

		return displayYmd;
	}
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	/**
	 * 改行変換処理
	 *
	 * <p>改行が含まれているとき<br>に変換する</p>
	 *
	 * @param void
	 * @return String 改行があるとき<br>に変換して返す
	 */
	public String getContentAfterBreakLine() {

		content = CommonUtil.changeToBrByBreakLine(content);

		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLabelNew() {
		return labelNew;
	}
	public void setLabelNew(String labelNew) {
		this.labelNew = labelNew;
	}
}
