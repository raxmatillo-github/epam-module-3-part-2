package com.epam.esm.daos;

import java.util.List;

import com.epam.esm.entities.GiftCertificate;

public interface GiftCertificateDAO {

	public List<GiftCertificate> getAllGiftCertificates();

	public GiftCertificate getGiftCertificateById(int id);

	public String getGiftCertificateNameById(int certificateId);
	
	public List<GiftCertificate> getGiftCertificatesByPaging(int page, int size);

	public List<GiftCertificate> getAllGiftCertificatesBySorting(String orderBy, String type);

	public List<GiftCertificate> getAllGiftCertificatesBySearching(String value);

	public int saveGiftCertificate(GiftCertificate giftCertificate);

	public int updateGiftCertificate(String sql, Object... params);

	public int deleteGiftCertificate(int id);

	public int getLastSavedGiftCertificateId();

	public boolean isExistByCertificateName(String name);

	public boolean isExistByCertificateId(int certificateId);
	

}
