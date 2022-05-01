package bean;

import common.CommonUtil;

/**
 * @author saito
 *
 */
public class DmBean {


	private String sendUser;
	private String receiveUser;
	private String msg;
	private String msgDate;
	private String msgToName;
	private String msgToId;



	//getter, setter
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	public String getReceiveUser() {
		return receiveUser;
	}
	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}
	public String getMsg() {
		return msg;
	}
	/**
	 * 改行変換処理
	 *
	 * <p>改行が含まれているとき<br>に変換する</p>
	 *
	 * @param void
	 * @return String 改行があるとき<br>に変換して返す
	 */
	public String getMsgAfterBreakLine() {

		msg = CommonUtil.changeToBrByBreakLine(msg);

		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsgDate() {
		return msgDate;
	}
	/**
	 * 日付変換処理
	 *
	 * <p>表示用の日付に変換する</p>
	 *
	 * @param void
	 * @return String トーク画面に表示する日付(MM/DD hh:mm)に変換して返す
	 */
	public String getMsgDateFormatTlakDate() {

		StringBuffer sb = new StringBuffer();

		//msg_dateの値をsbに代入し、MMとDDの間に/を代入及び不要な値(年とミリ秒)を削除
		sb.append(msgDate);
		sb.delete(0, 5);
		sb.replace(2, 3, "/");
		sb.delete(11, 16);
		return sb.toString();
	}
	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}
	public String getMsgToName() {
		return msgToName;
	}
	public void setMsgToName(String msgToName) {
		this.msgToName = msgToName;
	}
	public String getMsgToId() {
		return msgToId;
	}
	public void setMsgToId(String msgToId) {
		this.msgToId = msgToId;
	}
}
