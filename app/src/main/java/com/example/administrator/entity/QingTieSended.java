package com.example.administrator.entity;

public class QingTieSended {
//"CallFormCgy": "1",
  //  "PkICardID": "3",
  //  "RecvName": "张先生",
   // "SendDt": "2016/7/21 17:10:17"
	private String CallFormCgy;
	private String PkICardID;
	private String RecvName;
	private String SendDt;
	
	
	public QingTieSended() {
		super();
	}
	public QingTieSended(String callFormCgy, String pkICardID, String recvName,
			String sendDt) {
		super();
		CallFormCgy = callFormCgy;
		PkICardID = pkICardID;
		RecvName = recvName;
		SendDt = sendDt;
	}
	public String getCallFormCgy() {
		return CallFormCgy;
	}
	public void setCallFormCgy(String callFormCgy) {
		CallFormCgy = callFormCgy;
	}
	public String getPkICardID() {
		return PkICardID;
	}
	public void setPkICardID(String pkICardID) {
		PkICardID = pkICardID;
	}
	public String getRecvName() {
		return RecvName;
	}
	public void setRecvName(String recvName) {
		RecvName = recvName;
	}
	public String getSendDt() {
		return SendDt;
	}
	public void setSendDt(String sendDt) {
		SendDt = sendDt;
	}
	@Override
	public String toString() {
		return "QingTieSended [CallFormCgy=" + CallFormCgy + ", PkICardID="
				+ PkICardID + ", RecvName=" + RecvName + ", SendDt=" + SendDt
				+ "]";
	}
	
	
}
