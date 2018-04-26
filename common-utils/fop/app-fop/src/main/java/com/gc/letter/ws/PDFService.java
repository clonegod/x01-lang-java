package com.gc.letter.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public abstract interface PDFService
{
  @WebMethod
  @WebResult(name="bytes2Sign")
  public abstract byte[] generate(@WebParam(name="businessNo") String paramString)
    throws Exception;
  
}
