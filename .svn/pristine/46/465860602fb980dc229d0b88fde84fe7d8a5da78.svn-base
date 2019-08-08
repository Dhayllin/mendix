package mindspheresinglesignon;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import com.mendix.core.Core;
import com.mendix.externalinterface.connector.RequestHandler;
import com.mendix.http.HttpHeader;
import com.mendix.http.HttpMethod;
import com.mendix.http.HttpResponse;
import com.mendix.logging.ILogNode;
import com.mendix.m2ee.api.IMxRuntimeRequest;
import com.mendix.m2ee.api.IMxRuntimeResponse;

import mindspheresinglesignon.proxies.CredentialsResponse;
import mindspheresinglesignon.proxies.constants.Constants;

public class ApiReverseProxy extends RequestHandler {	
	
	private class TokenExpiredException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}

	@Override
	protected void processRequest(IMxRuntimeRequest request, IMxRuntimeResponse response, String path)
			throws Exception {

		String QueryString = request.getHttpServletRequest().getQueryString();
		String URL = Constants.getMindSphereGatewayURL() + request.getResourcePath();

		if (QueryString != null) {
			URL = URL + "?" + QueryString;
		}
		URI uri = URI.create(URL);		
		try {
			HttpHeader[] Headers = getRequestHeaders(request, getAccessTokenFromSession(request));
			HttpMethod method = HttpMethod.valueOf(request.getHttpServletRequest().getMethod());
			HttpResponse remoteresponse = Core.http().executeHttpRequest(uri, method, Headers, request.getInputStream());
			// Copy Headers to response
			for (HttpHeader header : remoteresponse.getAllHeaders()) {
				response.addHeader(header.getName(), header.getValue());
			}
			// Copy Content
			InputStream in = remoteresponse.getContent();
			OutputStream out = response.getOutputStream();

			if (in != null) {
				if (method.name() == "DELETE") {
					Core.getLogger("ApiReverseProxy").warn("Detected a DELETE request with content, please note that this content will be ignored from the Mendix webserver with a high risk. This problem should only exist during local developing and not in your version pushed to the cloud");
				}
				
				int d;
				while ((d = in.read()) != -1) {
					out.write(d);
				}
			}
			response.setStatus(remoteresponse.getStatusCode());
			remoteresponse.close();
		} catch (TokenExpiredException e) {			
			// Logout from Session			
			LogoutHandler.logout(this.getSessionFromRequest(request), request);
			ILogNode logger = Core.getLogger("ApiReverseProxy");			
			logger.warn("MindSphere token expired - logout performed. Please refresh / reload application");
			response.setStatus(IMxRuntimeResponse.FORBIDDEN);			
		}		
	}

	private String getAccessTokenFromSession(IMxRuntimeRequest request) throws Exception {
		CredentialsResponse ACResponse = CredentialsHandler.retrievCredentialsResponsFromSession(request);						
		if (ACResponse != null) { //
			if (ACResponse.getExpires_at().getTime() > System.currentTimeMillis()) {
				return ACResponse.getToken_type() + " " + ACResponse.getAccess_token();
			}  else {
				throw new TokenExpiredException();
			}
		}
		return "";
	}

	private HttpHeader[] getRequestHeaders(IMxRuntimeRequest request, String token) {
		List<HttpHeader> aList = new ArrayList<HttpHeader>();
		Enumeration<String> headersNames = request.getHttpServletRequest().getHeaderNames();
		while (headersNames.hasMoreElements()) {
			String key = headersNames.nextElement();
			if (key.compareToIgnoreCase("Cookie") != 0 && key.compareToIgnoreCase("Host") != 0
					&& key.compareToIgnoreCase("Content-Length") != 0) {
				aList.add(new HttpHeader(key, request.getHeader(key)));
			}
		}

		aList.add(new HttpHeader("Authorization", token));
		HttpHeader[] headers = new HttpHeader[aList.size()];
		return aList.toArray(headers);
	}
}