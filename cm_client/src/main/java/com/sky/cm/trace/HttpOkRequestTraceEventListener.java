package com.sky.cm.trace;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import com.sky.cm.bean.RequestTraceBean;
import okhttp3.Request;

public class HttpOkRequestTraceEventListener extends RequestTraceEventListen<Request>{

	@Override
	public RequestTraceBean convert(Request request) {
		if(request ==null || request.url() ==null) {
			return null;
		}
		RequestTraceBean info=new RequestTraceBean();
		info.setUrl(request.url().url().toString());
		if(request.body()!=null) {
			try {
				okio.Buffer buff=new okio.Buffer();
				request.body().writeTo(buff);
				info.setRequestBody(buff.readUtf8());
			} catch (IOException e) {
			}
		}
		info.setType(request.method());
		info.setHeaders(request.headers().toString());
		info.setTraceId(UUID.randomUUID().toString());
		HttpServletRequest req =getHttpRequest();
		if(req !=null) {
			String info_groupid = (String) req.getSession().getAttribute(groupId);
			String tracepid = (String) req.getSession().getAttribute("tracepid");
			info.setTracePId(tracepid);
			if(StringUtils.isEmpty(info_groupid)) {
				req.setAttribute(groupId, info.getTraceId());
				info_groupid=info.getTraceId();
			}
			info.setSessionId(req.getSession().getId());
			info.setGroupId(info_groupid);
		}

		return info;
	}

	

}
