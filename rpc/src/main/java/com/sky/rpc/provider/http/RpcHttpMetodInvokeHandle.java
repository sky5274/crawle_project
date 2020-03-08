package com.sky.rpc.provider.http;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sky.rpc.base.Result;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.provider.ProviderMethodInvoker;

@RestController
public class RpcHttpMetodInvokeHandle {
	
	@RequestMapping("/resttemplate/invoke")
	public Result<Object> invoke(HttpServletRequest req,@RequestBody RpcRequest request){
		SocketAddress addr=new InetSocketAddress(req.getRemoteHost(),req.getRemotePort());
		Result<Object> result=null;
		try {
			result=new Result<Object>(request.getRequestId(),ProviderMethodInvoker.invoke(addr, request));
		} catch (Throwable e) {
			result=new Result<Object>(request.getRequestId(), e);
		}
		return result;
	}
}
