package co.id.mii.mcc63lmsserverside.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.id.mii.mcc63lmsserverside.exception.UserNotFoundException;
import co.id.mii.mcc63lmsserverside.model.Profile;
import co.id.mii.mcc63lmsserverside.model.dto.ProfileData;
import co.id.mii.mcc63lmsserverside.repository.ProfileRepository;

@Service
public class ProfileService {

  private final ProfileRepository profileRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public ProfileService(ProfileRepository profileRepository, ModelMapper modelMapper) {
    this.profileRepository = profileRepository;
    this.modelMapper = modelMapper;
  }

  public ProfileData getMyProfile(Long userId) {
    return modelMapper.map(this.getProfile(userId), ProfileData.class);
  }

  public ProfileData updateMyProfile(Long userId, ProfileData profileData) {
    Profile profile = getProfile(userId);
    Profile newProfile = modelMapper.map(profileData, Profile.class);

    String fullName = profileData.getFullName();
    if (fullName == null || fullName.trim().isEmpty()) {
      throw new NullPointerException("Fullname cannot be null");
    }

    newProfile.setId(profile.getId());
    newProfile.setUser(profile.getUser());

    return modelMapper.map(profileRepository.save(newProfile), ProfileData.class);
  }

  private Profile getProfile(Long userId) {
    return profileRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
  }
}
