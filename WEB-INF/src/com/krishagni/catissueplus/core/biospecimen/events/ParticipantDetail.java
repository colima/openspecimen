
package com.krishagni.catissueplus.core.biospecimen.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.LazyInitializationException;

import com.krishagni.catissueplus.core.biospecimen.domain.Participant;
import com.krishagni.catissueplus.core.biospecimen.domain.ParticipantMedicalIdentifier;

public class ParticipantDetail {

	List<String> modifiedAttributes = new ArrayList<String>();

	private String firstName;

	private String lastName;

	private String middleName;

	private Date birthDate;

	private Date deathDate;

	private String gender;

	private Set<String> race;

	private String vitalStatus;

	private List<ParticipantMedicalIdentifierNumberDetail> pmis;

	private String sexGenotype;

	private String ethnicity;

	private String ssn;

	private String activityStatus;
	
	private String empi;

	private Long id;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Set<String> getRace() {
		return race;
	}

	public void setRace(Set<String> race) {
		this.race = race;
	}

	public String getVitalStatus() {
		return vitalStatus;
	}

	public void setVitalStatus(String vitalStatus) {
		this.vitalStatus = vitalStatus;
	}

	public List<ParticipantMedicalIdentifierNumberDetail> getPmis() {
		return pmis;
	}

	public void setPmis(List<ParticipantMedicalIdentifierNumberDetail> pmis) {
		this.pmis = pmis;
	}

	public String getSexGenotype() {
		return sexGenotype;
	}

	public void setSexGenotype(String sexGenotype) {
		this.sexGenotype = sexGenotype;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getEmpi() {
		return empi;
	}

	public void setEmpi(String empi) {
		this.empi = empi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static ParticipantDetail fromDomain(Participant participant) {
		ParticipantDetail result = new ParticipantDetail();
		
		result.setFirstName(participant.getFirstName());
		result.setLastName(participant.getLastName());
		result.setMiddleName(participant.getMiddleName());
		result.setActivityStatus(participant.getActivityStatus());
		result.setBirthDate(participant.getBirthDate());
		result.setDeathDate(participant.getDeathDate());
		result.setEthnicity(participant.getEthnicity());
		result.setGender(participant.getGender());
		result.setEmpi(participant.getEmpi());
		result.setId(participant.getId());
		
		//TODO revisit 
		List<ParticipantMedicalIdentifierNumberDetail> pmiColl = new ArrayList<ParticipantMedicalIdentifierNumberDetail>();
		try{
		Map<String, ParticipantMedicalIdentifier> pmi = participant.getPmiCollection();
		
		if (pmi != null) {
			for (ParticipantMedicalIdentifier participantMedicalIdentifier : pmi.values()) {
				ParticipantMedicalIdentifierNumberDetail medicalRecordNumberDetail = new ParticipantMedicalIdentifierNumberDetail();
				medicalRecordNumberDetail.setMrn(participantMedicalIdentifier.getMedicalRecordNumber());
				medicalRecordNumberDetail.setSiteName(participantMedicalIdentifier.getSite().getName());
				pmiColl.add(medicalRecordNumberDetail);
			}
		}
		}catch(LazyInitializationException e)
		{
			
		}
		result.setPmis(pmiColl);
		Set<String> raceSet = participant.getRaceColl();
		Set<String> newRace = new HashSet<String>(); 
		if(raceSet != null){
			for (String race : raceSet) {
				newRace.add(race);
			}
		}
		
		result.setRace(newRace);
		result.setSexGenotype(participant.getSexGenotype());
		result.setSsn(participant.getSocialSecurityNumber());
		result.setVitalStatus(participant.getVitalStatus());
		return result;
	}
	
	public static List<ParticipantDetail> from(List<Participant> participants) {
		List<ParticipantDetail> result = new ArrayList<ParticipantDetail>();
		for (Participant participant : participants) {
			result.add(ParticipantDetail.fromDomain(participant));
		}
		
		return result;
	}
}
