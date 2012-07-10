package org.jembi.rhea.transformers;

import org.jembi.rhea.RestfulHttpRequest;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class GetPatientOpenEMPIOffRampTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage msg, String enc)
			throws TransformerException {
		
		RestfulHttpRequest request = (RestfulHttpRequest) msg.getPayload();
		String path = request.getPath();
		int index = path.lastIndexOf('/');
		String id_str = path.substring(index + 1);
		index = id_str.indexOf('-');
		String idType = id_str.substring(0, index);
		String id = id_str.substring(index + 1);
		String domainId = idType;
		
		//TODO remove TESTING code
		id = "7f3efef0-b6e0-11e1-a6f8-1231392d2383";
		idType = "hl7";
		domainId = "2.16.840.1.113883.4.357";
		// END TESTING code
		
		String body = "<personIdentifier>\n" +
					  "   <identifier>" + id + "</identifier>\n" +
                      "   <identifierDomain>\n" +
                      "     <universalIdentifier>" + domainId + "</universalIdentifier>\n" +
                      "     <universalIdentifierTypeCode>" + idType + "</universalIdentifierTypeCode>\n" +
                      "   </identifierDomain>\n" +
                      "</personIdentifier>";
		request.setBody(body);
		request.setHttpMethod(RestfulHttpRequest.HTTP_POST);
		request.setPath("openempi/openempi-ws-rest/person-query-resource/findPersonById");
		
		return msg;
	}

}
