package bean;

public class ScheduleBean {
	//フィールドを定義
	private String day ;
	private String ymd ;
	private String memo1 ;
	private String memo2 ;
	private String memo3 ;

	private String typeIUD;


	// getter, setter
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getYmd() {
		return ymd;
	}
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	public String getMemo1() {
		return memo1;
	}
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	public String getMemo2() {
		return memo2;
	}
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	public String getMemo3() {
		return memo3;
	}
	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}

	public String getTypeIUD() {
		return typeIUD;
	}
	public void setTypeIUD(String typeIUD) {
		this.typeIUD = typeIUD;
	}
}
