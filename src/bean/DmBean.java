package bean;

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
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsgDate() {
		return msgDate;
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
