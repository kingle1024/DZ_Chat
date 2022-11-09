package server.service;

import java.io.*;
import java.net.Socket;
import java.util.stream.Collectors;

import org.json.JSONObject;

import member.Member;

public abstract class Service {
	private BufferedReader br;
	private BufferedWriter bw;
	protected Member me;

	public JSONObject receive() throws IOException {
		return new JSONObject(br.readLine());
	}
	
	public void send(JSONObject json) throws IOException {
		bw.write(json.toString());
		bw.newLine();
		bw.flush();
	}
	
	public void setInputStream(BufferedReader br) {
		this.br = br;
	}
	
	public void setOutputStream(BufferedWriter bw) {
		this.bw= bw;
	}
	
	public abstract void request() throws IOException;
}
