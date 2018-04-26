package com.gc.letter.service;

import static com.gc.letter.util.LetterUtil.emptyStrToNull;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gc.lafe.laws.webservice.letter.pip.PipBenfRec;
import com.gc.lafe.laws.webservice.letter.pip.PipClientRec;
import com.gc.lafe.laws.webservice.letter.pip.PipComponentRec;
import com.gc.lafe.laws.webservice.letter.pip.PipDataRec;
import com.gc.letter.consts.LetterEnum.ClientRole;
import com.gc.letter.consts.SystemConfig;
import com.gc.letter.util.DateUtil;
import com.gc.letter.util.LetterUtil;
import com.gc.letter.vo.BankAccount;
import com.gc.letter.vo.Beneficiary;
import com.gc.letter.vo.HealthAnnouncement;
import com.gc.letter.vo.Letter;
import com.gc.letter.vo.Rider;
import com.google.gson.Gson;
public class MappingHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(MappingHelper.class);

	public static Letter mapping(PipDataRec pip, Letter letter) {
		
		try {
			Gson gson = new Gson();
			logger.info("pip from LA: {}",gson.toJsonTree(pip).toString());
			
			pip.getChdrcoy();
			pip.getCoolPeriodDays();
			pip.getZcooped();
			
			letter.setOTAE("Y".equals(pip.getOtaFlag()));
			letter.setBusinessSource(pip.getSrceBizDesc());
//			letter.setBankIns(pip.getSrceBiz().equals("BA"));
			letter.setBankIns(pip.getCoolPeriodDays()==15);

			
			letter.setSettleOfDispute("".equals(pip.getDisputeMethodDesc()) ? null : pip.getDisputeMethodDesc());
			
			// 根据不同产品打印提示信息（首页）
			letter.setHasDiv(pip.isHasDiv());
			letter.setHasLongTermComponent(pip.isHasLongTermComponent());
			letter.setHasUv(pip.isHasUl());
			letter.setHasUl(letter.isHasUv());
			
			letter.setContractId(pip.getChdrnum());//保险单号
			
			letter.setProposeDate(String.valueOf(pip.getPropDateForPip()));//投保日
			letter.setSignDate(String.valueOf(pip.getIssueDateForPip()));//签发日
			letter.setEffectiveDate(String.valueOf(pip.getOccdateForPip()));//生效日
			letter.setDutyStartDate(letter.getEffectiveDate());//保险责任开始日: （投保OTA-E产品时打印）
			letter.setDutyEndDate(String.valueOf(pip.getRiskCessDateForPip()));//合同终止日期：（投保OTA-E产品时打印）
			
			//** 投保人信息 *//
			PipClientRec pipOwner = pip.getOwnerRec();
			pipOwner.getAge();
			
			com.gc.letter.vo.Client holder = new com.gc.letter.vo.Client();
			holder.setRole(ClientRole.投保人.name());
			//TODO: relationwithfirstInsurant
			String relation = pip.getRelationBetweenOwnerAndFirstLifDesc();
			if("父母亲".equals(relation)) {
				relation = "父母";
			}
			holder.setRelation(relation);
			holder.setClientNo(pipOwner.getClntNum());// 客户号
			holder.setName(pipOwner.getClntName());// 姓名
			holder.setSex("F".equals(pipOwner.getClntSex()) ? "女" : "男");// 性别
			holder.setBirthday(DateUtil.getSpeicfyDate(pipOwner.getDob()));// 出生日期
			holder.setIdType(pipOwner.getIdTypeDesc());// 证件类型
			holder.setIdNumber(emptyStrToNull(pipOwner.getIdNo()));// 证件号
			holder.setEmail("".equals(pipOwner.getEmail()) ? null : pipOwner
					.getEmail());// 邮件地址
			holder.setOccupation(covertOccu(pipOwner));
			
			holder.setPhoneNumber(pip.getMobileTel());// 联系电话
			holder.setCommunicationAddress(pip.getAddress());// 通讯地址
			holder.setPostcode(pip.getPostCode());// 邮编
			
			//首期银行账号
			holder.setHolderFirstPayAccount(
					new BankAccount(emptyStrToNull(pip.getPosBankAcckey()), 
							emptyStrToNull(pip.getPosBankDesc())));
			//续期银行账号
			holder.setHolderRenewPayAccount(
					new BankAccount(emptyStrToNull(pip.getDdBankAcckey()), 
							emptyStrToNull(pip.getDdBankDesc())));
			
			holder.setHomeAddress(null);
			letter.addClient(holder);
			
			//** 被保人信息 *//
			List<PipClientRec> insurantList = pip.getLifeList();
			for (PipClientRec client : insurantList) {
				com.gc.letter.vo.Client insurant = new com.gc.letter.vo.Client();
				insurant.setRelation(null);
				insurant.setRole(LetterUtil.getInsurantSequence(client.getLife()));
				insurant.setClientNo(client.getClntNum());
				insurant.setName(client.getClntName());
				insurant.setSex("F".equals(client.getClntSex()) ? "女" : "男");
				insurant.setBirthday(DateUtil.getSpeicfyDate(client.getDob()));
				insurant.setIdType(client.getIdTypeDesc());
				insurant.setIdNumber(emptyStrToNull(client.getIdNo()));
				insurant.setOccupation(covertOccu(client));
				insurant.setEmail("".equals(client.getEmail()) ? null : client.getEmail());
				insurant.setInsurantPayAccount(
						new BankAccount(emptyStrToNull(pip.getLifeDcBankAcckey()), emptyStrToNull(pip.getLifeDcBankDesc())));
				
				insurant.setCommunicationAddress(null);
				insurant.setHomeAddress(null);
				insurant.setPhoneNumber(null);
				insurant.setPostcode(null);
				letter.getClients().add(insurant);
			}
			
			//** 受益人信息 *//
			if(pip.getBenfList()!=null) {
				for (PipBenfRec pipBenf : pip.getBenfList()) {
					Beneficiary benf = new Beneficiary(pipBenf.getBenfSeq(),
							pipBenf.getSurName(), "F".equals(pipBenf.getSex())?"女":"男", DateUtil.getSpeicfyDate(pipBenf
									.getDob()), pipBenf.getBenfRelationShipDesc(),
									pipBenf.getBenfPercent(), pipBenf.getIdTypeDesc(),
									pipBenf.getIdNo());
					letter.addBeneficiary(benf);
				}
			}
			
			//** 险种信息 *//
			List<PipComponentRec> pipRiders = pip.getComponentList();
			for(PipComponentRec pipRider : pipRiders) {
				Rider r = new Rider();
				r.setCvsList(pipRider.getCvList());
				r.setPayPeriod(pipRider.getPremTermForPip());//交费期间
				r.setCovPeriod(pipRider.getRiskTermForPip());//保险期间
				r.setExpireDate(pipRider.getRiskCessDateForPip());//满期日
				r.setInsuredAmount(pipRider.getSumins());
				r.setIsBasic("00".equals(pipRider.getRider()));
				r.setPeriodFee(pipRider.getPremForPip());
				r.setRiderCode(pipRider.getCrtable());
				r.setRiderName(pipRider.getCrtableDesc().replace("（", "(").replace("）", ")"));
				letter.addRider(r);
			}

			letter.setPayFrequence(pip.getGetBillFreqDesc());
			
			letter.setFstPrem(null);
			letter.setFstAddPrem(null);
			letter.setFstPremSum(pip.getTotPrem());// 首期合计保险费
			letter.setSinglePrem(pip.getSingPrem());// 趸交保险费
			letter.setRenewalFee(pip.getRenewalInstPrem());// 续期每期应交保险费
			
			// 合同给付及保险费逾期未付处理方式
			pip.getCouponOpt();// 年金选项
			letter.setAnnunityDrawType(emptyStrToNull(pip
					.getCouponOptDesc()));// 年金描述
			pip.getDivOpt();// 红利选项
			letter.setDividentDrawType(emptyStrToNull(pip
					.getDivOptDesc()));// 红利描述
			pip.getNftOpt();// 逾期处理选项
			letter.setSettleOfOverdue(emptyStrToNull(pip
					.getNftOptDesc()));// 对应描述
			
			
			// 账户分配
			// List<PipFundAllocRec> fundAllocList = pip.getFundAllocList();
			
			//健康告知
			if(pip.getHealthNoteQuestions()!=null && pip.getHealthNoteQuestions().size()>0) {
				List<String> healthQuestionList = new LinkedList<String>();
				for(String question : pip.getHealthNoteQuestions()) {
					healthQuestionList.add(question.replaceAll("\\s+", ""));
				}
				letter.setHealthAnnouncement(
						new HealthAnnouncement(healthQuestionList,
								pip.isDoesFirstLifeHasHealthNotes(), pip.getFirstLifeDetailHealthNote(),
								pip.isDoesSecondLifeHasHealthNotes(), pip.getSecondLifeDetailHealthNote()));
			}
			
			
			letter.setShowAutoRenewal(!"U".equalsIgnoreCase(pip.getAutoRenewalFlag()));
			letter.setAutoRenewal("Y".equals(pip.getAutoRenewalFlag()));
			letter.setCircComplaintTel(pip.getCircComplaintTel());
			letter.setIacComplaintTel(pip.getIacComplaintTel());
			letter.setBranchAddress(
					LetterUtil.checkIsNotNull(pip.getBranchAddress()) ? 
							pip.getBranchAddress() : SystemConfig.CMP_ADDR);
			letter.setCoolingPeriod(pip.getCoolPeriodDays());
			letter.setBizSrc(pip.getSrceBiz());
			//EC-网销渠道不显示续期保费项
			letter.setShowRenewFeeItem(! pip.isBECChannel()
					&& letter.getAutoRenewal() && letter.getRenewalFee().intValue()>0);
			
			//附注
			letter.setRemarks(pip.getRemarks());
			
			//销售人员
			//TODO: 可以先全部赋值，然后反射所有字段，将空字符串改为null
			letter.setSellerName(emptyStrToNull(pip.getThirdSellerName()));
			letter.setSellerCode(emptyStrToNull(pip.getThirdSellerCode()));
			
			logger.info("pip convert local: {}",gson.toJsonTree(letter).toString());
			return letter;
		} catch (Exception e) {
			logger.error("pip data mapping excepiton: ",e);
			throw new RuntimeException(e);
		}
	}

	private static String covertOccu(PipClientRec client) {
		if("Z99".equals(client.getProfCode()) || "".equals(client.getProfCodeDesc()))
			return null;
		return client.getProfCodeDesc();
	}

}
