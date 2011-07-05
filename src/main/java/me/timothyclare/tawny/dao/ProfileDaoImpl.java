package me.timothyclare.tawny.dao;

import java.util.List;

import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.dao.api.ProfileDao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProfileDaoImpl implements ProfileDao {
	
	SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	@Transactional
	public void save(Profile profile) {
		sessionFactory.getCurrentSession().persist(profile);
	}

	@Override
	@Transactional
	public Profile get(String account) {
		Object object = sessionFactory.getCurrentSession().get(Profile.class, account);
		Profile profile = null;
		
		if(object instanceof Profile) {
			profile = (Profile)object;
		}
		
		return profile;
	}
	
	@Override
	@Transactional
	public List<Profile> getAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("from Profile");
		
		@SuppressWarnings("unchecked")
		List<Profile> list = query.list();
		
		return list;
	}

	@Override
	@Transactional
	public void update(Profile profile) {
		sessionFactory.getCurrentSession().update(profile);
	}
}
