package qnns.venusrestblog.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import qnns.venusrestblog.data.User;
import qnns.venusrestblog.repository.UserRepository;
import qnns.venusrestblog.misc.FieldHelper;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
public class UsersController {
    private UserRepository usersRepository;

    @GetMapping("")
    public List<User> fetchUsers() {
      return  usersRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> fetchUserById(@PathVariable long id) {
        Optional<User> user = usersRepository.findById(id);
        if(fetchUsers().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User "+ id + "not found");
        }
        return user;
    }

    @GetMapping("/me")
    private Optional<User> fetchMe() {

        return usersRepository.findById(1L);
    }

//    @GetMapping("/username/{userName}")
//    private User fetchByUserName(@PathVariable String userName) {
//        User user = findUserByUserName(userName);
//        if(user == null) {
//            // what to do if we don't find it
//            throw new RuntimeException("I don't know what I am doing");
//        }
//        return usersRepository.findByUsername(userName);
//    }
//
//    @GetMapping("/email/{email}")
//    private User fetchByEmail(@PathVariable String email) {
////        User user = findUserByEmail(email);
////        if(user == null) {
////            // what to do if we don't find it
////            throw new RuntimeException("I don't know what I am doing");
////        }
//        return usersRepository.findByEmail(email);
//    }

    private Optional<User> findUserById(long id) {
        // didn't find it so do something
        return usersRepository.findById(id);
    }

    @PostMapping("/create")
    public void createUser(@RequestBody User newUser) {
       newUser.setCreatedAt(LocalDate.now());
        usersRepository.save(newUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        usersRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(@RequestBody User updatedUser, @PathVariable long id) {
        // get the original record from the db
        Optional<User> userOptional = usersRepository.findById(id);
        // return 404 if user not found
        if(userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id " + id + " not found");
        }
        // get the user from the optional so we no longer have to deal with the optional
        User originalUser = userOptional.get();

        // merge the changed data in updatedUser with originalUser
        BeanUtils.copyProperties(updatedUser, originalUser, FieldHelper.getNullPropertyNames(updatedUser));

        // originalUser now has the merged data (changes + original data)
        originalUser.setId(id);

        usersRepository.save(originalUser);
    }

    @PutMapping("/{id}/updatePassword")
    private void updatePassword(@PathVariable Long id, @RequestParam(required = false) String oldPassword, @Valid @Size(min = 3) @RequestParam String newPassword) {
        User user = usersRepository.findById(id).get();
//        if(user == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User id" + id + "not found");
//        }

        // compare old password with saved pw
        if(!user.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "amscray");
        }

        // validate new password
        if(newPassword.length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NEW password length must be at least 3 characters");
        }

        user.setPassword(newPassword);
        usersRepository.save(user);
    }
}