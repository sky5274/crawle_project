package com.sky.transaction.datasource.event;

public interface ConnectSourceEvent {
	public void commit() throws Exception;
	public void rollBack() throws Exception;
}
