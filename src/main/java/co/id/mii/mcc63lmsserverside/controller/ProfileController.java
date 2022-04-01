package co.id.mii.mcc63lmsserverside.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.id.mii.mcc63lmsserverside.model.dto.ProfileData;
import co.id.mii.mcc63lmsserverside.service.ProfileService;

@RestController
@RequestMapping("profiles")
public class ProfileController {

  private final ProfileService profileService;

  @Autowired
  public ProfileController(ProfileService profileService) {
    this.profileService = profileService;
  }

  @GetMapping("{userId}")
  @ResponseBody
  public ProfileData getMyProfile(@PathVariable("userId") Long userId) {
    return profileService.getMyProfile(userId);
  }

  @PutMapping("{userId}")
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public ProfileData updateProfile(@PathVariable("userId") Long userId, @RequestBody ProfileData profileData) {
    return profileService.updateMyProfile(userId, profileData);
  }
}
