package br.com.alyson.apimanagertask.domain.service;

import br.com.alyson.apimanagertask.domain.exception.BusinessException;
import br.com.alyson.apimanagertask.domain.exception.EntityNotFoundException;
import br.com.alyson.apimanagertask.domain.model.User;
import br.com.alyson.apimanagertask.domain.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public User save(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new BusinessException(String.format("User with email %s already exists", user.getEmail()));
                });

        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User user) {

        User user1 = this.findById(id);

        BeanUtils.copyProperties(user, user1, "id");

        return userRepository.save(user1);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {

        User user = this.findById(id);

        userRepository.delete(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
