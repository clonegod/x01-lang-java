package com.gc.letter.ws;

import java.io.File;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gc.letter.service.LetterManager;
import com.gc.letter.util.FileUtil;


@WebService(endpointInterface = "com.yg.ws.PDFService")
public class PDFServiceImpl implements PDFService {
	
	private static final String SaveDir = "target/pdf/";
	final Logger logger = LoggerFactory.getLogger(PDFServiceImpl.class);
	
	public byte[] generate(String businessNo) throws Exception {
		logger.info("业务号：{}", businessNo);

		businessNo = businessNo.trim();
		
		File parent = new File(SaveDir);
		parent.mkdirs();
		File file = new File(parent, businessNo.concat(".pdf"));
		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();
		byte[] beforeSign = null;
		try{
			beforeSign = LetterManager.process(businessNo);
			FileUtil.getInstance().writeBytes2File(beforeSign, file);
			return beforeSign;
		}catch(Exception e) {
			throw e;
		} finally {
			if(beforeSign==null || beforeSign.length==0) {
				file.delete();
			}
		}
	}


	
}
