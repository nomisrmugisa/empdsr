package org.pdsr;

import org.pdsr.master.repo.CaseAntenatalRepository;
import org.pdsr.master.repo.CaseBiodataRepository;
import org.pdsr.master.repo.CaseBirthRepository;
import org.pdsr.master.repo.CaseDeliveryRepository;
import org.pdsr.master.repo.CaseFetalheartRepository;
import org.pdsr.master.repo.CaseLabourRepository;
import org.pdsr.master.repo.CasePregnancyRepository;
import org.pdsr.master.repo.CaseReferralRepository;
import org.pdsr.master.repo.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ExcelService {

	private final CaseBiodataRepository biodataRepository;
	private final CaseRepository caseidrepository;
	private final CaseReferralRepository referralRepository;
	private final CasePregnancyRepository pregnancyRepository;
	private final CaseAntenatalRepository antenatalRepository;
	private final CaseLabourRepository labourRepository;
	private final CaseDeliveryRepository deliveryRepository;
	private final CaseBirthRepository birthRepository;
	private final CaseFetalheartRepository fetalHeartRepository;

	public ExcelService(CaseRepository caseidRepository, CaseBiodataRepository biodataRepository,
			CaseReferralRepository referralRepository, CasePregnancyRepository pregnancyRepository,
			CaseAntenatalRepository antenatalRepository, CaseLabourRepository labourRepository,
			CaseDeliveryRepository deliveryRepository, CaseBirthRepository birthRepository,
			CaseFetalheartRepository fetalHeartRepository) {
		
		this.caseidrepository = caseidRepository;
		this.biodataRepository = biodataRepository;
		this.referralRepository = referralRepository;
		this.pregnancyRepository = pregnancyRepository;
		this.antenatalRepository = antenatalRepository;
		this.labourRepository = labourRepository;
		this.deliveryRepository = deliveryRepository;
		this.birthRepository = birthRepository;
		this.fetalHeartRepository = fetalHeartRepository;
	}

	public void save(MultipartFile file) {
		try {
			// Read the input stream into a byte array
			byte[] bytes = file.getBytes();
			InputStream inputStream = new ByteArrayInputStream(bytes);

			List<Caseid> caseids = ExcelHelper.returnCaseIds(inputStream);
			caseidrepository.saveAll(caseids);

			inputStream.reset();
			List<Biodata> biodata = ExcelHelper.returnBiodata(inputStream);

			// Link Caseid and Biodata
			for (Biodata bio : biodata) {
				Caseid caseid = caseidrepository.findByMothersIdNo(bio.getMothersIdNo());
				if (caseid != null) {
					bio.setCaseid(caseid);
					caseid.setBiodata(bio);
				}
			}

			biodataRepository.saveAll(biodata);

			// Reset the input stream for reading referrals
			inputStream.reset();
			List<Referral> referrals = ExcelHelper.returnReferral(inputStream);

			// Link Caseid and Referral
			for (Referral referral : referrals) {
				Caseid caseid = caseidrepository.findByMothersIdNo(referral.getMothersIdNo());
				if (caseid != null) {
					referral.setCaseid(caseid);
					caseid.setReferral(referral);
				}
			}

			referralRepository.saveAll(referrals);

			// reset the input stream for reading pregnancies
			inputStream.reset();
			List<Pregnancy> pregnancies = ExcelHelper.returnPregnancy(inputStream);

			// Link Caseid and Pregnancy
			for (Pregnancy pregnancy : pregnancies) {
				Caseid caseid = caseidrepository.findByMothersIdNo(pregnancy.getMothersIdNo());
				if (caseid != null) {
					pregnancy.setCaseid(caseid);
					caseid.setPregnancy(pregnancy);
				}
			}

			pregnancyRepository.saveAll(pregnancies);

			// Reset the input stream for reading antenatals
			inputStream.reset();
			List<Antenatal> antenatals = ExcelHelper.returnAntenatals(inputStream);

			// Link Caseid and Antenatal
			for (Antenatal antenatal : antenatals) {
				Caseid caseid = caseidrepository.findByMothersIdNo(antenatal.getMothersIdNo());
				if (caseid != null) {
					antenatal.setCaseid(caseid);
					caseid.setAntenatal(antenatal);
				}
			}

			antenatalRepository.saveAll(antenatals);

			// Reset the input stream for reading labours
			inputStream.reset();
			List<Labour> labours = ExcelHelper.returnLabour(inputStream);

			// Link Caseid and Labour
			for (Labour labour : labours) {
				Caseid caseid = caseidrepository.findByMothersIdNo(labour.getMothersIdNo());
				if (caseid != null) {
					labour.setCaseid(caseid);
					caseid.setLabour(labour);
				}
			}
			labourRepository.saveAll(labours);

			// Reset the input stream for reading deliveries
			inputStream.reset();
			List<Delivery> deliveries = ExcelHelper.returnDelivery(inputStream);

			// Link Caseid and Delivery
			for (Delivery delivery : deliveries) {
				Caseid caseid = caseidrepository.findByMothersIdNo(delivery.getMothersIdNo());
				if (caseid != null) {
					delivery.setCaseid(caseid);
					caseid.setDelivery(delivery);
				}
			}
			deliveryRepository.saveAll(deliveries);

			// Reset the input stream for reading births
			inputStream.reset();
			List<Birth> births = ExcelHelper.returnBirth(inputStream);

			// Link Caseid and Birth
			for (Birth birth : births) {
				Caseid caseid = caseidrepository.findByMothersIdNo(birth.getMothersIdNo());
				if (caseid != null) {
					birth.setCaseid(caseid);
					caseid.setBirth(birth);
				}
			}
			birthRepository.saveAll(births);

			// Reset the input stream for reading fetal hearts
			inputStream.reset();
			List<FetalHeart> fetalHearts = ExcelHelper.returnFetalHearts(inputStream);

			// Link Caseid and FetalHeart
			for (FetalHeart fetalHeart : fetalHearts) {
				Caseid caseid = caseidrepository.findByMothersIdNo(fetalHeart.getMothersIdNo());
				if (caseid != null) {
					fetalHeart.setCaseid(caseid);
					caseid.setFetalHeart(fetalHeart);
				}
			}
			fetalHeartRepository.saveAll(fetalHearts);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}
}
