package com.sky.table.handel;

import java.sql.ResultSet;

public interface SqlExcuteHandel <T>{
	public   T excuteResult(ResultSet result);
}
