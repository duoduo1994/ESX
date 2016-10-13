package com.example.esycab.selfview;

public interface FileTransferListener {

	void onFileTransferBegin();
	void onFileTransferEnd(String filename);
	void onFileTransfailed();

}
