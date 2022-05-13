package com.example.JustLearning.service;

import com.example.JustLearning.domain.Role;
import com.example.JustLearning.domain.User;
import com.example.JustLearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final String uploadPath;

    public UserService(UserRepository userRepository, @Value("${upload.path}") String uploadPath) {
        this.userRepository = userRepository;
        this.uploadPath = uploadPath;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public void updateRoles(User user, Map<String, String> form, String username) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet())
            if (roles.contains(key))
                user.getRoles().add(Role.valueOf(key));

        userRepository.save(user);
    }

    public boolean addNewUser(User user, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null){
            model.addAttribute("message", "User exists!");
            return true;
        }
        if(file.getOriginalFilename().isEmpty()){
            user.setImgProfile("68e2b43f-0a8f-4ed6-97a7-9fde4314e703.profileimage.png");
        } else{
            uploadFile(user, file);
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return false;
    }

    public String editUserData(User user, String name, String second_name, String email, String password, MultipartFile file, Model model) throws IOException {
        String truePas = user.getPassword();
        if (truePas.equals(password)) {
            user.setName(name);
            user.setSecond_name(second_name);
            user.setEmail(email);
            if (!file.getOriginalFilename().isEmpty()) {
                uploadFile(user, file);
            }
            userRepository.save(user);

            return "redirect:/profile/" + user.getId();
        } else {
            model.addAttribute("error", "Пароль пуст или введен неверно!");
            return "redirect:/profile/edit/" + user.getId();
        }
    }

    public void uploadFile(User user, MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);

        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();

        file.transferTo(new File(uploadPath + "/" + resultFilename));

        user.setImgProfile(resultFilename);
    }
}
