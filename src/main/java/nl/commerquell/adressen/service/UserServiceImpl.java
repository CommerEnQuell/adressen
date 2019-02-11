package nl.commerquell.adressen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.commerquell.adressen.dao.UserRepository;
import nl.commerquell.adressen.entity.User;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository repository) {
		this.userRepository = repository;
	}

	@Override
	public User findByUsername(String username) {
		Optional<User> optUser = userRepository.findById(username);
		if (!optUser.isPresent()) {
			throw new RuntimeException("User \"" + username + "\" does not exist");
		}
		return optUser.get();
	}

	@Override
	public List<User> findAll() {
		List<User> retval = userRepository.findAllByOrderByUsernameAsc();
		return retval;
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public void deleteByUsername(String username) {
		userRepository.deleteById(username);
	}

}
