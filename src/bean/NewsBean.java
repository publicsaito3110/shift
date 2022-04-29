package bean;

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
