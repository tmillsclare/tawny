package me.timothyclare.tawny.dao.api;

import java.util.List;

import me.timothyclare.tawny.bean.Profile;

public interface ProfileDao {
	Profile get(String account);
	List<Profile> getAll();
	void update(Profile profile);
	void save(Profile profile);
}
